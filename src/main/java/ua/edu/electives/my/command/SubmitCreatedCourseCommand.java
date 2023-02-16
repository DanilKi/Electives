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

/** Command creates a new course and saves it to database.

 */
public class SubmitCreatedCourseCommand implements Command {
    private static final Logger logger = LogManager.getLogger(SubmitCreatedCourseCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        String title = req.getParameter("title");
        String startDateStr = req.getParameter("start-date");
        String endDateStr = req.getParameter("end-date");
        int topicId = Integer.parseInt(req.getParameter("topic-id"));
        int teacherId = Integer.parseInt(req.getParameter("teacher-id"));
        String description = req.getParameter("description");

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = format.parse(startDateStr);
            endDate = format.parse(endDateStr);
        } catch (ParseException e) {
            logger.error("Cannot obtain formatted date while creating new course. " + e);
            throw new DBException("Cannot get correct date while creating new course", e);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate beginDate = LocalDate.parse(startDateStr, formatter);
        LocalDate finishDate = LocalDate.parse(endDateStr, formatter);
        int duration = (int) beginDate.until(finishDate, ChronoUnit.DAYS);

        Course course = new Course();
        course.setTitle(title);
        course.setStartDate(startDate);
        course.setEndDate(endDate);
        course.setDuration(duration);
        course.setTopicId(topicId);
        course.setTeacherId(teacherId);
        course.setDescription(description);
        CourseManager.getInstance().createCourse(course);
        logger.info("New course created: "+ course.getTitle());
        String message = LocaleUtil.toLocaleString(req, "createcourse.success");
        req.getSession().setAttribute("infoMessage", message);
        return ConstJSP.INDEX;
    }
}
