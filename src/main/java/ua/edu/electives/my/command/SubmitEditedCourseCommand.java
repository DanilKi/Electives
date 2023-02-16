package ua.edu.electives.my.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.edu.electives.my.dao.CourseManager;
import ua.edu.electives.my.dao.DBException;
import ua.edu.electives.my.entity.Course;
import ua.edu.electives.my.util.LocaleUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/** Command edits selected course and saves changes to database.

 */
public class SubmitEditedCourseCommand implements Command {
    private static final Logger logger = LogManager.getLogger(SubmitEditedCourseCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        Course course = (Course) req.getSession().getAttribute("editCourse");
        String address = "front?command=courseinfo&course-id=" + course.getId();
        String startDateStr = req.getParameter("start-date");
        String endDateStr = req.getParameter("end-date");

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null; Date endDate = null;
        try {
            startDate = format.parse(startDateStr);
            endDate = format.parse(endDateStr);
        } catch (ParseException e) {
            logger.error("Cannot obtain formatted date while updating course. " + e);
            throw new DBException("Cannot get correct date while updating course", e);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate beginDate = LocalDate.parse(startDateStr, formatter);
        LocalDate finishDate = LocalDate.parse(endDateStr, formatter);
        int duration = (int) beginDate.until(finishDate, ChronoUnit.DAYS);

        Course updCourse = new Course();
        updCourse.setId(course.getId());
        updCourse.setTitle(req.getParameter("title"));
        updCourse.setStartDate(startDate);
        updCourse.setEndDate(endDate);
        updCourse.setDuration(duration);
        updCourse.setTopicId(Integer.parseInt(req.getParameter("topic-id")));
        updCourse.setTeacherId(Integer.parseInt(req.getParameter("teacher-id")));
        updCourse.setDescription(req.getParameter("description"));
        CourseManager.getInstance().updateCourse(updCourse);
        logger.info("Course data updated for: "+ updCourse.getTitle());
        String message = LocaleUtil.toLocaleString(req, "editcourse.success");
        req.getSession().setAttribute("infoMessage", message);
        return address; //ConstJSP.INDEX;
    }
}
