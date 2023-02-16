package ua.edu.electives.my.command;

import ua.edu.electives.my.dao.DBException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Interface for implementation of command pattern

 */
public interface Command {
    String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException;
}
