package ua.edu.electives.my.commandTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import ua.edu.electives.my.command.GetAllUsersCommand;
import ua.edu.electives.my.dao.DBException;
import ua.edu.electives.my.dao.UserManager;
import ua.edu.electives.my.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

public class GetAllUsersCommandTest {
    private GetAllUsersCommand usersCommand;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private UserManager userManager;

    @BeforeEach
    public void initTest() {
        usersCommand = new GetAllUsersCommand();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        userManager = mock(UserManager.class);
        doNothing().when(request).setAttribute(isA(String.class), isA(Object.class));
        doNothing().when(request).setAttribute(isA(String.class), isA(Integer.class));
        when(request.getParameter(isA(String.class))).thenReturn(null);
    }

    @Test
    public void testGetAllUsersRegular() throws DBException {
        List<User> testUserList = new ArrayList<>();
        User testUser = new User();
        testUser.setFirstName("name1"); testUser.setLastName("name2"); testUser.setRoleId(1);
        testUserList.add(testUser);
        try (MockedStatic<UserManager> userManagerStatic = mockStatic(UserManager.class)) {
             userManagerStatic.when(UserManager::getInstance).thenReturn(userManager);
             when(userManager.getUsers(1, "none", 0, 10)).thenReturn(testUserList);
             when(userManager.getNumOfUsers(1)).thenReturn(1);
             String actual = usersCommand.execute(request, response);
            Assertions.assertEquals("users.jsp", actual);
        }
        verify(request, times(3)).getParameter(isA(String.class));
        verify(request, times(3)).setAttribute(isA(String.class),isA(Object.class));
    }

    @Test
    public void testGetAllUsersEmpty() throws DBException {
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute(isA(String.class))).thenReturn("en");
        ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);
        doNothing().when(request).setAttribute(eq("nothingFound"), valueCapture.capture());
        List<User> testUserList = new ArrayList<>();
        try (MockedStatic<UserManager> userManagerStatic = mockStatic(UserManager.class)) {
            userManagerStatic.when(UserManager::getInstance).thenReturn(userManager);
            when(userManager.getUsers(1, "none", 0, 10)).thenReturn(testUserList);
            when(userManager.getNumOfUsers(1)).thenReturn(0);
            String actual = usersCommand.execute(request, response);
            Assertions.assertEquals("users.jsp", actual);
        }
        Assertions.assertEquals("Nothing is found.", valueCapture.getValue());
        verify(request, times(3)).getParameter(isA(String.class));
        verify(request, times(4)).setAttribute(isA(String.class),isA(Object.class));
    }

}
