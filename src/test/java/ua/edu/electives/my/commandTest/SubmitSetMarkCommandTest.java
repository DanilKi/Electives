package ua.edu.electives.my.commandTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import ua.edu.electives.my.command.SubmitSetMarkCommand;
import ua.edu.electives.my.dao.DBException;
import ua.edu.electives.my.dao.UserManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.*;

public class SubmitSetMarkCommandTest {

    @Test
    public void testSubmitSetMarkCommand() throws DBException {
        SubmitSetMarkCommand command = new SubmitSetMarkCommand();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        UserManager userManager = mock(UserManager.class);

        when(request.getSession()).thenReturn(session);
        when(request.getSession(false)).thenReturn(session);
        doNothing().when(session).removeAttribute(isA(String.class));

        when(request.getParameter("course-id")).thenReturn("10");
        when(request.getParameterValues("student-id")).thenReturn(new String[]{"11", "15", "20"});
        when(request.getParameterValues("mark")).thenReturn(new String[]{"40", "50", "60"});

        try (MockedStatic<UserManager> userManagerStatic = mockStatic(UserManager.class)) {
            userManagerStatic.when(UserManager::getInstance).thenReturn(userManager);
            doNothing().when(userManager).updateCourseStudents(eq(10), anyInt(), anyInt());
            ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);
            doNothing().when(session).setAttribute(eq("infoMessage"), valueCapture.capture());
            String actual = command.execute(request, response);
            Assertions.assertEquals("front?command=courseinfo&course-id=10", actual);
            Assertions.assertEquals("Marks have been set successfully.", valueCapture.getValue());
            verify(userManager, times(3)).updateCourseStudents(eq(10), anyInt(), anyInt());
        }
        verify(request, times(2)).getParameterValues(isA(String.class));
        verify(session, times(2)).removeAttribute(isA(String.class));
    }
}
