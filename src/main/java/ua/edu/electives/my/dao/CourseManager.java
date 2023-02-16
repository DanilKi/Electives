package ua.edu.electives.my.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.edu.electives.my.entity.Course;
import ua.edu.electives.my.entity.CourseStudent;
import ua.edu.electives.my.entity.Topic;
import ua.edu.electives.my.entity.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/** Service class for Course entity, for working with DB using DAO

 */
public class CourseManager {
    private static CourseManager INSTANCE;
    private DAOFactory daoFactory;
    private CourseDAO courseDAO;

    private static final Logger logger = LogManager.getLogger(CourseManager.class);

    public static synchronized CourseManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CourseManager();
        }
        return INSTANCE;
    }

    private CourseManager() {
        daoFactory = DAOFactory.getDAOFactory(DBManager.DBMS);
        courseDAO = daoFactory.getCourseDAO();
    }

    /** Creates new course (insert new course tuple in DB)
     *
     * @param course Course to be created
     * @throws DBException */
    public void createCourse(Course course) throws DBException {
        Connection conn = null;
        try {
            conn = daoFactory.getConnection();
            courseDAO.createCourse(conn, course);
            DBManager.commitTransaction(conn);
            logger.info("New course created: " + course);
        } catch (SQLException e) {
            logger.error("Cannot create new course " + course.getTitle() + ". " + e.getMessage());
            DBManager.rollbackTransaction(conn);
            throw new DBException("Cannot create new course", e);
        }
    }

    /** Gets course by id.
     *
     * @param id Course id
     * @return Course instance
     * @throws DBException */
    public Course getCourse(int id) throws DBException {
        Connection conn = null;
        Course course = null;
        try {
            conn = daoFactory.getConnection();
            course = courseDAO.getCourse(conn, id);
            logger.info("Found course with id: " + id);
        } catch (SQLException e) {
            logger.error("Cannot find course with id " + id + ". " + e.getMessage());
            throw new DBException("Cannot find course", e);
        } finally {
            DBManager.close(conn);
        }
        return course;
    }

    /** Updates course data
     *
     * @param course Course instance
     * @throws DBException */
    public void updateCourse(Course course) throws DBException {
        Connection conn = null;

        try {
            conn = daoFactory.getConnection();
            courseDAO.updateCourse(conn, course);
            DBManager.commitTransaction(conn);
            logger.info("Updated course: " + course);
        } catch (SQLException e) {
            logger.error("Cannot update course " + course.getTitle() + ". " + e.getMessage());
            DBManager.rollbackTransaction(conn);
            throw new DBException("Cannot save changes to course", e);
        }
    }

    /** Deletes course
     *
     * @param id Course id
     * @throws DBException */
    public void deleteCourse(int id) throws DBException {
        Connection conn = null;
        try {
            conn = daoFactory.getConnection();
            courseDAO.deleteCourse(conn, id);
            DBManager.commitTransaction(conn);
            logger.info("Deleted course with id: " + id);
        } catch (SQLException e) {
            logger.error("Cannot delete course by id: " + id + ". " + e.getMessage());
            DBManager.rollbackTransaction(conn);
            throw new DBException("Cannot remove course", e);
        }
    }

    /** Gets courses list
     *
     * @param teacherId Id of a teacher
     * @param topicId Id of a topic
     * @param sort Sorting option
     * @param offset Starting item of a list
     * @param limit Max number of items in a list
     * @return List of Course instances
     * @throws DBException */
    public List<Course> getCourses(int teacherId, int topicId, String sort, int offset, int limit) throws DBException {
        List<Course> courses = null;
        Connection conn = null;
        try {
            conn = daoFactory.getConnection();
            courses = courseDAO.getCourses(conn, teacherId, topicId, sort, offset, limit);
            logger.info("List of courses received successfully");
        } catch (SQLException e) {
            logger.error("Cannot get courses list. " + e.getMessage());
            throw new DBException("Cannot get courses list", e);
        } finally {
            DBManager.close(conn);
        }
        return courses;
    }

    /** Gets number of courses
     *
     * @param teacherId Id of a teacher
     * @param topicId Id of a topic
     * @return Number of courses in DB
     * @throws DBException */
    public int getNumOfCourses(int teacherId, int topicId) throws DBException {
        int numCourses = 0;
        Connection conn = null;
        try {
            conn = daoFactory.getConnection();
            numCourses = courseDAO.getNumOfCourses(conn, teacherId, topicId);
            logger.debug("Number of courses received successfully");
        } catch (SQLException e) {
            logger.error("Cannot get number of courses. " + e.getMessage());
            throw new DBException("Cannot get number of courses", e);
        } finally {
            DBManager.close(conn);
        }
        return numCourses;
    }

    /** Gets number of courses
     *
     * @param teacherId Id of a teacher
     * @param status Course progress status
     * @return Number of courses in DB
     * @throws DBException */
    public int getNumOfTeacherCourses(int teacherId, String status) throws DBException {
        int numCourses = 0;
        Connection conn = null;
        try {
            conn = daoFactory.getConnection();
            numCourses = courseDAO.getNumOfTeacherCourses(conn, teacherId, status);
            logger.debug("Number of courses received successfully");
        } catch (SQLException e) {
            logger.error("Cannot get number of courses. " + e.getMessage());
            throw new DBException("Cannot get number of courses", e);
        } finally {
            DBManager.close(conn);
        }
        return numCourses;
    }

    /** Gets courses list of a certain teacher
     *
     * @param teacherId Id of the teacher
     * @param status Course progress status
     * @param sort Sorting option
     * @param offset Starting item of a list
     * @param limit Max number of items in a list
     * @return List of Course instances
     * @throws DBException */
    public List<Course> getCoursesByTeacher(int teacherId, String status, String sort, int offset, int limit) throws DBException {
        List<Course> courses = null;
        Connection conn = null;
        try {
            conn = daoFactory.getConnection();
            courses = courseDAO.getCoursesByTeacher(conn, teacherId, status, sort, offset, limit);
            logger.info("Found teacher's courses by id: " + teacherId);
        } catch (SQLException e) {
            logger.error("Cannot find courses by teacher id: " + teacherId + ". " + e.getMessage());
            throw new DBException("Cannot find teacher's courses", e);
        } finally {
            DBManager.close(conn);
        }
        return courses;
    }

    /** Gets courses list of a certain student
     *
     * @param student User entity instance having student role
     * @param sort Sorting option
     * @return List of CourseStudent instances
     * @throws DBException */
    public List<CourseStudent> getCoursesByStudent(User student, String sort) throws DBException {
        List<CourseStudent> csList = null;
        Connection conn = null;
        try {
            conn = daoFactory.getConnection();
            csList = courseDAO.getCoursesByStudent(conn, student, sort);
            logger.info("Found courses list for student " + student.getEmail());
        } catch (SQLException e) {
            logger.error("Cannot find courses for student " + student.getEmail() + ". " + e.getMessage());
            throw new DBException("Cannot find student's courses", e);
        } finally {
            DBManager.close(conn);
        }
        return csList;
    }

    /** Gets topic by id.
     *
     * @param id Topic id
     * @return Topic instance
     * @throws DBException */
    public Topic getTopic(int id) throws DBException {
        Connection conn = null;
        Topic topic = null;
        try {
            conn = daoFactory.getConnection();
            topic = courseDAO.getTopic(conn, id);
            logger.info("Found topic with id: " + id);
        } catch (SQLException e) {
            logger.error("Cannot find topic with id " + id + ". " + e.getMessage());
            throw new DBException("Cannot find topic", e);
        } finally {
            DBManager.close(conn);
        }
        return topic;
    }

    /** Gets topics list
     *
     * @return List of Topic instances
     * @throws DBException */
    public List<Topic> getTopics() throws DBException {
        List<Topic> topics = null;
        Connection conn = null;
        try {
            conn = daoFactory.getConnection();
            topics = courseDAO.getTopics(conn);
            logger.info("List of topics received successfully");
        } catch (SQLException e) {
            logger.error("Cannot get topics list. " + e.getMessage());
            throw new DBException("Cannot get topics list", e);
        } finally {
            DBManager.close(conn);
        }
        return topics;
    }

    /** Creates new topic (insert new topic tuple in DB)
     *
     * @param topic Topic to be created
     * @throws DBException */
    public void insertTopic(Topic topic) throws DBException {
        Connection conn = null;
        try {
            conn = daoFactory.getConnection();
            courseDAO.insertTopic(conn, topic);
            DBManager.commitTransaction(conn);
            logger.info("New topic created: " + topic);
        } catch (SQLException e) {
            logger.error("Cannot create new topic " + topic.getName() + ". " + e.getMessage());
            DBManager.rollbackTransaction(conn);
            throw new DBException("Cannot create new topic", e);
        }
    }

    /** Updates topic data
     *
     * @param topic Topic instance
     * @throws DBException */
    public void editTopic(Topic topic) throws DBException {
        Connection conn = null;
        try {
            conn = daoFactory.getConnection();
            courseDAO.editTopic(conn, topic);
            DBManager.commitTransaction(conn);
            logger.info("Updated topic: " + topic);
        } catch (SQLException e) {
            logger.error("Cannot update topic " + topic.getName() + ". " + e.getMessage());
            DBManager.rollbackTransaction(conn);
            throw new DBException("Cannot save changes to topic", e);
        }
    }

    /** Enrolls student in the course
     *
     * @param courseId Course id
     * @param studentId Student id
     * @throws DBException */
    public void enrollInCourse(int courseId, int studentId) throws DBException {
        Connection conn = null;
        try {
            conn = daoFactory.getConnection();
            courseDAO.enrollInCourse(conn, courseId, studentId);
            DBManager.commitTransaction(conn);
            logger.info("Student with id: " + studentId + " enrolled in course with id: " + courseId + " successfully");
        } catch (SQLException e) {
            logger.error("Cannot enroll student (id: " + studentId + ") in course (id: " + courseId + "). " + e.getMessage());
            DBManager.rollbackTransaction(conn);
            throw new DBException("Cannot enroll in course", e);
        }
    }
}
