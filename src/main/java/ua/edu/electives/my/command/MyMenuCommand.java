package ua.edu.electives.my.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.edu.electives.my.dao.CourseManager;
import ua.edu.electives.my.dao.DBException;
import ua.edu.electives.my.entity.Course;
import ua.edu.electives.my.entity.CourseStudent;
import ua.edu.electives.my.entity.Role;
import ua.edu.electives.my.entity.User;
import ua.edu.electives.my.util.DateUtil;
import ua.edu.electives.my.util.LocaleUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/** Command forms and displays personal user menu depending on user role.

 */
public class MyMenuCommand implements Command {

    private static final Logger logger = LogManager.getLogger(MyMenuCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        User user = (User) req.getSession().getAttribute("authorizedUser");
        Role role = (Role) req.getSession().getAttribute("role");
        Date currentDate = DateUtil.getCurrentDate();
        String tab = "inprogress";
        if (req.getParameter("tab") != null) {
            tab = req.getParameter("tab");
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

        if (role.getName().equals("teacher")) {
            List<Course> courses = CourseManager.getInstance().getCoursesByTeacher(user.getId(), tab, sort,
                                                            recordsPerPage * (page - 1), recordsPerPage);
            if (courses.isEmpty()) {
                String message = LocaleUtil.toLocaleString(req, "pagination.nothing");
                req.setAttribute("nothingFound", message);
            }
            int numOfRecords = CourseManager.getInstance().getNumOfTeacherCourses(user.getId(), tab);
            int numOfPages = (int) Math.ceil(numOfRecords * 1.0 / recordsPerPage);

            req.setAttribute("list", courses);
            req.setAttribute("numOfPages", numOfPages);
            req.setAttribute("page", page);
            logger.debug("Teacher menu for " + user.getEmail() + " prepared successfully");
        } else if (role.getName().equals("student")) {
            List<CourseStudent> csList = CourseManager.getInstance().getCoursesByStudent(user, sort);
            req.getSession().setAttribute("courseList", csList);
            if (tab.equalsIgnoreCase("inprogress")) {
                csList = csList.stream().filter(c -> c.getCourse().getStartDate().before(DateUtil.addDays(currentDate, 1)))
                        .filter(c -> c.getCourse().getEndDate().after(currentDate)).collect(Collectors.toList());
            } else if (tab.equalsIgnoreCase("notstarted")) {
                csList = csList.stream().filter(c -> c.getCourse().getStartDate().after(currentDate)).collect(Collectors.toList());
            } else if (tab.equalsIgnoreCase("finished")) {
                csList = csList.stream().filter(c -> c.getCourse().getEndDate().before(DateUtil.addDays(currentDate, 1))).collect(Collectors.toList());
            }

            int numOfRecords = csList.size();
            int numOfPages = (int) Math.ceil(numOfRecords * 1.0 / recordsPerPage);
            if (csList.isEmpty()) {
                String message = LocaleUtil.toLocaleString(req, "pagination.nothing");
                req.setAttribute("nothingFound", message);
            } else {
                if (recordsPerPage * page < csList.size()) {
                    csList = csList.subList(recordsPerPage * (page - 1), recordsPerPage * page);
                } else {
                    csList = csList.subList(recordsPerPage * (page - 1), csList.size());
                }
            }

            req.setAttribute("list", csList);
            req.setAttribute("numOfPages", numOfPages);
            req.setAttribute("page", page);
            logger.debug("Student menu for " + user.getEmail() + " prepared successfully");
        } else if (role.getName().equals("admin")) {
            logger.debug("Admin menu for " + user.getEmail() + " prepared successfully");
        }
        logger.debug("My Menu page loaded successfully");
        return ConstJSP.MY_MENU;
    }
}
