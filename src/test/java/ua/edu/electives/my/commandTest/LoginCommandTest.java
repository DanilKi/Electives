package ua.edu.electives.my.commandTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import ua.edu.electives.my.command.LoginCommand;
import ua.edu.electives.my.dao.DBException;
import ua.edu.electives.my.dao.UserManager;
import ua.edu.electives.my.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.*;

public class LoginCommandTest {
    private LoginCommand loginCommand;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private UserManager userManager;

    @BeforeEach
    public void initTest() {
        loginCommand = new LoginCommand();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        userManager = mock(UserManager.class);
        when(request.getSession()).thenReturn(session);
        when(request.getSession(false)).thenReturn(session);
        doNothing().when(session).removeAttribute(isA(String.class));
        doNothing().when(session).setAttribute(isA(String.class), isA(Object.class));
        doNothing().when(request).setAttribute(isA(String.class), isA(Object.class));
        when(request.getParameter("login")).thenReturn("login@gmail.com");
        when(request.getParameter("password")).thenReturn("password");
    }

    @Test
    public void testLoginCommandPositive() throws DBException {
        User testUser = new User();
        testUser.setEmail("login@gmail.com");
        testUser.setPassword("5f4dcc3b5aa765d61d8327deb882cf99");
        testUser.setBlocked(false);
        testUser.setRoleId(2);

        try (MockedStatic<UserManager> userManagerStatic = mockStatic(UserManager.class)) {
            userManagerStatic.when(UserManager::getInstance).thenReturn(userManager);
            when(userManager.getUser("login@gmail.com")).thenReturn(testUser);
            String result = loginCommand.execute(request, response);
            Assertions.assertEquals("front?command=mymenu&tab=inprogress&sort=az&page=1", result);
        }
        verify(session, times(2)).setAttribute(isA(String.class),isA(Object.class));
    }

    @Test
    public void testLoginCommandUserNotFound() throws DBException {
        try (MockedStatic<UserManager> userManagerStatic = mockStatic(UserManager.class)) {
            userManagerStatic.when(UserManager::getInstance).thenReturn(userManager);
            when(userManager.getUser("login@gmail.com")).thenReturn(null);
            String result = loginCommand.execute(request, response);
            Assertions.assertEquals("index.jsp", result);
        }
        verify(session, times(1)).setAttribute(isA(String.class),isA(Object.class));
    }

    @Test
    public void testLoginCommandPasswordInvalid() throws DBException {
        User testUser = new User();
        testUser.setEmail("login@gmail.com");
        testUser.setPassword("5f4dcc3b5aa765d61d8327deb882cf98");
        testUser.setBlocked(false);
        testUser.setRoleId(1);

        try (MockedStatic<UserManager> userManagerStatic = mockStatic(UserManager.class)) {
            userManagerStatic.when(UserManager::getInstance).thenReturn(userManager);
            when(userManager.getUser("login@gmail.com")).thenReturn(testUser);
            String result = loginCommand.execute(request, response);
            Assertions.assertEquals("index.jsp", result);
        }
        verify(session, times(1)).setAttribute(isA(String.class),isA(Object.class));
    }

    @Test
    public void testLoginCommandUserIsBlocked() throws DBException {
        User testUser = new User();
        testUser.setEmail("login@gmail.com");
        testUser.setPassword("5f4dcc3b5aa765d61d8327deb882cf99");
        testUser.setBlocked(true);
        testUser.setRoleId(2);

        try (MockedStatic<UserManager> userManagerStatic = mockStatic(UserManager.class)) {
            userManagerStatic.when(UserManager::getInstance).thenReturn(userManager);
            when(userManager.getUser("login@gmail.com")).thenReturn(testUser);
            String result = loginCommand.execute(request, response);
            Assertions.assertEquals("index.jsp", result);
        }
        verify(session, times(1)).setAttribute(isA(String.class),isA(Object.class));
    }
}
