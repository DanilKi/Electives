package ua.edu.electives.my.commandTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import ua.edu.electives.my.command.MyMenuCommand;
import ua.edu.electives.my.dao.CourseManager;
import ua.edu.electives.my.dao.DBException;
import ua.edu.electives.my.entity.Course;
import ua.edu.electives.my.entity.CourseStudent;
import ua.edu.electives.my.entity.Role;
import ua.edu.electives.my.entity.User;
import ua.edu.electives.my.util.DateUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

public class MyMenuCommandTest {
    private MyMenuCommand menuCommand;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private CourseManager courseManager;
    private User testUser;

    @BeforeEach
    public void initTest() {
        menuCommand = new MyMenuCommand();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        courseManager = mock(CourseManager.class);
        when(request.getSession()).thenReturn(session);
        when(request.getSession(false)).thenReturn(session);
        doNothing().when(session).setAttribute(isA(String.class), isA(Object.class));
        doNothing().when(request).setAttribute(isA(String.class), isA(Object.class));
        when(request.getParameter(isA(String.class))).thenReturn(null);
        testUser = new User(); testUser.setId(10); testUser.setEmail("test@test.com");
        when(session.getAttribute("authorizedUser")).thenReturn(testUser);
    }

    @Test
    public void testMyMenuForTeacher() throws DBException {
        when(session.getAttribute("role")).thenReturn(Role.TEACHER);
        List<Course> testCourseList = new ArrayList<>();
        Course testCourse = new Course();
        testCourse.setTitle("Test Java course");
        testCourse.setStartDate(DateUtil.getCurrentDate());
        testCourse.setEndDate(DateUtil.addDays(DateUtil.getCurrentDate(), 30));
        testCourse.setTopicId(1);
        testCourse.setTeacherId(2);
        testCourse.setDuration(30);
        testCourse.setStudents(10);
        testCourseList.add(testCourse);
        try (MockedStatic<CourseManager> courseManagerStatic = mockStatic(CourseManager.class)) {
            courseManagerStatic.when(CourseManager::getInstance).thenReturn(courseManager);
            when(courseManager.getCoursesByTeacher(testUser.getId(), "inprogress", "none", 0, 10))
                                                    .thenReturn(testCourseList);
            when(courseManager.getNumOfTeacherCourses(testUser.getId(), "inprogress")).thenReturn(1);
            String actual = menuCommand.execute(request, response);
            Assertions.assertEquals("mymenu.jsp", actual);
        }
        verify(request, times(3)).getParameter(isA(String.class));
        verify(request, times(3)).setAttribute(isA(String.class),isA(Object.class));
    }

    @Test
    public void testMyMenuForTeacherNoCoursesFound() throws DBException {
        when(session.getAttribute("role")).thenReturn(Role.TEACHER);
        ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);
        List<Course> testCourseList = new ArrayList<>();
        try (MockedStatic<CourseManager> courseManagerStatic = mockStatic(CourseManager.class)) {
            courseManagerStatic.when(CourseManager::getInstance).thenReturn(courseManager);
            when(courseManager.getCoursesByTeacher(testUser.getId(), "inprogress", "none", 0, 10))
                    .thenReturn(testCourseList);
            when(session.getAttribute("currentLocale")).thenReturn("en");
            doNothing().when(request).setAttribute(eq("nothingFound"), valueCapture.capture());
            when(courseManager.getNumOfTeacherCourses(testUser.getId(), "inprogress")).thenReturn(0);
            String actual = menuCommand.execute(request, response);
            Assertions.assertEquals("mymenu.jsp", actual);
        }
        Assertions.assertEquals("Nothing is found.", valueCapture.getValue());
        verify(request, times(3)).getParameter(isA(String.class));
        verify(request, times(4)).setAttribute(isA(String.class),isA(Object.class));
    }

    @Test
    public void testMyMenuForStudent() throws DBException {
        when(session.getAttribute("role")).thenReturn(Role.STUDENT);
        List<CourseStudent> testCSList = new ArrayList<>();
        Course testCourse = new Course();
        testCourse.setTitle("Basic Test course");
        testCourse.setStartDate(DateUtil.getCurrentDate());
        testCourse.setEndDate(DateUtil.addDays(DateUtil.getCurrentDate(), 30));
        testCourse.setTopicId(1);
        testCourse.setTeacherId(2);
        testCourse.setDuration(30);
        testCourse.setStudents(10);
        CourseStudent testCS = new CourseStudent(); testCS.setCourse(testCourse); testCS.setStudent(testUser);
        testCSList.add(testCS);
        try (MockedStatic<CourseManager> courseManagerStatic = mockStatic(CourseManager.class)) {
            courseManagerStatic.when(CourseManager::getInstance).thenReturn(courseManager);
            when(courseManager.getCoursesByStudent(testUser, "none")).thenReturn(testCSList);
            String actual = menuCommand.execute(request, response);
            Assertions.assertEquals("mymenu.jsp", actual);
        }
        verify(request, times(3)).getParameter(isA(String.class));
        verify(request, times(3)).setAttribute(isA(String.class), isA(Object.class));
        verify(session, times(1)).setAttribute(isA(String.class), isA(Object.class));
    }

    @Test
    public void testMyMenuForStudentNoCoursesFound() throws DBException {
        when(session.getAttribute("role")).thenReturn(Role.STUDENT);
        List<CourseStudent> testCSList = new ArrayList<>();
        Course testCourse = new Course();
        testCourse.setTitle("Test Test course");
        testCourse.setStartDate(DateUtil.addDays(DateUtil.getCurrentDate(), 5));
        testCourse.setEndDate(DateUtil.addDays(DateUtil.getCurrentDate(), 30));
        testCourse.setTopicId(1);
        testCourse.setTeacherId(2);
        testCourse.setDuration(25);
        testCourse.setStudents(10);
        CourseStudent testCS = new CourseStudent(); testCS.setCourse(testCourse); testCS.setStudent(testUser);
        testCSList.add(testCS);
        ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);
        try (MockedStatic<CourseManager> courseManagerStatic = mockStatic(CourseManager.class)) {
            courseManagerStatic.when(CourseManager::getInstance).thenReturn(courseManager);
            when(courseManager.getCoursesByStudent(testUser, "none")).thenReturn(testCSList);
            when(session.getAttribute("currentLocale")).thenReturn("en");
            doNothing().when(request).setAttribute(eq("nothingFound"), valueCapture.capture());
            String actual = menuCommand.execute(request, response);
            Assertions.assertEquals("mymenu.jsp", actual);
        }
        Assertions.assertEquals("Nothing is found.", valueCapture.getValue());
        verify(request, times(3)).getParameter(isA(String.class));
        verify(request, times(4)).setAttribute(isA(String.class), isA(Object.class));
        verify(session, times(1)).setAttribute(isA(String.class), isA(Object.class));
    }

}
