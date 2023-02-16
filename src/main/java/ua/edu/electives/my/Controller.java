package ua.edu.electives.my;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.edu.electives.my.command.Command;
import ua.edu.electives.my.command.CommandContainer;
import ua.edu.electives.my.command.ConstJSP;
import ua.edu.electives.my.dao.DBException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/** Front controller servlet.

*/
@WebServlet("/front")
public class Controller extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(Controller.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String address = ConstJSP.ERROR;
        String commandName = req.getParameter("command");
        Command command = CommandContainer.getCommand(commandName);
        logger.debug("GET request: command ==> " + commandName);

        try {
            address = command.execute(req, resp);
            logger.debug("Forward to address ==> " + address);
        } catch (DBException e) {
            logger.error("Exception captured in front controller: " + e);
            req.setAttribute("errorMessage", e.getMessage());
        }
        req.getRequestDispatcher(address).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String address = ConstJSP.ERROR;
        String commandName = req.getParameter("command");
        Command command = CommandContainer.getCommand(commandName);
        logger.debug("POST request: command ==> " + commandName);

        try {
            address = command.execute(req, resp);
            logger.debug("Redirect to address ==> " + address);
        } catch (DBException e) {
            logger.error("Exception captured in front controller: " + e);
            req.getSession().setAttribute("errorMessage", e.getMessage());
        }
        resp.sendRedirect(address);
    }
}
