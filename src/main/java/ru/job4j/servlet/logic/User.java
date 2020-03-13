package ru.job4j.servlet.logic;

import java.util.Date;

/**
 * User
 * @author Victor Egorov (qrioflat@gmail.com).
 * @version 0.1
 * @since 05.02.2020
 */
public class User implements Comparable<User> {
    private static final String DEF_NAME = "Default";
    private static final String DEF_EMAIL = "Default";
    private static final String DEF_LOGIN = "Default";
    private static final String DEF_PHOTO = "default.png";
    private static final String DEF_PASS = "";
    private static final Role DEF_ROLE = new Role(1, "Nub");
    private static final Date DATE = new Date(System.currentTimeMillis());
    private final Role role;
    private String id;
    private String name;
    private String email;
    private String login;
    private final Date createDate;
    private String photoId;
    private String password;

    /**
     * Constructor.
     */
    public User() {
        this(DEF_NAME, DEF_EMAIL, DEF_LOGIN, DEF_PASS, DEF_PHOTO, DATE, DEF_ROLE);
    }

    /**
     * Constructor.
     * @param name User name
     * @param email User email
     * @param login User login
     */
    public User(final String name, final String email, final String login) {
        this(name, email, login, DEF_PASS, DEF_PHOTO, DATE, DEF_ROLE);
    }

    /**
     * Constructor.
     * @param name User name
     * @param email User email
     * @param login User login
     * @param password User password
     * @param photoId User photo id
     * @param role User role
     */
    public User(final String name, final String email, final String login, final String password,
                final String photoId, final Role role) {
        this(name, email, login, password, photoId, DATE, role);
    }
    /**
     * Constructor.
     * @param name User name
     * @param email User email
     * @param login User login
     * @param password User password
     * @param photoId User photo id
     * @param createDate User create date
     * @param role User role
     */
    public User(final String name, final String email, final String login, final String password,
                final String photoId, final Date createDate, final Role role) {
        this.name = name;
        this.email = email;
        this.login = login;
        this.password = password;
        this.photoId = photoId;
        this.createDate = createDate;
        this.role = role;
    }

    /**
     * Id user setter.
     * @param id new id
     * @return User with new id
     */
    public User setId(final String id) {
        this.id = id;
        return this;
    }

    /**
     * Id getter.
     * @return Users id
     */
    public String getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return "User{"
                + "id='" + id + '\''
                + ", name='" + name + '\''
                + ", email='" + email + '\''
                + ", login='" + login + '\''
                + ", createDate=" + createDate
                + ", photoId='" + photoId + '\''
                + ", role=" + role
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        if (role != null ? !role.equals(user.role) : user.role != null) {
            return false;
        }
        if (id != null ? !id.equals(user.id) : user.id != null) {
            return false;
        }
        if (name != null ? !name.equals(user.name) : user.name != null) {
            return false;
        }
        if (email != null ? !email.equals(user.email) : user.email != null) {
            return false;
        }
        if (login != null ? !login.equals(user.login) : user.login != null) {
            return false;
        }
        if (createDate != null ? !createDate.equals(user.createDate) : user.createDate != null) {
            return false;
        }
        if (photoId != null ? !photoId.equals(user.photoId) : user.photoId != null) {
            return false;
        }
        return password != null ? password.equals(user.password) : user.password == null;
    }

    @Override
    public int hashCode() {
        int result = role != null ? role.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (photoId != null ? photoId.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    /**
     * To sorted Users at id/
     * @param o other User
     * @return compare
     * @noinspection NullableProblems
     */
    @Override
    public int compareTo(User o) {
        int result = 1;
        if (o != null) {
            int fist = Integer.valueOf(this.id);
            int second = Integer.valueOf(o.id);
            result = fist - second;
        }
        return result;
    }

    /**
     * Email getter.
     * @return users email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Login getter.
     * @return users getter
     */
    public String getLogin() {
        return this.login;
    }

    /**
     * Name getter.
     * @return users name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Create date getter.
     * @return user create date
     */
    public Date getCreateDate() {
        return this.createDate;
    }

    /**
     * Photo id getter.
     * @return photo id
     */
    public String getPhotoId() {
        return this.photoId;
    }

    /**
     * Password getter.
     * @return user password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Role getter.
     * @return role
     */
    public Role getRole() {
        return role;
    }
}
