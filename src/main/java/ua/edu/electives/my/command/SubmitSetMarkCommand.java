package ua.edu.electives.my.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.edu.electives.my.dao.DBException;
import ua.edu.electives.my.dao.UserManager;
import ua.edu.electives.my.util.LocaleUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Command sets marks for selected course to ejournal and saves changes to database.

 */
public class SubmitSetMarkCommand implements Command {
    private static final Logger logger = LogManager.getLogger(SubmitSetMarkCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        req.getSession().removeAttribute("course");
        req.getSession().removeAttribute("courseStudents");
        int courseId = Integer.parseInt(req.getParameter("course-id"));
        String[] studentIds = req.getParameterValues("student-id");
        String[] marks = req.getParameterValues("mark");
        String address = "front?command=courseinfo&course-id=" + courseId;
        UserManager userManager = UserManager.getInstance();
        for (int i = 0; i < studentIds.length; i++) {
            int studentId = Integer.parseInt(studentIds[i]);
            int mark = Integer.parseInt(marks[i]);
            userManager.updateCourseStudents(courseId, studentId, mark);
        }
        logger.info("Marks saved successfully");
        String message = LocaleUtil.toLocaleString(req, "setmarks.success");
        req.getSession().setAttribute("infoMessage", message);
        return address;
    }

}
