package ua.edu.electives.my.dao;

import ua.edu.electives.my.entity.Course;
import ua.edu.electives.my.entity.CourseStudent;
import ua.edu.electives.my.entity.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/** Interface for implementation by UserDAO classes
    for various DBMSes
 */
public interface UserDAO {

    void registerUser(Connection conn, User user) throws SQLException;

    User getUser(Connection conn, String email) throws SQLException;

    User getUser(Connection conn, int id) throws SQLException;

    void updateUser(Connection conn, User user) throws SQLException;

    List<User> getUsers(Connection conn, int roleId, String sort, int offset, int limit) throws SQLException;

    int getNumOfUsers(Connection conn, int roleId) throws SQLException;

    List<User> getTeachers(Connection conn) throws SQLException;

    List<CourseStudent> getCourseStudents(Connection conn, Course course) throws SQLException;

    void updateCourseStudents(Connection conn, int courseId, int studentId, int mark) throws SQLException;
}
