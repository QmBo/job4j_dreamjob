package ru.job4j.servlets.userservlet;

import com.google.common.base.Joiner;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;

import java.util.Collections;

import static java.lang.String.format;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

public class UserServletTest {
    private static final String UPDATE = "update";
    private static final String UPDATE_TO = "User update to:";
    private static final String LS = "<br>";
    private static final String ID = "id";
    private static final String DELETE = "Deleted";
    private static final String UNF = "Users not Found!";
    private static final String DEL = "del";
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String LOGIN = "login";
    private static final String ANU = "Add new";

    @Mock
    HttpServletRequest request;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(request.getParameterMap()).thenReturn(Collections.singletonMap("Test", new String[]{"Test"}));
    }

    private String addUser(UserServlet service) {
        when(request.getParameter(NAME)).thenReturn("Test");
        when(request.getParameter(LOGIN)).thenReturn("Test");
        when(request.getParameter(EMAIL)).thenReturn("Test");
        String result = service.add().apply(request);
        int start = result.indexOf("id='") + 4;
        int end = result.indexOf("'", start);
        return result.substring(start, end);
    }

    private void delete(UserServlet service, String id) {
        when(request.getParameter(DEL)).thenReturn(id);
        service.delete().apply(request);
    }

    @Test
    public void whenAddThenAddUser() {
        UserServlet service = new UserServlet();
        String id = addUser(service);
        String exp = Joiner.on(" ").join(ANU, "User{id='");
        String result = service.add().apply(request);
        assertThat(result, is(startsWith(exp)));
        int start = result.indexOf("id='") + 4;
        int end = result.indexOf("'", start);
        delete(service, result.substring(start, end));
        delete(service, id);
    }

    @Test
    public void whenDeleteThenDeleteUser() {
        UserServlet service = new UserServlet();
        String id = addUser(service);
        when(request.getParameter(DEL)).thenReturn(id);
        String exp = Joiner.on(" ").join(DELETE, "User{id='");
        assertThat(service.delete().apply(request), is(startsWith(exp)));
    }

    @Test
    public void whenUncorrectedIdThenUNF() {
        UserServlet service = new UserServlet();
        when(request.getParameter(ID)).thenReturn("-9999");
        assertThat(service.findById().apply(request), is(UNF));
    }

    @Test
    public void whenFindAllThenAllUsersPrint() {
        UserServlet service = new UserServlet();
        String firstId = addUser(service);
        String secondId = addUser(service);
        assertThat(
                service.findAll().apply(request),
                allOf(
                        containsString(format("User{id='%s'", firstId)),
                        containsString(format("%sUser{id='%s'", LS, secondId))
                )
        );
        delete(service, firstId);
        delete(service, secondId);
    }

    @Test
    public void whenUpdateThenUserUpdated() {
        UserServlet service = new UserServlet();
        String id = addUser(service);
        when(request.getParameter(UPDATE)).thenReturn("");
        when(request.getParameter(NAME)).thenReturn("1");
        when(request.getParameter(LOGIN)).thenReturn("1");
        when(request.getParameter(EMAIL)).thenReturn("1");
        when(request.getParameter(ID)).thenReturn(id);
        String mask = Joiner.on(" ").join(UPDATE_TO, "User{id='");
        assertThat(service.update().apply(request), is(startsWith(mask)));
        delete(service, id);
    }
}