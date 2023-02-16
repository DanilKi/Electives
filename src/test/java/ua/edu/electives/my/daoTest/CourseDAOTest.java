package ua.edu.electives.my.daoTest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ua.edu.electives.my.dao.ConstSQL;
import ua.edu.electives.my.dao.CourseDAO;
import ua.edu.electives.my.dao.DAOFactory;
import ua.edu.electives.my.dao.DBManager;
import ua.edu.electives.my.dao.mysql.MySQLCourseDAO;
import ua.edu.electives.my.dao.mysql.MySQLDAOFactory;
import ua.edu.electives.my.entity.Course;
import ua.edu.electives.my.entity.Topic;

import java.sql.*;
import java.util.List;

public class CourseDAOTest {
    private Connection conn;
    private DAOFactory daoFactory;
    private CourseDAO courseDAO;
    private PreparedStatement pstmt;
    private Statement stmt;

    @BeforeEach
    public void initTest() {
        conn = Mockito.mock(Connection.class);
        daoFactory = Mockito.mock(MySQLDAOFactory.class);
        courseDAO = MySQLCourseDAO.getInstance();
        pstmt = Mockito.mock(PreparedStatement.class);
        stmt = Mockito.mock(Statement.class);
        Mockito.when(daoFactory.getCourseDAO()).thenReturn(courseDAO);
        try {
            Mockito.when(daoFactory.getConnection()).thenReturn(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void closeTest() {
        DBManager.close(stmt);
        DBManager.close(pstmt);
        DBManager.close(conn);
    }

    @Test
    public void testGetTopics() throws SQLException {
        ResultSet rset = Mockito.mock(ResultSet.class);
        Mockito.when(conn.prepareStatement(ConstSQL.GET_TOPICS)).thenReturn(pstmt);
        Mockito.when(pstmt.executeQuery()).thenReturn(rset);
        Mockito.when(rset.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        Mockito.when(rset.getString("name")).thenReturn("Java").thenReturn("JavaScript").thenReturn("Python");
        DBManager.close(rset);

        List<Topic> topics = courseDAO.getTopics(conn);
        Assertions.assertNotNull(topics);
        Assertions.assertEquals(3, topics.size());
    }

    @Test
    public void testGetNumOfCourses() throws SQLException {
        ResultSet rset = Mockito.mock(ResultSet.class);
        Mockito.when(conn.createStatement()).thenReturn(stmt);
        Mockito.when(stmt.executeQuery(ConstSQL.GET_NUM_OF_COURSES)).thenReturn(rset);
        Mockito.when(rset.next()).thenReturn(true).thenReturn(false);
        Mockito.when(rset.getInt(1)).thenReturn(30);
        DBManager.close(rset);

        int numOfCourses = courseDAO.getNumOfCourses(conn, 0, 0);
        Assertions.assertEquals(30, numOfCourses);
    }

    @Test
    public void testGetCourse() throws SQLException {
        ResultSet rset = Mockito.mock(ResultSet.class);
        Mockito.when(conn.prepareStatement(ConstSQL.GET_COURSE)).thenReturn(pstmt);
        Mockito.when(pstmt.executeQuery()).thenReturn(rset);
        Mockito.when(rset.next()).thenReturn(true);
        Mockito.when(rset.getInt("id")).thenReturn(1);
        Mockito.when(rset.getString("title")).thenReturn("Beginner Java course");
        DBManager.close(rset);

        Course course = courseDAO.getCourse(conn, 1);
        Assertions.assertNotEquals("Beginner Java", course.getTitle());
        Assertions.assertEquals(1, course.getId());
    }

    @Test
    public void testGetCourseIsNull() throws SQLException {
        ResultSet rset = Mockito.mock(ResultSet.class);
        Mockito.when(conn.prepareStatement(ConstSQL.GET_COURSE)).thenReturn(pstmt);
        Mockito.when(pstmt.executeQuery()).thenReturn(rset);
        Mockito.when(rset.next()).thenReturn(false);
        DBManager.close(rset);

        Course course = courseDAO.getCourse(conn, 50);
        Assertions.assertNull(course);
    }

    @Test
    public void testGetCourses() throws SQLException {
        ResultSet rset = Mockito.mock(ResultSet.class);
        Mockito.when(conn.prepareStatement(ConstSQL.GET_COURSES + " " + ConstSQL.OFFSET_LIMIT)).thenReturn(pstmt);
        Mockito.when(pstmt.executeQuery()).thenReturn(rset);
        Mockito.when(rset.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        Mockito.when(rset.getString("title")).thenReturn("Beginner Java course")
                        .thenReturn("Basic Java course").thenReturn("Advanced Java course");
        DBManager.close(rset);

        List<Course> courses = courseDAO.getCourses(conn, 0, 0, "none", 0, 10);
        Assertions.assertNotNull(courses);
        Assertions.assertEquals(3, courses.size());
    }

    @Test
    public void testGetCoursesCheckTitle() throws SQLException {
        ResultSet rset = Mockito.mock(ResultSet.class);
        Mockito.when(conn.prepareStatement(ConstSQL.GET_COURSES + " " + ConstSQL.OFFSET_LIMIT)).thenReturn(pstmt);
        Mockito.when(pstmt.executeQuery()).thenReturn(rset);
        Mockito.when(rset.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        Mockito.when(rset.getString("title")).thenReturn("Beginner Java course")
                        .thenReturn("Basic Java course").thenReturn("Advanced Java course");
        DBManager.close(rset);

        List<Course> courses = courseDAO.getCourses(conn, 0, 0, "none", 0, 10);
        Assertions.assertEquals("Basic Java course", courses.get(1).getTitle());
    }

    @Test
    public void testGetCoursesByTeacher() throws SQLException {
        ResultSet rset = Mockito.mock(ResultSet.class);
        Mockito.when(conn.prepareStatement(ConstSQL.GET_TEACHER_COURSES + " " + ConstSQL.COURSES_COMPLETED
                                                            + " " + ConstSQL.OFFSET_LIMIT)).thenReturn(pstmt);
        Mockito.when(pstmt.executeQuery()).thenReturn(rset);
        Mockito.when(rset.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        Mockito.when(rset.getString("title")).thenReturn("Basic Python course").thenReturn("Advanced PHP course");
        DBManager.close(rset);

        List<Course> courses = courseDAO.getCoursesByTeacher(conn, 2, "finished", "none", 0, 10);
        Assertions.assertEquals(2, courses.size());
    }

}
