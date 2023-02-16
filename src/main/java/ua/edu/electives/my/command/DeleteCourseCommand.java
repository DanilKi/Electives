package ua.edu.electives.my.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.edu.electives.my.dao.CourseManager;
import ua.edu.electives.my.dao.DBException;
import ua.edu.electives.my.util.LocaleUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Command deletes selected course.

 */
public class DeleteCourseCommand implements Command {
    private static final Logger logger = LogManager.getLogger(DeleteCourseCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        int courseId = Integer.parseInt(req.getParameter("course-id"));
        CourseManager.getInstance().deleteCourse(courseId);
        logger.info("Course with id: '"+ courseId + "' deleted successfully");
        String message = LocaleUtil.toLocaleString(req, "deletecourse.success");
        req.getSession().setAttribute("infoMessage", message);
        return ConstJSP.INDEX;
    }
}
