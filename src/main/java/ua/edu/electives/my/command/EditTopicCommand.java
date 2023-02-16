package ua.edu.electives.my.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.edu.electives.my.dao.CourseManager;
import ua.edu.electives.my.dao.DBException;
import ua.edu.electives.my.entity.Topic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/** Command prepares and displays page for editing selected topic.

 */
public class EditTopicCommand implements Command {
    private static final Logger logger = LogManager.getLogger(EditTopicCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        List<Topic> topics = CourseManager.getInstance().getTopics();
        req.setAttribute("topics", topics);
        logger.debug("Edit topic page loaded successfully");
        return ConstJSP.EDIT_TOPIC;
    }
}
