package ua.edu.electives.my.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.edu.electives.my.dao.CourseManager;
import ua.edu.electives.my.dao.DBException;
import ua.edu.electives.my.dao.UserManager;
import ua.edu.electives.my.entity.Course;
import ua.edu.electives.my.entity.Topic;
import ua.edu.electives.my.entity.User;
import ua.edu.electives.my.util.LocaleUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/** Command gets and displays information about elective courses.

 */
public class GetAllCoursesCommand implements Command {

    private static final Logger logger = LogManager.getLogger(GetAllCoursesCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        int teacher = 0;
        if (req.getParameter("teacher") != null) {
            teacher = Integer.parseInt(req.getParameter("teacher"));
        }
        int topic = 0;
        if (req.getParameter("topic") != null) {
           topic = Integer.parseInt(req.getParameter("topic"));
        }
        String sort = "none";
        if (req.getParameter("sort") != null) {
            sort = req.getParameter("sort");
        }
        int page = 1;
        if (req.getParameter("page") != null) {
            page = Integer.parseInt(req.getParameter("page"));
        }
        int recordsPerPage = 10;

        List<Course> courses = CourseManager.getInstance().getCourses(teacher, topic, sort,
                                        recordsPerPage * (page - 1), recordsPerPage);
        if (courses.isEmpty()) {
            String message = LocaleUtil.toLocaleString(req, "pagination.nothing");
            req.setAttribute("nothingFound", message);
        }
        int numOfRecords = CourseManager.getInstance().getNumOfCourses(teacher, topic);
        int numOfPages = (int) Math.ceil(numOfRecords * 1.0 / recordsPerPage);
        req.setAttribute("list", courses);
        req.setAttribute("numOfPages", numOfPages);
        req.setAttribute("page", page);

        List<User> teachers = UserManager.getInstance().getTeachers();
        List<Topic> topics = CourseManager.getInstance().getTopics();
        req.setAttribute("teachers", teachers);
        req.setAttribute("topics", topics);
        logger.debug("Courses page loaded successfully");
        return ConstJSP.COURSES;
    }
}
