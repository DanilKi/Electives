package ua.edu.electives.my.dao.mysql;

import ua.edu.electives.my.dao.ConstSQL;
import ua.edu.electives.my.dao.CourseDAO;
import ua.edu.electives.my.dao.DBManager;
import ua.edu.electives.my.entity.Course;
import ua.edu.electives.my.entity.CourseStudent;
import ua.edu.electives.my.entity.Topic;
import ua.edu.electives.my.entity.User;
import ua.edu.electives.my.util.DateUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** DAO class for Course entity, for working with MySQL DB

 */
public class MySQLCourseDAO implements CourseDAO {

    private static MySQLCourseDAO INSTANCE;

    public static synchronized MySQLCourseDAO getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MySQLCourseDAO();
        }
        return INSTANCE;
    }

    private MySQLCourseDAO() {}

    /** Inserts new course record to DB
     *
     * @param conn Connection to DB
     * @param course Course to be inserted
     * @throws SQLException */
    @Override
    public void createCourse(Connection conn, Course course) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(ConstSQL.CREATE_COURSE);
            pstmt.setString(1, course.getTitle());
            pstmt.setDate(2, new Date(course.getStartDate().getTime()));
            pstmt.setDate(3, new Date(course.getEndDate().getTime()));
            pstmt.setInt(4, course.getDuration());
            pstmt.setInt(5, course.getTopicId());
            pstmt.setInt(6, course.getTeacherId());
            pstmt.setString(7, course.getDescription());
            pstmt.executeUpdate();
        } finally {
            DBManager.close(pstmt);
        }
    }

    /** Gets course from DB by id
     *
     * @param conn Connection to DB
     * @param id Course id
     * @return Course instance
     * @throws SQLException */
    @Override
    public Course getCourse(Connection conn, int id) throws SQLException {
        Course course = null;
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        try {
            pstmt = conn.prepareStatement(ConstSQL.GET_COURSE);
            pstmt.setInt(1, id);
            rset = pstmt.executeQuery();
            if (rset.next()) {
                course = mapCourse(rset);
            }
        } finally {
            DBManager.close(rset);
            DBManager.close(pstmt);
        }
        return course;
    }

    /** Maps fields of course record from DB to Course object fields
     *
     * @param rset Resultset containing course record from DB
     * @return Course instance
     * @throws SQLException */
    private static Course mapCourse(ResultSet rset) throws SQLException {
        Course course = new Course();
        course.setId(rset.getInt("id"));
        course.setTitle(rset.getString("title"));
        course.setStartDate(rset.getDate("start_date"));
        course.setEndDate(rset.getDate("end_date"));
        course.setDuration(rset.getInt("duration"));
        course.setTopicId(rset.getInt("topic_id"));
        course.setTeacherId(rset.getInt("teacher_id"));
        course.setDescription(rset.getString("description"));
        course.setStudents(rset.getInt("students"));
        return course;
    }

    /** Updates course record in DB
     *
     * @param conn Connection to DB
     * @param course Course instance
     * @throws SQLException */
    @Override
    public void updateCourse(Connection conn, Course course) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(ConstSQL.UPDATE_COURSE);
            pstmt.setString(1, course.getTitle());
            pstmt.setDate(2, new Date(course.getStartDate().getTime()));
            pstmt.setDate(3, new Date(course.getEndDate().getTime()));
            pstmt.setInt(4, course.getDuration());
            pstmt.setInt(5, course.getTopicId());
            pstmt.setInt(6, course.getTeacherId());
            pstmt.setString(7, course.getDescription());
            pstmt.setInt(8, course.getId());
            pstmt.executeUpdate();
        } finally {
            DBManager.close(pstmt);
        }
    }

    /** Deletes course record in DB
     *
     * @param conn Connection to DB
     * @param id Course id
     * @throws SQLException */
    @Override
    public void deleteCourse(Connection conn, int id) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(ConstSQL.DELETE_COURSE);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } finally {
            DBManager.close(pstmt);
        }
    }

    /** Gets courses from DB
     *
     * @param conn Connection to DB
     * @param teacherId Id of a teacher
     * @param topicId Id of a topic
     * @param sort Sorting option
     * @param offset Starting item of a list
     * @param limit Max number of items in a list
     * @return List of Course instances
     * @throws SQLException */
    @Override
    public List<Course> getCourses(Connection conn, int teacherId, int topicId, String sort, int offset, int limit) throws SQLException {
        List<Course> courses = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        String sql = ConstSQL.GET_COURSES;
        if (teacherId != 0 && topicId != 0) {
            sql = sql + " WHERE teacher_id = ? AND topic_id = ?";
        } else if (teacherId == 0 && topicId != 0) {
            sql = sql + " WHERE topic_id = ?";
        } else if (teacherId != 0 && topicId == 0) {
            sql = sql + " WHERE teacher_id = ?";
        }
        switch (sort) {
            case "az": sql = sql + " " + ConstSQL.ORDER_BY_TITLE + " ASC"; break;
            case "za": sql = sql + " " + ConstSQL.ORDER_BY_TITLE + " DESC"; break;
            case "duration": sql = sql + " " + ConstSQL.ORDER_BY_DURATION; break;
            case "students": sql = sql + " " + ConstSQL.ORDER_BY_NUM_OF_STUDENTS + " DESC"; break;
        }
        sql = sql + " " + ConstSQL.OFFSET_LIMIT;
        int i = 1;
        try {
            pstmt = conn.prepareStatement(sql);
            if (teacherId != 0) { pstmt.setInt(i++, teacherId); }
            if (topicId != 0) { pstmt.setInt(i++, topicId); }
            pstmt.setInt(i++, offset);
            pstmt.setInt(i++, limit);
            rset = pstmt.executeQuery();
            while (rset.next()) {
                courses.add(mapCourse(rset));
            }
        } finally {
            DBManager.close(rset);
            DBManager.close(pstmt);
        }
        return courses;
    }

    /** Gets number of courses from DB
     *
     * @param conn Connection to DB
     * @param teacherId Id of a teacher
     * @param topicId Id of a topic
     * @return Number of course records in DB
     * @throws SQLException */
    @Override
    public int getNumOfCourses(Connection conn, int teacherId, int topicId) throws SQLException {
        int numCourses = 0;
        Statement stmt = null;
        ResultSet rset = null;
        String sql = ConstSQL.GET_NUM_OF_COURSES;
        if (teacherId != 0 && topicId != 0) {
            sql = sql + " WHERE teacher_id = " + teacherId + " AND topic_id = " + topicId;
        } else if (teacherId == 0 && topicId != 0) {
            sql = sql + " WHERE topic_id = " + topicId;
        } else if (teacherId != 0 && topicId == 0) {
            sql = sql + " WHERE teacher_id = " + teacherId;
        }
        try {
            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);
            if (rset.next()) {
               numCourses = rset.getInt(1);
            }
        } finally {
            DBManager.close(rset);
            DBManager.close(stmt);
        }
        return numCourses;
    }

    /** Gets number of courses from DB
     *
     * @param conn Connection to DB
     * @param teacherId Id of a teacher
     * @param status Course progress status
     * @return Number of course records in DB
     * @throws SQLException */
    @Override
    public int getNumOfTeacherCourses(Connection conn, int teacherId, String status) throws SQLException {
        int numCourses = 0;
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        String currentDate = DateUtil.getCurrentDateString();
        String sql = ConstSQL.GET_NUM_OF_TEACHER_COURSES;
        switch (status) {
            case "inprogress": sql = sql + " " + ConstSQL.COURSES_IN_PROGRESS; break;
            case "notstarted": sql = sql + " " + ConstSQL.COURSES_NOT_STARTED; break;
            case "finished": sql = sql + " " + ConstSQL.COURSES_COMPLETED; break;
        }
        int i = 1;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(i++, teacherId);
            if (status.equalsIgnoreCase("inprogress")) { pstmt.setString(i++, currentDate); }
            pstmt.setString(i++, currentDate);
            rset = pstmt.executeQuery();
            if (rset.next()) {
                numCourses = rset.getInt(1);
            }
        } finally {
            DBManager.close(rset);
            DBManager.close(pstmt);
        }
        return numCourses;
    }

    /** Gets courses of a certain teacher from DB
     *
     * @param conn Connection to DB
     * @param teacherId Id of the teacher
     * @param status Course progress status
     * @param sort Sorting option
     * @param offset Starting item of a list
     * @param limit Max number of items in a list
     * @return List of Course instances
     * @throws SQLException */
    @Override
    public List<Course> getCoursesByTeacher(Connection conn, int teacherId, String status, String sort, int offset, int limit) throws SQLException {
        List<Course> courses = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        String currentDate = DateUtil.getCurrentDateString();
        String sql = ConstSQL.GET_TEACHER_COURSES;
        switch (status) {
            case "inprogress": sql = sql + " " + ConstSQL.COURSES_IN_PROGRESS; break;
            case "notstarted": sql = sql + " " + ConstSQL.COURSES_NOT_STARTED; break;
            case "finished": sql = sql + " " + ConstSQL.COURSES_COMPLETED; break;
        }
        switch (sort) {
            case "az": sql = sql + " " + ConstSQL.ORDER_BY_TITLE + " ASC"; break;
            case "za": sql = sql + " " + ConstSQL.ORDER_BY_TITLE + " DESC"; break;
            case "duration": sql = sql + " " + ConstSQL.ORDER_BY_DURATION; break;
            case "students": sql = sql + " " + ConstSQL.ORDER_BY_NUM_OF_STUDENTS + " DESC"; break;
        }
        sql = sql + " " + ConstSQL.OFFSET_LIMIT;
        int i = 1;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(i++, teacherId);
            if (status.equalsIgnoreCase("inprogress")) { pstmt.setString(i++, currentDate); }
            pstmt.setString(i++, currentDate);
            pstmt.setInt(i++, offset);
            pstmt.setInt(i++, limit);
            rset = pstmt.executeQuery();
            while (rset.next()) {
                courses.add(mapCourse(rset));
            }
        } finally {
            DBManager.close(rset);
            DBManager.close(pstmt);
        }
        return courses;
    }

    /** Gets courses of a certain student from DB
     *
     * @param conn Connection to DB
     * @param student User entity instance having student role
     * @param sort Sorting option
     * @return List of CourseStudent instances
     * @throws SQLException */
    @Override
    public List<CourseStudent> getCoursesByStudent(Connection conn, User student, String sort) throws SQLException {
        List<CourseStudent> csList = new ArrayList<>();
        CourseStudent courseStudent = null;
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        String sql = ConstSQL.GET_STUDENT_COURSES;
        switch (sort) {
            case "az": sql = sql + " " + ConstSQL.ORDER_BY_TITLE; break;
            case "za": sql = sql + " " + ConstSQL.ORDER_BY_TITLE + " DESC"; break;
            case "duration": sql = sql + " " + ConstSQL.ORDER_BY_DURATION; break;
            case "students": sql = sql + " " + ConstSQL.ORDER_BY_NUM_OF_STUDENTS + " DESC"; break;
        }
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, student.getId());
            rset = pstmt.executeQuery();
            while (rset.next()) {
                courseStudent = new CourseStudent();
                courseStudent.setCourse(getCourse(conn, rset.getInt("course_id")));
                courseStudent.setStudent(student);
                courseStudent.setMark(rset.getInt("mark"));
                csList.add(courseStudent);
            }
        } finally {
            DBManager.close(rset);
            DBManager.close(pstmt);
        }
        return csList;
    }

    /** Gets topic from DB by id
     *
     * @param conn Connection to DB
     * @param id Topic id
     * @return Topic instance
     * @throws SQLException */
    @Override
    public Topic getTopic(Connection conn, int id) throws SQLException {
        Topic topic = new Topic();
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        try {
            pstmt = conn.prepareStatement(ConstSQL.GET_TOPIC);
            pstmt.setInt(1, id);
            rset = pstmt.executeQuery();
            if (rset.next()) {
                topic.setId(rset.getInt("id"));
                topic.setName(rset.getString("name"));
            }
        } finally {
            DBManager.close(rset);
            DBManager.close(pstmt);
        }
        return topic;
    }

    /** Gets topics from DB
     *
     * @param conn Connection to DB
     * @return List of Topic instances
     * @throws SQLException */
    @Override
    public List<Topic> getTopics(Connection conn) throws SQLException {
        List<Topic> topics = new ArrayList<>();
        Topic topic = null;
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        try {
            pstmt = conn.prepareStatement(ConstSQL.GET_TOPICS);
            rset = pstmt.executeQuery();
            while (rset.next()) {
                topic = new Topic();
                topic.setId(rset.getInt("id"));
                topic.setName(rset.getString("name"));
                topics.add(topic);
            }
        } finally {
            DBManager.close(rset);
            DBManager.close(pstmt);
        }
        return topics;
    }

    /** Inserts new topic record to DB
     *
     * @param conn Connection to DB
     * @param topic Topic to be inserted
     * @throws SQLException */
    @Override
    public void insertTopic(Connection conn, Topic topic) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(ConstSQL.INSERT_TOPIC);
            pstmt.setString(1, topic.getName());
            pstmt.executeUpdate();
        } finally {
            DBManager.close(pstmt);
        }
    }

    /** Updates topic record in DB
     *
     * @param conn Connection to DB
     * @param topic Topic instance
     * @throws SQLException */
    @Override
    public void editTopic(Connection conn, Topic topic) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(ConstSQL.EDIT_TOPIC);
            pstmt.setString(1, topic.getName());
            pstmt.setInt(2, topic.getId());
            pstmt.executeUpdate();
        } finally {
            DBManager.close(pstmt);
        }
    }

    /** Enrolls student in the course, inserts new course_student record to DB
     *
     * @param conn Connection to DB
     * @param courseId Course id
     * @param studentId Student id
     * @throws SQLException */
    @Override
    public void enrollInCourse(Connection conn, int courseId, int studentId) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(ConstSQL.ENROLL_IN_COURSE);
            pstmt.setInt(1, courseId);
            pstmt.setInt(2, studentId);
            pstmt.executeUpdate();
        } finally {
            DBManager.close(pstmt);
        }
    }
}
