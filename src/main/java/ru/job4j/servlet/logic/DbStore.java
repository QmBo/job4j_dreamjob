package ru.job4j.servlet.logic;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;
import java.util.Date;

import static java.lang.String.format;

public class DbStore implements Store {
    private static final Logger LOG = LogManager.getLogger(DbStore.class);
    private static final BasicDataSource SOURCE = new BasicDataSource();
    private static final Store INSTANCE = new DbStore();

    /**
     * Private constructor.
     */
    private DbStore() {
        SOURCE.setDriverClassName("org.postgresql.Driver");
        SOURCE.setUrl("jdbc:postgresql://127.0.0.1:5432/servlet");
        SOURCE.setUsername("postgres");
        SOURCE.setPassword("password");
        SOURCE.setMinIdle(5);
        SOURCE.setMaxIdle(10);
        SOURCE.setMaxOpenPreparedStatements(100);
    }

    /**
     * Get instance.
     * @return instance
     */
    public static Store getInstance() {
        return INSTANCE;
    }

    /**
     * Add new User and set User id.
     * @param user User to add
     * @return User with id
     */
    @Override
    public User add(final User user) {
        List<User> result = this.sqlRequest(
                format(
                        "insert into users (name, login, email, password, role_id, create_time, photoId) "
                                + "values ('%s', '%s', '%s', '%s', '%s', '%d', '%s') RETURNING *",
                        user.getName(), user.getLogin(), user.getEmail(),
                        user.getPassword(), user.getRole().getId(),
                        user.getCreateDate().getTime(), user.getPhotoId()
                )
        );
        return result.isEmpty() ? null : result.get(0);
    }

    /**
     * Update User.
     * @param user new data to user
     * @return updated User.
     */
    @Override
    public User update(final User user) {
        List<User> result = this.sqlRequest(
                format(
                        "update users set name = '%s', login = '%s', email = '%s', create_time = '%d', "
                                + "photoId = '%s', password = '%s', role_id = '%s' where id ='%s' RETURNING *",
                        user.getName(), user.getLogin(), user.getEmail(),
                        user.getCreateDate().getTime(), user.getPhotoId(),
                        user.getPassword(), user.getRole().getId(), user.getId()
                )
        );
        return result.isEmpty() ? null : result.get(0);
    }

    /**
     * Delete User.
     * @param user user to delete
     * @return deleted User.
     */
    @Override
    public User delete(final User user) {
        List<User> result = this.sqlRequest(
                format(
                        "delete from users where id ='%s' RETURNING *",
                        user.getId()
                )
        );
        return result.isEmpty() ? null : result.get(0);
    }

    /**
     * All User finder.
     * @return all users from memory
     */
    @Override
    public Set<User> findAll() {
        return new TreeSet<>(this.sqlRequest("select * from users"));
    }

    /**
     * Find User by id.
     * @param user user to find
     * @return founded User on {@code null}
     */
    @Override
    public User findById(final User user) {
        List<User> result = this.sqlRequest(format("select * from users where id ='%s'", user.getId()));
        return result.isEmpty() ? null : result.get(0);
    }

    /**
     * SQL Requester.
     * @param sql request.
     * @return list of users at request.
     */
    private List<User> sqlRequest(final String sql) {
        List<User> result = new LinkedList<>();
        Map<Integer, Role> allRoles = allRoles();
        try (Connection st = SOURCE.getConnection();
             ResultSet rs = st.createStatement().executeQuery(sql)) {
            while (rs.next()) {
                int roleId = rs.getInt("role_id");
                User user = new User(
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getString("photoId"),
                        new Date(rs.getLong("create_time")),
                        new Role(roleId, allRoles.get(roleId).getName())
                ).setId(rs.getString("id"));
                result.add(user);
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * All roles getter.
     * @return list of roles at request.
     */
    public Map<Integer, Role> allRoles() {
        Map<Integer, Role> result = new HashMap<>(100);
        try (Connection st = SOURCE.getConnection();
             ResultSet rs = st.createStatement().executeQuery("select * from roles")) {
            while (rs.next()) {
                Role role = new Role(
                        rs.getInt("id"),
                        rs.getString("role"));
                result.put(role.getId(), role);
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }
}