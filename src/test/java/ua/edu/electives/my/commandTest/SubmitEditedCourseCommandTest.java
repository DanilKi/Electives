package ua.edu.electives.my.commandTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import ua.edu.electives.my.command.SubmitEditedCourseCommand;
import ua.edu.electives.my.dao.CourseManager;
import ua.edu.electives.my.dao.DBException;
import ua.edu.electives.my.entity.Course;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;

public class SubmitEditedCourseCommandTest {

    @Test
    public void testSubmitEditedCourseCommand() throws DBException {
        SubmitEditedCourseCommand command = new SubmitEditedCourseCommand();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        CourseManager courseManager = mock(CourseManager.class);

        when(request.getSession()).thenReturn(session);
        when(request.getSession(false)).thenReturn(session);
        Course testCourse = new Course(); testCourse.setId(1);
        when(session.getAttribute("editCourse")).thenReturn(testCourse);

        when(request.getParameter("title")).thenReturn("Test course");
        when(request.getParameter("start-date")).thenReturn("2023-06-01");
        when(request.getParameter("end-date")).thenReturn("2023-07-01");
        when(request.getParameter("topic-id")).thenReturn("1");
        when(request.getParameter("teacher-id")).thenReturn("2");
        when(request.getParameter("description")).thenReturn("test description");

        try (MockedStatic<CourseManager> courseManagerStatic = mockStatic(CourseManager.class)) {
            courseManagerStatic.when(CourseManager::getInstance).thenReturn(courseManager);
            ArgumentCaptor<Course> courseCapture = ArgumentCaptor.forClass(Course.class);
            doNothing().when(courseManager).updateCourse(courseCapture.capture());
            ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);
            doNothing().when(session).setAttribute(eq("infoMessage"), valueCapture.capture());
            String actual = command.execute(request, response);
            Assertions.assertEquals("front?command=courseinfo&course-id=1", actual);
            Assertions.assertEquals("Course updated successfully.", valueCapture.getValue());
            Assertions.assertEquals(30, courseCapture.getValue().getDuration());
            Assertions.assertNotNull(courseCapture.getValue().getDescription());
        }
        verify(request, times(6)).getParameter(isA(String.class));
        verify(request, times(2)).getSession();
    }
}
