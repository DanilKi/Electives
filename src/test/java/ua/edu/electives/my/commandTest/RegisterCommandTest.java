package ua.edu.electives.my.commandTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import ua.edu.electives.my.command.RegisterCommand;
import ua.edu.electives.my.dao.DBException;
import ua.edu.electives.my.dao.UserManager;
import ua.edu.electives.my.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.*;

public class RegisterCommandTest {

    @Test
    public void testRegisterCommand() throws DBException {
        RegisterCommand command = new RegisterCommand();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        UserManager userManager = mock(UserManager.class);

        when(request.getSession()).thenReturn(session);
        when(request.getSession(false)).thenReturn(session);

        when(request.getParameter("email")).thenReturn("test@email.com");
        when(request.getParameter("password")).thenReturn("password");
        when(request.getParameter("firstname")).thenReturn("Firstname");
        when(request.getParameter("lastname")).thenReturn("Lastname");

        try (MockedStatic<UserManager> userManagerStatic = mockStatic(UserManager.class)) {
            userManagerStatic.when(UserManager::getInstance).thenReturn(userManager);
            doNothing().when(userManager).registerUser(any(User.class));
            ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);
            doNothing().when(session).setAttribute(eq("infoMessage"), valueCapture.capture());
            String actual = command.execute(request, response);
            Assertions.assertEquals("index.jsp", actual);
            Assertions.assertEquals("User has been registered successfully.", valueCapture.getValue());
        }
    }

}
