package ua.edu.electives.my.daoTest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ua.edu.electives.my.dao.ConstSQL;
import ua.edu.electives.my.dao.DAOFactory;
import ua.edu.electives.my.dao.DBManager;
import ua.edu.electives.my.dao.UserDAO;
import ua.edu.electives.my.dao.mysql.MySQLDAOFactory;
import ua.edu.electives.my.dao.mysql.MySQLUserDAO;
import ua.edu.electives.my.entity.Role;
import ua.edu.electives.my.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDAOTest {
    private Connection conn;
    private DAOFactory daoFactory;
    private UserDAO userDAO;
    private PreparedStatement pstmt;

    @BeforeEach
    public void initTest() {
        conn = Mockito.mock(Connection.class);
        daoFactory = Mockito.mock(MySQLDAOFactory.class);
        userDAO = MySQLUserDAO.getInstance();
        pstmt = Mockito.mock(PreparedStatement.class);
        Mockito.when(daoFactory.getUserDAO()).thenReturn(userDAO);
        try {
            Mockito.when(daoFactory.getConnection()).thenReturn(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void closeTest() {
        DBManager.close(pstmt);
        DBManager.close(conn);
    }

    @Test
    public void testGetUsers() throws SQLException {
        ResultSet rset = Mockito.mock(ResultSet.class);
        Mockito.when(conn.prepareStatement(ConstSQL.GET_USERS + " " + ConstSQL.OFFSET_LIMIT)).thenReturn(pstmt);
        Mockito.when(pstmt.executeQuery()).thenReturn(rset);
        Mockito.when(rset.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        Mockito.when(rset.getString("email")).thenReturn("admin@gmail.com")
                    .thenReturn("teacher@gmail.com").thenReturn("student@gmail.com");
        DBManager.close(rset);

        List<User> users = userDAO.getUsers(conn, 0, "none", 0, 10);
        Assertions.assertEquals(3, users.size());
    }

    @Test
    public void testGetUsersCheckUser() throws SQLException {
        ResultSet rset = Mockito.mock(ResultSet.class);
        Mockito.when(conn.prepareStatement(ConstSQL.GET_USERS + " " + ConstSQL.OFFSET_LIMIT)).thenReturn(pstmt);
        Mockito.when(pstmt.executeQuery()).thenReturn(rset);
        Mockito.when(rset.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        Mockito.when(rset.getString("email")).thenReturn("admin@gmail.com")
                        .thenReturn("teacher@gmail.com").thenReturn("student@gmail.com");
        Mockito.when(rset.getInt("role_id")).thenReturn(0).thenReturn(1).thenReturn(2);
        DBManager.close(rset);

        List<User> users = userDAO.getUsers(conn, 0, "none", 0, 10);
        Assertions.assertEquals("admin@gmail.com", users.get(0).getEmail());
        Assertions.assertEquals("admin", Role.getRole(users.get(0)).getName());
    }

    @Test
    public void testGetUserById() throws SQLException {
        ResultSet rset = Mockito.mock(ResultSet.class);
        Mockito.when(conn.prepareStatement(ConstSQL.GET_USER_BY_ID)).thenReturn(pstmt);
        Mockito.when(pstmt.executeQuery()).thenReturn(rset);
        Mockito.when(rset.next()).thenReturn(true);
        Mockito.when(rset.getInt("id")).thenReturn(5);
        DBManager.close(rset);

        User user = userDAO.getUser(conn, 5);
        Assertions.assertEquals(5, user.getId());
    }

    @Test
    public void testGetUserByIdIsNull() throws SQLException {
        ResultSet rset = Mockito.mock(ResultSet.class);
        Mockito.when(conn.prepareStatement(ConstSQL.GET_USER_BY_ID)).thenReturn(pstmt);
        Mockito.when(pstmt.executeQuery()).thenReturn(rset);
        Mockito.when(rset.next()).thenReturn(false);
        DBManager.close(rset);

        User user = userDAO.getUser(conn, 100);
        Assertions.assertNull(user);
    }

    @Test
    public void testGetUserByEmail() throws SQLException {
        ResultSet rset = Mockito.mock(ResultSet.class);
        Mockito.when(conn.prepareStatement(ConstSQL.GET_USER_BY_EMAIL)).thenReturn(pstmt);
        Mockito.when(pstmt.executeQuery()).thenReturn(rset);
        Mockito.when(rset.next()).thenReturn(true);
        Mockito.when(rset.getString("email")).thenReturn("student@gmail.com");
        DBManager.close(rset);

        User user = userDAO.getUser(conn, "student@gmail.com");
        Assertions.assertEquals("student@gmail.com", user.getEmail());
    }

    @Test
    public void testGetUserByEmailIsNull() throws SQLException {
        ResultSet rset = Mockito.mock(ResultSet.class);
        Mockito.when(conn.prepareStatement(ConstSQL.GET_USER_BY_EMAIL)).thenReturn(pstmt);
        Mockito.when(pstmt.executeQuery()).thenReturn(rset);
        Mockito.when(rset.next()).thenReturn(false);
        DBManager.close(rset);

        User user = userDAO.getUser(conn, "qwerty@qwe.rty");
        Assertions.assertNull(user);
    }

}
