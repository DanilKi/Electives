package ua.edu.electives.my.commandTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import ua.edu.electives.my.command.CourseInfoCommand;
import ua.edu.electives.my.dao.CourseManager;
import ua.edu.electives.my.dao.DBException;
import ua.edu.electives.my.entity.Course;
import ua.edu.electives.my.entity.Topic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.*;

public class CourseInfoCommandTest {

    @Test
    public void tesCourseInfoCommand() throws DBException {
        CourseInfoCommand infoCommand = new CourseInfoCommand();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        CourseManager courseManager = mock(CourseManager.class);
        when(request.getParameter("course-id")).thenReturn("2");
        doNothing().when(request).setAttribute(isA(String.class), isA(Object.class));
        Course testCourse = new Course();
        testCourse.setId(2); testCourse.setTopicId(1);

        try (MockedStatic<CourseManager> courseManagerStatic = mockStatic(CourseManager.class)) {
            courseManagerStatic.when(CourseManager::getInstance).thenReturn(courseManager);
            when(courseManager.getCourse(2)).thenReturn(testCourse);
            when(courseManager.getTopic(testCourse.getTopicId())).thenReturn(new Topic());
            String result = infoCommand.execute(request, response);
            Assertions.assertEquals("courseinfo.jsp", result);
        }
        verify(request, times(3)).setAttribute(isA(String.class), isA(Object.class));
    }
}
