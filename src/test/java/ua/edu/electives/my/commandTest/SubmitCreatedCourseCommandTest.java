package ua.edu.electives.my.commandTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import ua.edu.electives.my.command.SubmitCreatedCourseCommand;
import ua.edu.electives.my.dao.CourseManager;
import ua.edu.electives.my.dao.DBException;
import ua.edu.electives.my.entity.Course;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.*;

public class SubmitCreatedCourseCommandTest {

    @Test
    public void testSubmitCreatedCourseCommand() throws DBException {
        SubmitCreatedCourseCommand command = new SubmitCreatedCourseCommand();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        CourseManager courseManager = mock(CourseManager.class);

        when(request.getSession()).thenReturn(session);
        when(request.getSession(false)).thenReturn(session);

        when(request.getParameter("title")).thenReturn("Test course");
        when(request.getParameter("start-date")).thenReturn("2023-06-01");
        when(request.getParameter("end-date")).thenReturn("2023-06-30");
        when(request.getParameter("topic-id")).thenReturn("1");
        when(request.getParameter("teacher-id")).thenReturn("2");

        try (MockedStatic<CourseManager> courseManagerStatic = mockStatic(CourseManager.class)) {
            courseManagerStatic.when(CourseManager::getInstance).thenReturn(courseManager);
            ArgumentCaptor<Course> courseCapture = ArgumentCaptor.forClass(Course.class);
            doNothing().when(courseManager).createCourse(courseCapture.capture());
            ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);
            doNothing().when(session).setAttribute(eq("infoMessage"), valueCapture.capture());
            String actual = command.execute(request, response);
            Assertions.assertEquals("index.jsp", actual);
            Assertions.assertEquals("New course created successfully.", valueCapture.getValue());
            Assertions.assertEquals(29, courseCapture.getValue().getDuration());
            Assertions.assertNull(courseCapture.getValue().getDescription());
        }
    }
}
