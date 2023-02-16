package ua.edu.electives.my.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.edu.electives.my.dao.DBException;
import ua.edu.electives.my.dao.UserManager;
import ua.edu.electives.my.entity.User;
import ua.edu.electives.my.util.LocaleUtil;
import ua.edu.electives.my.util.PasswordUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Command for registering new user profile.

 */
public class RegisterCommand implements Command {

    private static final Logger logger = LogManager.getLogger(RegisterCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String firstName = req.getParameter("firstname");
        String lastName = req.getParameter("lastname");
        int roleId;
        if (req.getParameter("roleid") == null) {
            roleId = 2;
        } else {
            roleId = Integer.parseInt(req.getParameter("roleid"));
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(PasswordUtil.encrypt(password));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setRoleId(roleId);
        user.setBlocked(false);
        UserManager.getInstance().registerUser(user);
        logger.info("User " + user.getEmail() + " registered successfully");
        String message = LocaleUtil.toLocaleString(req, "register.success");
        req.getSession().setAttribute("infoMessage", message);
        return ConstJSP.INDEX;
    }
}
