package ua.edu.electives.my.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.ResourceBundle;

/** Class for localization messages of the application

 */
public class LocaleUtil {

    private static final Locale en = new Locale("en");
    private static final Locale uk = new Locale("uk");
    private static final ResourceBundle enResourceBundle = ResourceBundle.getBundle("resources", en);
    private static final ResourceBundle ukResourceBundle = ResourceBundle.getBundle("resources", uk);

    private LocaleUtil() {}

    public static String toLocaleString(HttpServletRequest req, String key) {
        HttpSession session = req.getSession(false);
        String locale = (String) session.getAttribute("currentLocale");

        if (locale == null) {
            return enResourceBundle.getString(key);
        }

        switch (locale) {
            case "uk":
                return ukResourceBundle.getString(key);
            default:
                return enResourceBundle.getString(key);
        }
    }

}
