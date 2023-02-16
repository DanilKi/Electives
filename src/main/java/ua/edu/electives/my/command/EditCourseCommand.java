package ua.edu.electives.my.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.edu.electives.my.dao.CourseManager;
import ua.edu.electives.my.dao.DBException;
import ua.edu.electives.my.dao.UserManager;
import ua.edu.electives.my.entity.Course;
import ua.edu.electives.my.entity.Topic;
import ua.edu.electives.my.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/** Command prepares and displays page for editing selected course.

 */
public class EditCourseCommand implements Command {
    private static final Logger logger = LogManager.getLogger(EditCourseCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        int courseId = Integer.parseInt(req.getParameter("course-id"));
        Course course = CourseManager.getInstance().getCourse(courseId);
        req.getSession().setAttribute("editCourse", course);
        List<User> teachers = UserManager.getInstance().getTeachers();
        List<Topic> topics = CourseManager.getInstance().getTopics();
        req.setAttribute("teachers", teachers);
        req.setAttribute("topics", topics);
        logger.debug("Edit course page loaded successfully");
        return ConstJSP.EDIT_COURSE;
    }
}
