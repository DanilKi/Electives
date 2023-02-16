package ua.edu.electives.my.dao;

import ua.edu.electives.my.entity.Course;
import ua.edu.electives.my.entity.CourseStudent;
import ua.edu.electives.my.entity.Topic;
import ua.edu.electives.my.entity.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/** Interface for implementation by CourseDAO classes
    for various DBMSes
 */
public interface CourseDAO {

    void createCourse(Connection conn, Course course) throws SQLException;

    Course getCourse(Connection conn, int id) throws SQLException;

    void updateCourse(Connection conn, Course course) throws SQLException;

    void deleteCourse(Connection conn, int id) throws SQLException;

    List<Course> getCourses(Connection conn, int teacherId, int topicId, String sort, int offset, int limit) throws SQLException;

    int getNumOfCourses(Connection conn, int teacherId, int topicId) throws SQLException;

    int getNumOfTeacherCourses(Connection conn, int teacherId, String status) throws SQLException;

    List<Course> getCoursesByTeacher(Connection conn, int teacherId, String status, String sort, int offset, int limit) throws SQLException;

    List<CourseStudent> getCoursesByStudent(Connection conn, User student, String sort) throws SQLException;

    Topic getTopic(Connection conn, int id) throws SQLException;

    List<Topic> getTopics(Connection conn) throws SQLException;

    void insertTopic(Connection conn, Topic topic) throws SQLException;

    void editTopic(Connection conn, Topic topic) throws SQLException;

    void enrollInCourse(Connection conn, int courseId, int studentId) throws SQLException;
}
