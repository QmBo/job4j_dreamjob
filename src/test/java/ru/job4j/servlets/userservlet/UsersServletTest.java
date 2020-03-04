package ru.job4j.servlets.userservlet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;

import java.util.Collections;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class UsersServletTest {
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String LOGIN = "login";
    private static final String DEL = "del";

    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletRequest secondRequest;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenUserUpdateThenUpdateList() {
        when(request.getParameter(NAME)).thenReturn("test");
        when(request.getParameter(EMAIL)).thenReturn("test");
        when(request.getParameter(LOGIN)).thenReturn("test");
        when(request.getParameterMap()).thenReturn(Collections.singletonMap("Test", new String[]{"Test"}));
        when(secondRequest.getParameter(NAME)).thenReturn("2");
        when(secondRequest.getParameter(EMAIL)).thenReturn("Super@email");
        when(secondRequest.getParameter(LOGIN)).thenReturn("2");
        when(secondRequest.getParameterMap()).thenReturn(Collections.singletonMap("Test", new String[]{"Test"}));
        ValidateService service = ValidateService.getInstance();
        User user = service.add(request);
        when(request.getParameter(ID)).thenReturn(user.getId());
        when(secondRequest.getParameter(ID)).thenReturn(user.getId());
        User result = service.update(secondRequest);
        assertThat(result.getEmail(), is("Super@email"));
        when(request.getParameter(DEL)).thenReturn(user.getId());
        service.delete(request);
    }
}