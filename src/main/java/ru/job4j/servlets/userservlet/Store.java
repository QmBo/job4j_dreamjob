package ru.job4j.servlets.userservlet;

import java.util.Set;
/**
 * Store
 * @author Victor Egorov (qrioflat@gmail.com).
 * @version 0.1
 * @since 05.02.2020
 */
public interface Store {
    /**
     * Add new User and set User id.
     * @param user User to add
     * @return User with id
     */
    User add(User user);
    /**
     * Update User.
     * @param user new data to user
     * @return updated User.
     */
    User update(User user);
    /**
     * Delete User.
     * @param user user to delete
     * @return deleted User.
     */
    User delete(User user);
    /**
     * All User finder.
     * @return all users from memory
     */
    Set<User> findAll();
    /**
     * Find User by id.
     * @param user user to find
     * @return founded User on {@code null}
     */
    User findById(User user);
}
