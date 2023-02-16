package ua.edu.electives.my.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.edu.electives.my.dao.CourseManager;
import ua.edu.electives.my.dao.DBException;
import ua.edu.electives.my.entity.Topic;
import ua.edu.electives.my.util.LocaleUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Command creates new topic for courses.

 */
public class CreateTopicCommand implements Command {
    private static final Logger logger = LogManager.getLogger(CreateTopicCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        String topicName = req.getParameter("topic-name");
        Topic topic = new Topic();
        topic.setName(topicName);
        CourseManager.getInstance().insertTopic(topic);
        logger.info("Topic " + topic.getName() + " created successfully");
        String message = LocaleUtil.toLocaleString(req, "createtopic.success");
        req.getSession().setAttribute("infoMessage", message);
        return ConstJSP.INDEX;
    }
}
