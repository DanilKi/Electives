package ua.edu.electives.my;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/** Context listener class.
 * for initializing logfile path and locales path

 */
@WebListener
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();
        String path = ctx.getRealPath("WEB-INF/log/log4j2.log");
        System.setProperty("logFile", path);

        final Logger logger = LogManager.getLogger(ContextListener.class);
        logger.info("Electives application started");
        logger.debug("Using log file: " + path);

        String localesFileName = ctx.getInitParameter("locales");
        String localesFileRealPath = ctx.getRealPath(localesFileName);
        Properties locales = new Properties();
        try {
            locales.load(new FileInputStream(localesFileRealPath));
        } catch (IOException e) {
            logger.error("Can't find localization parameters");
        }

        ctx.setAttribute("locales", locales);
        locales.list(System.out);
        logger.debug("Localization parameters initialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.clearProperty("logFile");
    }
}
