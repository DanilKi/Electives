package ua.edu.electives.my.commandTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import ua.edu.electives.my.command.SubmitEditedUserCommand;
import ua.edu.electives.my.dao.DBException;
import ua.edu.electives.my.dao.UserManager;
import ua.edu.electives.my.entity.Role;
import ua.edu.electives.my.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.*;

public class SubmitEditedUserCommandTest {

    @Test
    public void testSubmitEditedUserCommand() throws DBException {
        SubmitEditedUserCommand command = new SubmitEditedUserCommand();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        UserManager userManager = mock(UserManager.class);

        when(request.getSession()).thenReturn(session);
        when(request.getSession(false)).thenReturn(session);
        User testUser = new User();
        testUser.setId(12);
        testUser.setEmail("test@email.com");
        testUser.setPassword("5f4dcc3b5aa765d61d8327deb882cf99");
        testUser.setRoleId(2);
        testUser.setBlocked(false);
        when(session.getAttribute("editUser")).thenReturn(testUser);
        when(session.getAttribute("role")).thenReturn(Role.STUDENT);

        when(request.getParameter("email")).thenReturn("testuser@test.com");
        //when(request.getParameter("password")).thenReturn("5f4dcc3b5aa765d61d8327deb882cf99");
        when(request.getParameter("first-name")).thenReturn("Firstname");
        when(request.getParameter("last-name")).thenReturn("Lastname");
        when(request.getParameter("status")).thenReturn("false");

        try (MockedStatic<UserManager> userManagerStatic = mockStatic(UserManager.class)) {
            userManagerStatic.when(UserManager::getInstance).thenReturn(userManager);
            ArgumentCaptor<User> userCapture = ArgumentCaptor.forClass(User.class);
            doNothing().when(userManager).updateUser(userCapture.capture());
            doNothing().when(session).setAttribute(isA(String.class), isA(Object.class));
            ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);
            doNothing().when(session).setAttribute(eq("infoMessage"), valueCapture.capture());
            String actual = command.execute(request, response);
            Assertions.assertEquals("index.jsp", actual);
            Assertions.assertEquals("User profile updated successfully.", valueCapture.getValue());
            Assertions.assertFalse(userCapture.getValue().isBlocked());
            Assertions.assertNotNull(userCapture.getValue().getPassword());
        }
        verify(request, times(5)).getParameter(isA(String.class));
        verify(request, times(4)).getSession();
    }
}
