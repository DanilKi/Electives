package ua.edu.electives.my.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.edu.electives.my.command.ConstJSP;
import ua.edu.electives.my.entity.Role;
import ua.edu.electives.my.util.LocaleUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Authorization filter for access control.
 * Defines pages and controller commands users have access to depending on their roles.

 */
public class AuthFilter implements Filter {
    private static Map<String, List<String>> commandAccessMap = new HashMap<>();;
    private static Map<String, List<String>> jspPageAccessMap = new HashMap<>();

    private static final Logger logger = LogManager.getLogger(AuthFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        commandAccessMap.put("admin", asList(filterConfig.getInitParameter("admin.cmd")));
        commandAccessMap.put("teacher", asList(filterConfig.getInitParameter("teacher.cmd")));
        commandAccessMap.put("student", asList(filterConfig.getInitParameter("student.cmd")));
        commandAccessMap.put("anyone", asList(filterConfig.getInitParameter("anyone.cmd")));
        jspPageAccessMap.put("admin", asList(filterConfig.getInitParameter("admin.jsp")));
        jspPageAccessMap.put("teacher", asList(filterConfig.getInitParameter("teacher.jsp")));
        jspPageAccessMap.put("student", asList(filterConfig.getInitParameter("student.jsp")));
        jspPageAccessMap.put("anyone", asList(filterConfig.getInitParameter("anyone.jsp")));
        logger.debug("Authorization filter parameters initialized");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String command = req.getParameter("command");
        String path = req.getServletPath();
        Role role = (Role) req.getSession().getAttribute("role");
        String roleString = "";
        if (role != null) {
            roleString = role.getName();
        } else {
            roleString = "anyone";
        }
        if ((command != null && commandAccessMap.get(roleString).contains(command))
                || (command == null && jspPageAccessMap.get(roleString).contains(path))) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            String message = LocaleUtil.toLocaleString(req, "authfilter.forbidden");
            servletRequest.setAttribute("errorMessage", message);
            logger.error("Access to address blocked by AuthFilter ==> " + path +"?"+ req.getQueryString());
            servletRequest.getRequestDispatcher(ConstJSP.ERROR).forward(servletRequest, servletResponse);
        }
    }

    /** Parses param string to list
     *
     * @param params String of parameters to parse
     * @return List of string parameters
     */
    private List<String> asList(String params) {
        List<String> paramList = new ArrayList<>();
        String[] split = params.split(" ");
        for (int i = 0; i < split.length; i++) {
            if (split[i].length() > 1) {
                paramList.add(split[i]);
            }
        }
        return paramList;
    }
}
