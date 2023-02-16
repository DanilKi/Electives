package ua.edu.electives.my.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.edu.electives.my.dao.DBException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/** Class for formatting Date objects

 */
public class DateUtil {

    private static final Logger logger = LogManager.getLogger(DateUtil.class);

    private DateUtil() {}

    public static Date getCurrentDate() throws DBException {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String stringDate = dateFormat.format(date);
        try {
            date = dateFormat.parse(stringDate);
        } catch (ParseException e) {
            logger.error("Cannot obtain formatted date. " + e);
            throw new DBException("Cannot get current date", e);
        }
        return date;
    }

    public static String getCurrentDateString() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

}
