package ua.edu.electives.my.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.edu.electives.my.dao.CourseManager;
import ua.edu.electives.my.dao.DBException;
import ua.edu.electives.my.entity.Course;
import ua.edu.electives.my.entity.Topic;
import ua.edu.electives.my.util.DateUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/** Command gets and displays information about selected course.

 */
public class CourseInfoCommand implements Command {

    private static final Logger logger = LogManager.getLogger(CourseInfoCommand.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        int courseId = Integer.parseInt(req.getParameter("course-id"));
        Course course = CourseManager.getInstance().getCourse(courseId);
        Topic topic = CourseManager.getInstance().getTopic(course.getTopicId());
        Date currentDate = DateUtil.getCurrentDate();

        req.setAttribute("course", course);
        req.setAttribute("topic", topic);
        req.setAttribute("currentDate", currentDate);
        logger.debug("Course information page loaded successfully");
        return ConstJSP.COURSE_INFO;
    }
}
