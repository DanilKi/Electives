package ua.edu.electives.my.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.edu.electives.my.dao.CourseManager;
import ua.edu.electives.my.dao.DBException;
import ua.edu.electives.my.dao.UserManager;
import ua.edu.electives.my.entity.Topic;
import ua.edu.electives.my.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/** Command prepares and displays page for creating new course.

 */
public class CreateCourseCommand implements  Command {
    private static final Logger logger = LogManager.getLogger(CreateCourseCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        List<User> teachers = UserManager.getInstance().getTeachers();
        List<Topic> topics = CourseManager.getInstance().getTopics();
        req.setAttribute("teachers", teachers);
        req.setAttribute("topics", topics);
        logger.debug("Create course page loaded successfully");
        return ConstJSP.CREATE_COURSE;
    }
}
