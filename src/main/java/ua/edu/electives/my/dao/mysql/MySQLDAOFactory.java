package ua.edu.electives.my.dao.mysql;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.edu.electives.my.dao.CourseDAO;
import ua.edu.electives.my.dao.DAOFactory;
import ua.edu.electives.my.dao.UserDAO;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/** DAO Factory class for MySQL database

 */
public class MySQLDAOFactory extends DAOFactory {

    private static DataSource dataSource;
    private static MySQLDAOFactory INSTANCE;

    private static final Logger logger = LogManager.getLogger(MySQLDAOFactory.class);

    /** Returns singleton instance
     * @return Instance of the singleton */
    public static synchronized MySQLDAOFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MySQLDAOFactory();
        }
        return INSTANCE;
    }

    /** Establishes connection to the database and
    * obtains datasource with pool of connections */
    private MySQLDAOFactory() {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/ConnectionPool");
        } catch (NamingException e) {
            logger.fatal("Can't obtain connection pool. " + e.getMessage());
            throw new IllegalStateException("Data source not found!");
        }
    }

    /** Gets connection from the connection pool
     *
     * @return Connection to the database */
    @Override
    public Connection getConnection() throws SQLException {
        logger.debug("Taking connection from pool");
        return dataSource.getConnection();
    }

    /** Returns instance of User entity DAO for MySQL database
     *
     * @return Instance of MySQL UserDAO */
    @Override
    public UserDAO getUserDAO() {
        return MySQLUserDAO.getInstance();
    }

    /** Returns instance of Course entity DAO for MySQL database
     *
     * @return Instance of MySQL CourseDAO */
    @Override
    public CourseDAO getCourseDAO() {
        return MySQLCourseDAO.getInstance();
    }
}
