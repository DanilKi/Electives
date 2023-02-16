package ua.edu.electives.my.dao.mysql;

import ua.edu.electives.my.dao.ConstSQL;
import ua.edu.electives.my.dao.DBManager;
import ua.edu.electives.my.dao.UserDAO;
import ua.edu.electives.my.entity.Course;
import ua.edu.electives.my.entity.CourseStudent;
import ua.edu.electives.my.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** DAO class for User entity, for working with MySQL DB

 */
public class MySQLUserDAO implements UserDAO {

    private static MySQLUserDAO INSTANCE;

    public static synchronized MySQLUserDAO getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MySQLUserDAO();
        }
        return INSTANCE;
    }

    private MySQLUserDAO() {}

    /** Inserts new user record to DB
     *
     * @param conn Connection to DB
     * @param user User to be inserted
     * @throws SQLException */
    @Override
    public void registerUser(Connection conn, User user) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(ConstSQL.REGISTER_USER);
            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getFirstName());
            pstmt.setString(4, user.getLastName());
            pstmt.setInt(5, user.getRoleId());
            pstmt.setBoolean(6, user.isBlocked());
            pstmt.executeUpdate();
        } finally {
            DBManager.close(pstmt);
        }
    }

    /** Gets user from DB by email
     *
     * @param conn Connection to DB
     * @param email Email of user
     * @return User instance
     * @throws SQLException */
    @Override
    public User getUser(Connection conn, String email) throws SQLException {
        User user = null;
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        try {
            pstmt = conn.prepareStatement(ConstSQL.GET_USER_BY_EMAIL);
            pstmt.setString(1, email);
            rset = pstmt.executeQuery();
            if (rset.next()) {
                user = mapUser(rset);
            }
        } finally {
            DBManager.close(rset);
            DBManager.close(pstmt);
        }
        return user;
    }

    /** Maps fields of user record from DB to User object fields
     *
     * @param rset Resultset containing user record from DB
     * @return User instance
     * @throws SQLException */
    private static User mapUser(ResultSet rset) throws SQLException {
        User user = new User();
        user.setId(rset.getInt("id"));
        user.setEmail(rset.getString("email"));
        user.setPassword(rset.getString("password"));
        user.setFirstName(rset.getString("f_name"));
        user.setLastName(rset.getString("l_name"));
        user.setRoleId(rset.getInt("role_id"));
        user.setBlocked(rset.getBoolean("is_blocked"));
        return user;
    }

    /** Gets user from DB by id
     *
     * @param conn Connection to DB
     * @param id Id of user
     * @return User instance
     * @throws SQLException */
    @Override
    public User getUser(Connection conn, int id) throws SQLException {
        User user = null;
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        try {
            pstmt = conn.prepareStatement(ConstSQL.GET_USER_BY_ID);
            pstmt.setInt(1, id);
            rset = pstmt.executeQuery();
            if (rset.next()) {
                user = mapUser(rset);
            }
        } finally {
            DBManager.close(rset);
            DBManager.close(pstmt);
        }
        return user;
    }

    /** Updates user record in DB
     *
     * @param conn Connection to DB
     * @param user User instance
     * @throws SQLException */
    @Override
    public void updateUser(Connection conn, User user) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(ConstSQL.UPDATE_USER);
            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getFirstName());
            pstmt.setString(4, user.getLastName());
            pstmt.setInt(5, user.getRoleId());
            pstmt.setBoolean(6, user.isBlocked());
            pstmt.setInt(7, user.getId());
            pstmt.executeUpdate();
        } finally {
            DBManager.close(pstmt);
        }
    }

    /** Gets users from DB
     *
     * @param conn Connection to DB
     * @param roleId Role id of a user
     * @param sort Sorting option
     * @param offset Starting item of a list
     * @param limit Max number of items in a list
     * @return List of User instances
     * @throws SQLException */
    @Override
    public List<User> getUsers(Connection conn, int roleId, String sort, int offset, int limit) throws SQLException {
        List<User> users = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        String sql = ConstSQL.GET_USERS;
        switch (sort) {
            case "az": sql = sql + " " + ConstSQL.ORDER_BY_NAME; break;
            case "za": sql = sql + " " + ConstSQL.ORDER_BY_NAME_DESC; break;
        }
        sql = sql + " " + ConstSQL.OFFSET_LIMIT;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, roleId);
            pstmt.setInt(2, offset);
            pstmt.setInt(3, limit);
            rset = pstmt.executeQuery();
            while (rset.next()) {
                users.add(mapUser(rset));
            }
        } finally {
            DBManager.close(rset);
            DBManager.close(pstmt);
        }
        return users;
    }

    /** Gets number of users from DB
     *
     * @param conn Connection to DB
     * @param roleId Role id of a user
     * @return Number of user records in DB
     * @throws SQLException */
    @Override
    public int getNumOfUsers(Connection conn, int roleId) throws SQLException {
        int numUsers = 0;
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        try {
            pstmt = conn.prepareStatement(ConstSQL.GET_NUM_OF_USERS);
            pstmt.setInt(1, roleId);
            rset = pstmt.executeQuery();
            if (rset.next()) {
                numUsers = rset.getInt(1);
            }
        } finally {
            DBManager.close(rset);
            DBManager.close(pstmt);
        }
        return numUsers;
    }

    /** Gets teachers from DB
     *
     * @param conn Connection to DB
     * @return List of User instances with teacher role
     * @throws SQLException */
    @Override
    public List<User> getTeachers(Connection conn) throws SQLException {
        List<User> teachers = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        try {
            pstmt = conn.prepareStatement(ConstSQL.GET_TEACHERS);
            rset = pstmt.executeQuery();
            while (rset.next()) {
                teachers.add(mapUser(rset));
            }
        } finally {
            DBManager.close(rset);
            DBManager.close(pstmt);
        }
        return teachers;
    }

    /** Gets students of a certain course from DB
     *
     * @param conn Connection to DB
     * @param course Course entity instance
     * @return List of CourseStudent instances
     * @throws SQLException */
    @Override
    public List<CourseStudent> getCourseStudents(Connection conn, Course course) throws SQLException {
        List<CourseStudent> csList = new ArrayList<>();
        CourseStudent courseStudent = null;
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        try {
            pstmt = conn.prepareStatement(ConstSQL.GET_COURSE_STUDENTS);
            pstmt.setInt(1, course.getId());
            rset = pstmt.executeQuery();
            while (rset.next()) {
                courseStudent = new CourseStudent();
                courseStudent.setCourse(course);
                courseStudent.setStudent(getUser(conn, rset.getInt("student_id")));
                courseStudent.setMark(rset.getInt("mark"));
                csList.add(courseStudent);
            }
        } finally {
            DBManager.close(rset);
            DBManager.close(pstmt);
        }
        return csList;
    }

    /** Sets mark for student for a certain course in DB
     *
     * @param conn Connection to DB
     * @param courseId Course id
     * @param studentId Student id
     * @param mark Grade given to a student
     * @throws SQLException */
    @Override
    public void updateCourseStudents(Connection conn, int courseId, int studentId, int mark) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(ConstSQL.UPDATE_MARK);
            pstmt.setInt(1, mark);
            pstmt.setInt(2, courseId);
            pstmt.setInt(3, studentId);
            pstmt.executeUpdate();
        } finally {
            DBManager.close(pstmt);
        }
    }
}
