package ru.job4j.servlet.servlets;

import org.junit.Test;
import ru.job4j.servlet.logic.MemoryStore;
import ru.job4j.servlet.logic.Store;
import ru.job4j.servlet.logic.User;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;

public class MemoryStoreTest {

    @Test
    public void whenDeleteUserThenUserNotInCapacity() {
        Store store = MemoryStore.getInstance();
        User user = new User();
        String id = store.add(user).getId();
        assertThat(store.findById(new User().setId(id)), is(user));
        assertThat(store.delete(new User().setId(id)), is(user));
        assertNull(store.findById(new User().setId(id)));
    }

    @Test
    public void whenUpdateUserThenUserUpdated() {
        Store store = MemoryStore.getInstance();
        User user = new User();
        assertNotNull(store.add(new User()));
        String id = store.add(user).getId();
        User updateUser = new User("test", "test", "test").setId(id);
        assertNotNull(store.update(updateUser));
        assertThat(store.findById(new User().setId(id)), is(updateUser));
        assertNotNull(store.findAll());
        assertNotSame(user, updateUser);
    }
}