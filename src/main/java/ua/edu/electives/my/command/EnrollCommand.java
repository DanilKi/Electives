package ua.edu.electives.my.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.edu.electives.my.dao.CourseManager;
import ua.edu.electives.my.dao.DBException;
import ua.edu.electives.my.entity.CourseStudent;
import ua.edu.electives.my.entity.User;
import ua.edu.electives.my.util.LocaleUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/** Command enrolls current user as student into selected course.

 */
public class EnrollCommand implements Command {
    private static final Logger logger = LogManager.getLogger(EnrollCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        int courseId = Integer.parseInt(req.getParameter("course-id"));
        int studentId = Integer.parseInt(req.getParameter("student-id"));
        String address = (String) req.getSession().getAttribute("backAddress"); // "front?command=courseinfo&course-id=" + courseId;
        User user = (User) req.getSession().getAttribute("authorizedUser");

        CourseManager.getInstance().enrollInCourse(courseId, studentId);
        List<CourseStudent> csList = CourseManager.getInstance().getCoursesByStudent(user, "none");
        req.getSession().setAttribute("courseList", csList);
        String message = LocaleUtil.toLocaleString(req, "enroll.success");
        req.getSession().setAttribute("infoMessage", message);
        logger.debug("User " + user.getEmail() + " successfully enrolled in the course with id: " + courseId);
        return address;
    }
}
