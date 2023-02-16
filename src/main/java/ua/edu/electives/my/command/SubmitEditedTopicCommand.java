package ua.edu.electives.my.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.edu.electives.my.dao.CourseManager;
import ua.edu.electives.my.dao.DBException;
import ua.edu.electives.my.entity.Topic;
import ua.edu.electives.my.util.LocaleUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Command edits selected topic and saves changes to database.

 */
public class SubmitEditedTopicCommand implements Command {
    private static final Logger logger = LogManager.getLogger(SubmitEditedTopicCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        int topicId = Integer.parseInt(req.getParameter("topic-id"));
        String topicName = req.getParameter("topic-name");

        Topic topic = new Topic();
        topic.setId(topicId);
        topic.setName(topicName);
        CourseManager.getInstance().editTopic(topic);
        logger.info("Topic data updated for: "+ topic.getName());
        String message = LocaleUtil.toLocaleString(req, "edittopic.success");
        req.getSession().setAttribute("infoMessage", message);
        return ConstJSP.INDEX;
    }
}
