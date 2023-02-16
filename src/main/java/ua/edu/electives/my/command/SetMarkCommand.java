package ua.edu.electives.my.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.edu.electives.my.dao.DBException;
import ua.edu.electives.my.dao.UserManager;
import ua.edu.electives.my.entity.Course;
import ua.edu.electives.my.entity.CourseStudent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/** Command prepares and displays ejournal page for selected course.

 */
public class SetMarkCommand implements Command {
    private static final Logger logger = LogManager.getLogger(SetMarkCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        Course course = (Course) req.getSession().getAttribute("course");
        List<CourseStudent> csList = UserManager.getInstance().getCourseStudents(course);
        req.getSession().setAttribute("courseStudents", csList);
        logger.debug("E-Journal page loaded successfully");
        return ConstJSP.EJOURNAL;
    }
}
