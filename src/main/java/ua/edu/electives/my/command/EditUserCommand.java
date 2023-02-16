package ua.edu.electives.my.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.edu.electives.my.dao.DBException;
import ua.edu.electives.my.dao.UserManager;
import ua.edu.electives.my.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Command prepares and displays page for editing selected user.

 */
public class EditUserCommand implements Command {
    private static final Logger logger = LogManager.getLogger(EditUserCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        int id = Integer.parseInt(req.getParameter("id"));
        User user = UserManager.getInstance().getUser(id);
        req.getSession().setAttribute("editUser", user);
        logger.debug("Edit user page loaded successfully");
        return ConstJSP.EDIT_USER;
    }
}
