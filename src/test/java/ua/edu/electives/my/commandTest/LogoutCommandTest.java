package ua.edu.electives.my.commandTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.edu.electives.my.command.LogoutCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.*;

public class LogoutCommandTest {

    @Test
    public void testLogoutCommand() {
        LogoutCommand logoutCommand = new LogoutCommand();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession(false)).thenReturn(session);
        String actual = logoutCommand.execute(request, response);
        Assertions.assertEquals("index.jsp", actual);
    }
}
