package ua.edu.electives.my.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

/**Class contains common methods for working with DB objects

 */
public class DBManager {

    public static final String DBMS = "MySQL";

    private static final Logger logger = LogManager.getLogger(DBManager.class);

    /** Closes connection, statement or resultset
     * @param closeable object of type AutoCloseable to be closed */
    public static void close(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                if (closeable.getClass().getSimpleName().equals("PoolGuardConnectionWrapper")) {
                    logger.debug("Connection returned to pool");
                }
                closeable.close();
            } catch (Exception e) {
                logger.error("Can't close resource. " + e.getMessage());
            }
        }
    }

    /** Commits and returns connection to connection pool
     * @param conn Connection to be committed and returned */
    public static void commitTransaction(Connection conn) {
        if (conn != null) {
            try {
                conn.commit();
                conn.close();
                logger.debug("Connection committed and returned to pool");
            } catch (SQLException e) {
                logger.error("Can't commit and return connection to pool. " + e.getMessage());
            }
        }
    }

    /** Rollbacks and returns connection to connection pool
     * @param conn Connection to be rollbacked and returned */
    public static void rollbackTransaction(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
                conn.close();
                logger.debug("Connection rollbacked and returned to pool");
            } catch (SQLException e) {
                logger.error("Can't rollback and return connection to pool. " + e.getMessage());
            }
        }
    }

}
