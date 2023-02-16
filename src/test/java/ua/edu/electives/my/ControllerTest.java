package ua.edu.electives.my;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import ua.edu.electives.my.command.Command;
import ua.edu.electives.my.command.CommandContainer;
import ua.edu.electives.my.dao.DBException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class ControllerTest {
    private Controller controller;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Command command;

    @BeforeEach
    public void initTest() {
        controller = new Controller();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        when(request.getParameter("command")).thenReturn("login");
        command = mock(Command.class);
    }

    @Test
    public void testControllerGet() throws DBException {
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher(isA(String.class))).thenReturn(requestDispatcher);
        try (MockedStatic<CommandContainer> commandContainerStatic = mockStatic(CommandContainer.class)) {
            commandContainerStatic.when(() -> CommandContainer.getCommand("login")).thenReturn(command);
            when(command.execute(request, response)).thenReturn("login.jsp");
            Assertions.assertEquals("login.jsp", command.execute(request, response));
            doNothing().when(requestDispatcher).forward(request, response);
            controller.doGet(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
        verify(request, times(1)).getRequestDispatcher("login.jsp");
    }

    @Test
    public void testControllerPost() throws DBException {
        ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);
        try (MockedStatic<CommandContainer> commandContainerStatic = mockStatic(CommandContainer.class)) {
            commandContainerStatic.when(() -> CommandContainer.getCommand("login")).thenReturn(command);
            when(command.execute(request, response)).thenReturn("login.jsp");
            doNothing().when(response).sendRedirect(valueCapture.capture());
            controller.doPost(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals("login.jsp", valueCapture.getValue());
    }

    @Test
    public void testControllerGetError() throws DBException {
        doNothing().when(request).setAttribute(isA(String.class), isA(Object.class));
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher(isA(String.class))).thenReturn(requestDispatcher);
        try (MockedStatic<CommandContainer> commandContainerStatic = mockStatic(CommandContainer.class)) {
            commandContainerStatic.when(() -> CommandContainer.getCommand("login")).thenReturn(command);
            when(command.execute(request, response)).thenThrow(new DBException("Test error message"));
            doNothing().when(requestDispatcher).forward(request, response);
            controller.doGet(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
        verify(request, times(1)).getRequestDispatcher("error.jsp");
        verify(request, times(1)).setAttribute("errorMessage", "Test error message");
    }

    @Test
    public void testControllerPostError() throws DBException {
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        doNothing().when(session).setAttribute(isA(String.class), isA(Object.class));
        ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);
        try (MockedStatic<CommandContainer> commandContainerStatic = mockStatic(CommandContainer.class)) {
            commandContainerStatic.when(() -> CommandContainer.getCommand("login")).thenReturn(command);
            when(command.execute(request, response)).thenThrow(new DBException("Test error message"));
            doNothing().when(response).sendRedirect(valueCapture.capture());
            controller.doPost(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals("error.jsp", valueCapture.getValue());
        verify(session, times(1)).setAttribute("errorMessage", "Test error message");
    }

}
