package ua.edu.electives.my.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.edu.electives.my.dao.mysql.MySQLDAOFactory;

import java.sql.Connection;
import java.sql.SQLException;

/** Data Access Object Factory abstract class

 */
public abstract class DAOFactory {
    private static final String MYSQL = "MySQL";

    private static final Logger logger = LogManager.getLogger(DAOFactory.class);

    public static DAOFactory getDAOFactory(String dbmsName) {
        if (MYSQL.equalsIgnoreCase(dbmsName)) {
            logger.info("Used DBMS name is MySQL");
            return MySQLDAOFactory.getInstance();
        }
        logger.error("Unknown DBMS name used");
        throw new IllegalArgumentException("Incorrect DAOFactory name");
    }

    public abstract Connection getConnection() throws SQLException;

    public abstract UserDAO getUserDAO();

    public abstract CourseDAO getCourseDAO();
}
