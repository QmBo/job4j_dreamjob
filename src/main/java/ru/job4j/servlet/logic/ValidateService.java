package ru.job4j.servlet.logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * ValidateService
 * @author Victor Egorov (qrioflat@gmail.com).
 * @version 0.1
 * @since 05.02.2020
 */
public class ValidateService {
    private static final Logger LOG = LogManager.getLogger(ValidateService.class);
    private static final String ID = "id";
    private static final String DEL = "del";
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String LOGIN = "login";
    private static final String PHOTO_ID = "photoId";
    private static final String PASS = "password";
    private static final String ROLE = "role";
    private static final String DEF_ROLE = "User";
    private static final ValidateService SERVICE = new ValidateService();
    private static final String OUT_ID = "-1";
    private static final String COUNTRY = "country";
    private static final String CITY = "city";
    public static final String COUNTRY_PARAM = "name";
    public static final String ALL_COUNTRYS = "allCountrys";
    public static final String DEF_CITY = "Moscow";
    public static final String DEF_COUNTRY = "Russia";
    private final Store logic = DbStore.getInstance();

    /**
     * Private constructor.
     */
    private ValidateService() {
    }

    /**
     * Get instance.
     * @return instance
     */
    public static ValidateService getInstance() {
        return SERVICE;
    }

    /**
     * Add User.
     * @param req request
     * @return answer
     */
    public User add(final HttpServletRequest req) {
        User user;
        if (req.getParameterMap().isEmpty()) {
            Map<String, String> address = this.getCorrectAddress(req);
            user = new User(
                    (String) req.getAttribute(NAME),
                    (String) req.getAttribute(EMAIL),
                    (String) req.getAttribute(LOGIN),
                    (String) req.getAttribute(PASS),
                    (String) req.getAttribute(PHOTO_ID),
                    this.getRoles().get(this.gerRoleId(req)),
                    address.get(COUNTRY),
                    address.get(CITY)
            );
        } else {
            user = new User(
                    req.getParameter(NAME),
                    req.getParameter(EMAIL),
                    req.getParameter(LOGIN));
        }
        return this.logic.add(user);
    }

    /**
     * Check input addresses and return correct or default.
     * @param req request
     * @return country and city names map
     */
    private Map<String, String> getCorrectAddress(final HttpServletRequest req) {
        Map<String, String> result = new HashMap<>(Map.of(COUNTRY, DEF_COUNTRY, CITY, DEF_CITY));
        String cityId = (String) req.getAttribute(CITY);
        if (cityId != null) {
            List<UsersAddress> addresses = this.logic.allAddresses();
            for (UsersAddress address : addresses) {
                if (address.getId() == Integer.valueOf(cityId)) {
                    result.put(COUNTRY, address.getCountry());
                    result.put(CITY, address.getCity());
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Present corrected role id. If uncorrected get the "User" role id.
     * @param req request
     * @return role id or USER ROLE id
     */
    private int gerRoleId(HttpServletRequest req) {
        String roleValue = (String) req.getAttribute(ROLE);
        int roleId = 0;
        Map<Integer, Role> roleMap = this.logic.allRoles();
        if (roleValue != null) {
            try {
                roleId = Integer.valueOf(roleValue);
            } catch (NumberFormatException e) {
                LOG.error("NumberFormatException {roleValue}", e);
            }
        }
        if (!roleMap.keySet().contains(roleId)) {
            for (Role role : roleMap.values()) {
                if (DEF_ROLE.equals(role.getName())) {
                    roleId = role.getId();
                }
            }
        }
        return roleId;
    }

    /**
     * Find User by id.
     * @param req request
     * @return answer
     */
    public User findById(final HttpServletRequest req) {
        String id;
        if (req.getParameterMap().isEmpty()) {
            id = (String) req.getAttribute(ID);
        } else {
            id = req.getParameter(ID);
        }
        return this.logic.findById(new User().setId(id));
    }

    /**
     * Find all Users.
     * @return answer
     */
    public Set<User> findAll() {
        return this.logic.findAll();
    }

    /**
     * Delete User.
     * @param req request
     * @return answer
     */
    public User delete(final HttpServletRequest req) {
        return this.logic.delete(new User().setId(req.getParameter(DEL)));
    }

    /**
     * Update User.
     * @param req request
     * @return answer
     */
    public User update(final HttpServletRequest req) {
        User result = null;
        String id;
        if (req.getParameterMap().isEmpty()) {
            id = (String) req.getAttribute(ID);
        } else {
            id = req.getParameter(ID);
        }
        User oldUser = this.logic.findById(new User().setId(id));
        if (oldUser != null) {
            User user;
            if (req.getParameterMap().isEmpty()) {
                String photoId = (String) req.getAttribute(PHOTO_ID);
                if (photoId == null) {
                    photoId = oldUser.getPhotoId();
                }
                Map<String, String> address = this.getCorrectAddress(req);
                user = new User(
                        (String) req.getAttribute(NAME),
                        (String) req.getAttribute(EMAIL),
                        (String) req.getAttribute(LOGIN),
                        (String) req.getAttribute(PASS),
                        photoId,
                        this.getRoles().get(this.gerRoleId(req)),
                        address.get(COUNTRY),
                        address.get(CITY)
                ).setId(id);
            } else {
                user = new User(
                        req.getParameter(NAME),
                        req.getParameter(EMAIL),
                        req.getParameter(LOGIN)).setId(id);
            }
            result = this.logic.update(user);
        }
        return result;
    }

    /**
     * Is credentials.
     * @param req request
     * @return answer
     */
    public boolean isCredentials(final HttpServletRequest req) {
        boolean result = false;
        String login = req.getParameter(LOGIN);
        String password = req.getParameter(PASS);
        if (login != null && password != null) {
            Set<User> users = this.findAll();
            for (User user : users) {
                if (req.getParameter(LOGIN).equals(user.getLogin())) {
                    result = req.getParameter(PASS).equals(user.getPassword());
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Shove all roles.
     * @return roles map
     */
    public Map<Integer, Role> getRoles() {
        return this.logic.allRoles();
    }

    /**
     * Check login to available. If update user, put user id.
     * @param req request
     * @return available
     */
    public boolean available(final HttpServletRequest req) {
        boolean result = true;
        String id = req.getParameter(ID);
        String login = req.getParameter(LOGIN);
        if (id == null) {
            id = OUT_ID;
        }
        for (User user : this.logic.findAll()) {
            if (login.equals(user.getLogin()) && !id.equals(user.getId())) {
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * Get all addresses.
     * @param req request
     * @return available
     */
    public LinkedHashMap<Integer, String> getAllAddresses(final HttpServletRequest req) {
        LinkedHashMap<Integer, String> result = new LinkedHashMap<>();
        String param = req.getParameter(COUNTRY_PARAM);
        if (param != null) {
            List<UsersAddress> addresses = this.logic.allAddresses();
            if (ALL_COUNTRYS.equals(param)) {
                for (UsersAddress address : addresses) {
                    result.putIfAbsent(address.getCountryId(), address.getCountry());
                }
            } else {
                for (UsersAddress address : addresses) {
                    String id = String.valueOf(address.getCountryId());
                    if (param.equals(id)) {
                        result.putIfAbsent(address.getId(), address.getCity());
                    }
                }
            }
        }
        return result;
    }
}
