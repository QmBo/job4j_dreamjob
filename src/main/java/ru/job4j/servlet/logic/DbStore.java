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
                        "insert into users (name, login, email, password, role_id, create_time, photoId, city_id) "
                                + "values ('%s', '%s', '%s', '%s', '%s', '%d', '%s', "
                                + "(select id from citys where city = '%s')) RETURNING id",
                        user.getName(), user.getLogin(), user.getEmail(),
                        user.getPassword(), user.getRole().getId(),
                        user.getCreateDate().getTime(), user.getPhotoId(), user.getCity()
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
                                + "photoId = '%s', password = '%s', role_id = '%s', "
                                + "city_id = (select id from citys where city = '%s') where id ='%s' RETURNING id",
                        user.getName(), user.getLogin(), user.getEmail(),
                        user.getCreateDate().getTime(), user.getPhotoId(),
                        user.getPassword(), user.getRole().getId(), user.getCity(), user.getId()
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
                        "delete from users where id ='%s' RETURNING id",
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
        return new TreeSet<>(this.sqlRequest("select id from users"));
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
        try (Connection conn = SOURCE.getConnection();
             ResultSet rs = conn.createStatement().executeQuery(sql)) {
            List<Integer> ids = new LinkedList<>();
            while (rs.next()) {
                ids.add(rs.getInt("id"));
            }
            result = this.usersGetter(ids, conn);
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * SQL Requester helper. Return users bu id.
     * @param ids users to return
     * @param conn connection
     * @return users bu id
     */
    private List<User> usersGetter(final List<Integer> ids, final Connection conn) {
        List<User> result = new LinkedList<>();
        //noinspection SqlResolve
        try (PreparedStatement ps = conn.prepareStatement(
                "select u.id, u.name, u.login, u.email, u.create_time, u.photoid, u.password, "
                        + "r.role, c.city, co.country, u.role_id, u.city_id, c.country_id from users as u "
                        + "inner join citys as c on(u.city_id = c.id) inner join roles as r on(u.role_id = r.id) "
                        + "inner join countrys as co on (co.id = c.country_id) where u.id = ?")) {
            for (int i : ids) {
                ps.setInt(1, i);
                result.add(this.usersCrater(ps.executeQuery()));
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    private User usersCrater(ResultSet rs) {
        User result = null;
        try {
            while (rs.next()) {
                result = new User(
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getString("photoId"),
                        new Date(rs.getLong("create_time")),
                        new Role(
                                rs.getInt("role_id"),
                                rs.getString("role")
                        ),
                        rs.getString("country"),
                        rs.getString("city")
                ).setId(rs.getString("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
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


    /**
     * All address getter.
     * @return list of address at request.
     * @noinspection SqlResolve
     */
    @Override
    public List<UsersAddress> allAddresses() {
        List<UsersAddress> result = new LinkedList<>();
        try (Connection st = SOURCE.getConnection();
             ResultSet rs = st.createStatement().executeQuery(
                     "select co.country, c.city, c.id, c.country_id from countrys as co "
                             + "inner join citys as c on (c.country_id = co.id) order by c.id"
             )) {
            while (rs.next()) {
                UsersAddress address = new UsersAddress(
                        rs.getInt("id"),
                        rs.getString("city"),
                        rs.getInt("country_id"),
                        rs.getString("country")
                );
                result.add(address);
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }
}