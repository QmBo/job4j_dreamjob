package ru.job4j.servlet.servlets;

import org.apache.logging.log4j.LogManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.job4j.servlet.logic.DbStore;
import ru.job4j.servlet.logic.MemoryStore;
import ru.job4j.servlet.logic.Store;
import ru.job4j.servlet.logic.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.ArgumentMatchers.any;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


/** @noinspection ConstantConditions*/
@RunWith(PowerMockRunner.class)
@PrepareForTest({DbStore.class, LogManager.class, ImageUploader.class, HttpSession.class})
public class ServletsTest {
    private static final String PHOTO_ID = "photoId";
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String LOGIN = "login";
    private static final String PASS = "password";
    private static final String ROLE = "role";
    private static final String ID = "id";
    private static final String DEL = "del";

    @Before
    public void setUp() {
        mockStatic(LogManager.class);
        mockStatic(DbStore.class);
        mockStatic(ImageUploader.class);
        mockStatic(HttpSession.class);
    }

    @Test
    public void whenUpdateThenUpdated() {
        Store store = MemoryStore.getInstance();
        when(DbStore.getInstance()).thenReturn(store);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletRequest reqUpdate = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(req.getAttribute(NAME)).thenReturn("IAMGROOT");
        when(req.getAttribute(LOGIN)).thenReturn("GROOT");
        when(req.getAttribute(PASS)).thenReturn("GROOT");
        when(req.getAttribute(EMAIL)).thenReturn("I@AM.GROOT");
        when(req.getAttribute(ROLE)).thenReturn("2");
        new UserCreateServlet().doPost(req, resp);
        String createId = store.findAll().stream().findFirst().get().getId();
        when(reqUpdate.getAttribute(ID)).thenReturn(createId);
        when(reqUpdate.getAttribute(NAME)).thenReturn("GROOTIAM");
        when(reqUpdate.getAttribute(LOGIN)).thenReturn("IAM");
        when(reqUpdate.getAttribute(PASS)).thenReturn("IAM");
        when(reqUpdate.getAttribute(EMAIL)).thenReturn("GROOT@GROOT.GROOT");
        when(reqUpdate.getAttribute(ROLE)).thenReturn("2");
        when(reqUpdate.getAttribute(PHOTO_ID)).thenReturn("2");
        RequestDispatcher rd = mock(RequestDispatcher.class);
        when(reqUpdate.getRequestDispatcher(any(String.class))).thenReturn(rd);
        HttpSession session = new SessionStab();
        when(reqUpdate.getSession()).thenReturn(session);
        new UserEditServlet().doPost(reqUpdate, resp);
        assertThat(store.findById(new User().setId(createId)).getEmail(), is("GROOT@GROOT.GROOT"));
        when(reqUpdate.getParameter(DEL)).thenReturn(createId);
        new UsersServlet().doPost(reqUpdate, resp);
    }

    @Test
    public void whenAddUserThenUserAdded() {
        Store store = MemoryStore.getInstance();
        when(DbStore.getInstance()).thenReturn(store);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(ImageUploader.upload(any(HttpServlet.class), any(HttpServletRequest.class))).thenReturn(null);
        when(req.getAttribute(NAME)).thenReturn("IAMGROOT");
        when(req.getAttribute(LOGIN)).thenReturn("GROOT");
        when(req.getAttribute(PASS)).thenReturn("GROOT");
        when(req.getAttribute(EMAIL)).thenReturn("I@AM.GROOT");
        when(req.getAttribute(ROLE)).thenReturn("2");
        new UserCreateServlet().doPost(req, resp);
        String createId = store.findAll().stream().findFirst().get().getId();
        assertThat(store.findAll().iterator().next().getName(), is("IAMGROOT"));
        when(req.getParameter(DEL)).thenReturn(createId);
        new UsersServlet().doPost(req, resp);
    }
}