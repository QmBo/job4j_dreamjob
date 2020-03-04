package ru.job4j.servlets.userservlet;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
/**
 * MemoryStore
 * @author Victor Egorov (qrioflat@gmail.com).
 * @version 0.1
 * @since 05.02.2020
 */
public class MemoryStore implements Store {
    private static final Store STORE = new MemoryStore();
    private final Map<String, User> capacity = new ConcurrentHashMap<>(100);

    /**
     * Private constructor.
     */
    private MemoryStore() {
    }

    /**
     * Get instance.
     * @return instance
     */
    public static Store getInstance() {
        return STORE;
    }

    /**
     * Add new User and set User id.
     * @param user User to add
     * @return User with id
     */
    @Override
    public synchronized User add(User user) {
        String lastId = this.capacity.keySet().stream().max(this::camper).orElse("0");
        String id = String.valueOf(Integer.valueOf(lastId) + 1);
        this.capacity.putIfAbsent(id, user);
        return user.setId(id);
}

    /**
     * Last id find helper.
     * Camper two strings as number.
     * @param o1 first string
     * @param o2 second string
     * @return camper
     */
    private int camper(String o1, String o2) {
        int fist = Integer.valueOf(o1);
        int second = Integer.valueOf(o2);
        return fist - second;
    }

    /**
     * Update User.
     * @param user new data to user
     * @return updated User.
     */
    @Override
    public User update(User user) {
        return this.capacity.replace(user.getId(), user);
    }

    /**
     * Delete User.
     * @param user user to delete
     * @return deleted User.
     */
    @Override
    public User delete(User user) {
        return this.capacity.remove(user.getId());
    }

    /**
     * All User finder.
     * @return all users from memory
     */
    @Override
    public Set<User> findAll() {
        return new TreeSet<>(this.capacity.values());
    }

    /**
     * Find User by id.
     * @param user user to find
     * @return founded User on {@code null}
     */
    @Override
    public User findById(User user) {
        return this.capacity.get(user.getId());
    }
}
