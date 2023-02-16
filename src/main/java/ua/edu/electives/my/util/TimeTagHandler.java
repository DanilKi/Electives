package ua.edu.electives.my.util;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/** Custom tag that outputs date and time.

 */

public class TimeTagHandler extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            String locale = (String) pageContext.getSession().getAttribute("currentLocale");
            if (locale == null) { locale = "en"; }
            if ("uk".equals(locale)) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
                out.print(dateFormat.format(Calendar.getInstance().getTime()));
            } else {
                out.print(Calendar.getInstance().getTime());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SKIP_BODY;
    }
}
