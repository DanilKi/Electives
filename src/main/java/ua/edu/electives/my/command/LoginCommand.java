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

/** Command for signing in the existing user.

 */
public class LoginCommand implements Command {

    private static final Logger logger = LogManager.getLogger(LoginCommand.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        req.getSession().removeAttribute("errorLogin");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String address = ConstJSP.INDEX;

        User user = UserManager.getInstance().getUser(login);
        if (user == null) {
            logger.error("Login error. Cannot find user " + login);
            String message = LocaleUtil.toLocaleString(req, "login.error_login");
            req.getSession().setAttribute("errorLogin", message);
            return address;
        }

        String hashedPassword = PasswordUtil.encrypt(password);
        if (!user.getPassword().equals(hashedPassword)) {
            logger.error("Login error. Incorrect password");
            String message = LocaleUtil.toLocaleString(req, "login.error_password");
            req.getSession().setAttribute("errorLogin", message);
            return address;
        } else if (user.isBlocked()) {
            logger.error("Login error. User account is blocked");
            String message = LocaleUtil.toLocaleString(req, "login.error_blocked");
            req.getSession().setAttribute("errorLogin", message);
            return address;
        }

        address = "front?command=mymenu&tab=inprogress&sort=az&page=1";
        Role role = Role.getRole(user);
        req.getSession().setAttribute("authorizedUser", user);
        req.getSession().setAttribute("role", role);
        logger.info("User " + login + " logged in successfully. Role ==> " + role.getName());
        return address;
    }

}
