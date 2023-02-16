package ua.edu.electives.my.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.edu.electives.my.dao.DBException;
import ua.edu.electives.my.dao.UserManager;
import ua.edu.electives.my.entity.User;
import ua.edu.electives.my.util.LocaleUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/** Command gets and displays information about existing users and their roles.

 */
public class GetAllUsersCommand implements Command {
    private static final Logger logger = LogManager.getLogger(GetAllUsersCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        int roleId = 1;
        if (req.getParameter("tab") != null) {
            roleId = Integer.parseInt(req.getParameter("tab"));
        }
        String sort = "none";
        if (req.getParameter("sort") != null) {
            sort = req.getParameter("sort");
        }
        int page = 1;
        if (req.getParameter("page") != null) {
            page = Integer.parseInt(req.getParameter("page"));
        }
        int recordsPerPage = 10;
        int beginFromRecord = recordsPerPage * (page - 1);

        List<User> users = UserManager.getInstance().getUsers(roleId, sort, beginFromRecord, recordsPerPage);
        if (users.isEmpty()) {
            String message = LocaleUtil.toLocaleString(req, "pagination.nothing");
            req.setAttribute("nothingFound", message);
        }
        int numOfRecords = UserManager.getInstance().getNumOfUsers(roleId);
        int numOfPages = (int) Math.ceil(numOfRecords * 1.0 / recordsPerPage);
        req.setAttribute("list", users);
        req.setAttribute("numOfPages", numOfPages);
        req.setAttribute("page", page);
        logger.debug("Users page loaded successfully");
        return ConstJSP.USERS;
    }
}
