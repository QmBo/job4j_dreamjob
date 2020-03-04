package ru.job4j.servlets.userservlet;

import java.util.Date;
import java.util.Objects;

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
    private static final Date DATE = new Date(System.currentTimeMillis());
    private String id;
    private String name;
    private String eMail;
    private String login;
    private Date createDate;
    private String photoId;

    /**
     * Constructor.
     */
    public User() {
        this(DEF_NAME, DEF_EMAIL, DEF_LOGIN, DEF_PHOTO, DATE);
    }

    /**
     * Constructor.
     * @param name User name
     * @param eMail User email
     * @param login User login
     */
    public User(final String name, final String eMail, final String login) {
        this(name, eMail, login, DEF_PHOTO, DATE);
    }

    /**
     * Constructor.
     * @param name User name
     * @param eMail User email
 * @param login User login
     * @param photoId User photo id
     */
    public User(final String name, final String eMail, final String login, String photoId) {
        this(name, eMail, login, photoId, DATE);
    }

    /**
     * Constructor.
     * @param name User name
     * @param eMail User email
     * @param login User login
     * @param photoId User photo id
     * @param createDate User create date
     */
    public User(final String name, final String eMail, final String login, String photoId, final Date createDate) {
        this.name = name;
        this.eMail = eMail;
        this.login = login;
        this.photoId = photoId;
        this.createDate = createDate;
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
        return "User{" + "id='" + id
                + '\'' + ", name='" + name
                + '\'' + ", eMail='" + eMail
                + '\'' + ", login='" + login
                + '\'' + ", createDate=" + createDate
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
        return Objects.equals(id, user.id)
                && name.equals(user.name)
                && eMail.equals(user.eMail)
                && login.equals(user.login)
                && createDate.equals(user.createDate)
                && photoId.equals(user.photoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, eMail, login, createDate, photoId);
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
        return this.eMail;
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
}
