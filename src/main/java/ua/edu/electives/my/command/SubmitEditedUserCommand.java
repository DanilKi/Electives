package ua.edu.electives.my.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.edu.electives.my.dao.DBException;
import ua.edu.electives.my.dao.UserManager;
import ua.edu.electives.my.entity.Role;
import ua.edu.electives.my.entity.User;
import ua.edu.electives.my.util.LocaleUtil;
import ua.edu.electives.my.util.PasswordUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Command edits profile for selected user and saves changes to database.

 */
public class SubmitEditedUserCommand implements Command {
    private static final Logger logger = LogManager.getLogger(SubmitEditedUserCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        User user = (User) req.getSession().getAttribute("editUser");
        Role role = (Role) req.getSession().getAttribute("role");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        if (password == null || password.isEmpty()) {
            password = user.getPassword();
        } else {
            password = PasswordUtil.encrypt(password);
        }
        String firstName = req.getParameter("first-name");
        String lastName = req.getParameter("last-name");
        String status = req.getParameter("status");
        boolean blocked = false;
        if (status == null) {
            blocked = user.isBlocked();
        } else {
            blocked = Boolean.parseBoolean(status);
        }

        User updUser = new User();
        updUser.setId(user.getId());
        updUser.setEmail(email);
        updUser.setPassword(password);
        updUser.setFirstName(firstName);
        updUser.setLastName(lastName);
        updUser.setRoleId(user.getRoleId());
        updUser.setBlocked(blocked);
        UserManager.getInstance().updateUser(updUser);
        logger.debug("User data updated for: " + updUser.getEmail());

        if (!role.getName().equalsIgnoreCase("admin")) {
            req.getSession().setAttribute("authorizedUser", updUser);
        }
        String message = LocaleUtil.toLocaleString(req, "edituser.success");
        req.getSession().setAttribute("infoMessage", message);
        return ConstJSP.INDEX;
    }
}
