package ua.edu.electives.my.commandTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import ua.edu.electives.my.command.GetAllCoursesCommand;
import ua.edu.electives.my.dao.CourseManager;
import ua.edu.electives.my.dao.DBException;
import ua.edu.electives.my.dao.UserManager;
import ua.edu.electives.my.entity.Course;
import ua.edu.electives.my.entity.Topic;
import ua.edu.electives.my.entity.User;
import ua.edu.electives.my.util.DateUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

public class GetAllCoursesCommandTest {
    private GetAllCoursesCommand coursesCommand;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private CourseManager courseManager;
    private UserManager userManager;

    @BeforeEach
    public void initTest() {
        coursesCommand = new GetAllCoursesCommand();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        courseManager = mock(CourseManager.class);
        userManager = mock(UserManager.class);
        doNothing().when(request).setAttribute(isA(String.class), isA(Object.class));
        doNothing().when(request).setAttribute(isA(String.class), isA(Integer.class));
        when(request.getParameter(isA(String.class))).thenReturn(null);
    }

    @Test
    public void testGetAllCoursesRegular() throws DBException {
        List<Course> testCourseList = new ArrayList<>();
        Course testCourse = new Course();
        testCourse.setTitle("Basic Java course");
        testCourse.setStartDate(DateUtil.getCurrentDate());
        testCourse.setEndDate(DateUtil.addDays(DateUtil.getCurrentDate(), 30));
        testCourse.setTopicId(1);
        testCourse.setTeacherId(2);
        testCourse.setDuration(30);
        testCourse.setStudents(10);
        testCourseList.add(testCourse);

        try (MockedStatic<CourseManager> courseManagerStatic = mockStatic(CourseManager.class);
             MockedStatic<UserManager> userManagerStatic = mockStatic(UserManager.class)) {
            courseManagerStatic.when(CourseManager::getInstance).thenReturn(courseManager);
            userManagerStatic.when(UserManager::getInstance).thenReturn(userManager);
            when(courseManager.getCourses(0,0,"none", 0,10)).thenReturn(testCourseList);
            when(courseManager.getNumOfCourses(0,0)).thenReturn(1);
            when(userManager.getTeachers()).thenReturn(new ArrayList<User>());
            when(courseManager.getTopics()).thenReturn(new ArrayList<Topic>());
            String result = coursesCommand.execute(request, response);
            Assertions.assertEquals("courses.jsp", result);
        }
        verify(request, times(4)).getParameter(isA(String.class));
        verify(request, times(5)).setAttribute(isA(String.class),isA(Object.class));
    }

    @Test
    public void testGetAllCoursesEmpty() throws DBException {
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute(isA(String.class))).thenReturn("en");
        ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);
        doNothing().when(request).setAttribute(eq("nothingFound"), valueCapture.capture());
        List<Course> testCourseList = new ArrayList<>();

        try (MockedStatic<CourseManager> courseManagerStatic = mockStatic(CourseManager.class);
             MockedStatic<UserManager> userManagerStatic = mockStatic(UserManager.class)) {
            courseManagerStatic.when(CourseManager::getInstance).thenReturn(courseManager);
            userManagerStatic.when(UserManager::getInstance).thenReturn(userManager);
            when(courseManager.getCourses(0,0,"none", 0,10)).thenReturn(testCourseList);
            when(courseManager.getNumOfCourses(0,0)).thenReturn(0);
            when(userManager.getTeachers()).thenReturn(new ArrayList<User>());
            when(courseManager.getTopics()).thenReturn(new ArrayList<Topic>());
            String result = coursesCommand.execute(request, response);
            Assertions.assertEquals("courses.jsp", result);
        }
        Assertions.assertEquals("Nothing is found.", valueCapture.getValue());
        verify(request, times(4)).getParameter(isA(String.class));
        verify(request, times(6)).setAttribute(isA(String.class),isA(Object.class));
    }

}
