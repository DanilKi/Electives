package ua.edu.electives.my.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.edu.electives.my.entity.Course;
import ua.edu.electives.my.entity.CourseStudent;
import ua.edu.electives.my.entity.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/** Service class for User entity, for working with DB using DAO

 */
public class UserManager {
    private static UserManager INSTANCE;
    private DAOFactory daoFactory;
    private UserDAO userDAO;

    private static final Logger logger = LogManager.getLogger(UserManager.class);

    public static synchronized UserManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserManager();
        }
        return INSTANCE;
    }

    private UserManager() {
        daoFactory = DAOFactory.getDAOFactory(DBManager.DBMS);
        userDAO = daoFactory.getUserDAO();
    }

    /** Gets user by email
     *
     * @param email Email of user
     * @return User instance
     * @throws DBException */
    public User getUser(String email) throws DBException {
        Connection conn = null;
        User user = null;
        try {
            conn = daoFactory.getConnection();
            user = userDAO.getUser(conn, email);
            logger.info("Found user by email: " + email);
        } catch (SQLException e) {
            logger.error("Cannot find user by email: " + email + ". " + e.getMessage());
            throw new DBException("Cannot find user. Try again", e);
        } finally {
            DBManager.close(conn);
        }
        return user;
    }

    /** Gets user by id
     *
     * @param id Id of user
     * @return User instance
     * @throws DBException */
    public User getUser(int id) throws DBException {
        Connection conn = null;
        User user = null;
        try {
            conn = daoFactory.getConnection();
            user = userDAO.getUser(conn, id);
            logger.info("Found user by id: " + id);
        } catch (SQLException e) {
            logger.error("Cannot find user by id: " + id + ". " + e.getMessage());
            throw new DBException("Cannot find user", e);
        } finally {
            DBManager.close(conn);
        }
        return user;
    }

    /** Registers new user (creates new user tuple in DB)
     *
     * @param user User to be registered
     * @throws DBException */
    public void registerUser(User user) throws DBException {
        Connection conn = null;
        try {
            conn = daoFactory.getConnection();
            userDAO.registerUser(conn, user);
            DBManager.commitTransaction(conn);
            logger.info("New user registered: " + user);
        } catch (SQLException e) {
            logger.error("Cannot create new user. " + e.getMessage());
            DBManager.rollbackTransaction(conn);
            throw new DBException("Cannot register new user", e);
        }
    }

    /** Updates user data
     *
     * @param user User instance
     * @throws DBException */
    public void updateUser(User user) throws DBException {
        Connection conn = null;
        try {
            conn = daoFactory.getConnection();
            userDAO.updateUser(conn, user);
            logger.info("User updated: " + user);
            DBManager.commitTransaction(conn);
        } catch (SQLException e) {
            logger.error("Cannot update user " + user.getEmail() + ". " + e.getMessage());
            DBManager.rollbackTransaction(conn);
            throw new DBException("Cannot save changes", e);
        }
    }

    /** Gets users list
     *
     * @param roleId Role id of a user
     * @param sort Sorting option
     * @param offset Starting item of a list
     * @param limit Max number of items in a list
     * @return List of User instances
     * @throws DBException */
    public List<User> getUsers(int roleId, String sort, int offset, int limit) throws DBException {
        List<User> users = null;
        Connection conn = null;
        try {
            conn = daoFactory.getConnection();
            users = userDAO.getUsers(conn, roleId, sort, offset, limit);
            logger.info("Received list of users successfully");
        } catch (SQLException e) {
            logger.error("Cannot get list of users. " + e.getMessage());
            throw new DBException("Cannot get users list", e);
        } finally {
            DBManager.close(conn);
        }
        return users;
    }

    /** Gets number of users
     *
     * @param roleId Role id of a user
     * @return Number of users in DB
     * @throws DBException */
    public int getNumOfUsers(int roleId) throws DBException {
        int numUsers = 0;
        Connection conn = null;
        try {
            conn = daoFactory.getConnection();
            numUsers = userDAO.getNumOfUsers(conn, roleId);
            logger.debug("Number of users received successfully");
        } catch (SQLException e) {
            logger.error("Cannot get number of users. " + e.getMessage());
            throw new DBException("Cannot get number of users", e);
        } finally {
            DBManager.close(conn);
        }
        return numUsers;
    }

    /** Gets all teachers
     *
     * @return List of User instances with teacher role
     * @throws DBException */
    public List<User> getTeachers() throws DBException {
        List<User> teachers = null;
        Connection conn = null;
        try {
            conn = daoFactory.getConnection();
            teachers = userDAO.getTeachers(conn);
            logger.info("Received list of all teachers successfully");
        } catch (SQLException e) {
            logger.error("Cannot get list of all teachers. " + e.getMessage());
            throw new DBException("Cannot get teachers list", e);
        } finally {
            DBManager.close(conn);
        }
        return teachers;
    }

    /** Gets students of a certain course
     *
     * @param course Course entity instance
     * @return List of CourseStudent instances
     * @throws DBException */
    public List<CourseStudent> getCourseStudents(Course course) throws DBException {
        List<CourseStudent> csList = null;
        Connection conn = null;
        try {
            conn = daoFactory.getConnection();
            csList = userDAO.getCourseStudents(conn, course);
            logger.info("Students of course " + course.getTitle() + " received");
        } catch (SQLException e) {
            logger.error("Cannot get students of course " + course.getTitle() + ". " + e.getMessage());
            throw new DBException("Cannot get students of course", e);
        } finally {
            DBManager.close(conn);
        }
        return csList;
    }

    /**Sets mark for student for a certain course
     *
     * @param courseId Course id
     * @param studentId Student id
     * @param mark Grade given to a student
     * @throws DBException */
    public void updateCourseStudents(int courseId, int studentId, int mark) throws DBException {
        Connection conn = null;
        try {
            conn = daoFactory.getConnection();
            userDAO.updateCourseStudents(conn, courseId, studentId, mark);
            logger.info("Mark for student with id: '" + studentId + "' set successfully");
            DBManager.commitTransaction(conn);
        } catch (SQLException e) {
            logger.error("Cannot set mark for student. " + e.getMessage());
            DBManager.rollbackTransaction(conn);
            throw new DBException("Cannot save mark", e);
        }
    }
}
