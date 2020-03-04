package ru.job4j.servlets.userservlet;

import com.google.common.base.Joiner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.function.Function;

public class UserServlet extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(UserServlet.class);
    private static final String UR = "Uncorrected Request!";
    private static final String UPDATE_TO = "User update to:";
    private static final String UNF = "Users not Found!";
    private static final String ANU = "Add new";
    private static final String DELETE = "Deleted";
    private static final String ID = "id";
    private static final String ADD = "add";
    private static final String ALL = "all";
    private static final String TYPE = "text/html";
    private static final String DEL = "del";
    private static final String UPDATE = "update";
    private static final String LS = "<br>";
    private final ValidateService logic = ValidateService.getInstance();
    private final Map<String, Function<HttpServletRequest, String>> getDispatch = new HashMap<>();
    private final Map<String, Function<HttpServletRequest, String>> postDispatch = new HashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType(TYPE);
        try {
            PrintWriter writer = new PrintWriter(resp.getOutputStream());
            writer.append(this.request(this.getDispatcherInit(), req));
            writer.flush();
        } catch (IOException e) {
            LOG.error("error IOException", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType(TYPE);
        try {
            PrintWriter writer = new PrintWriter(resp.getOutputStream());
            writer.append(this.request(this.postDispatcherInit(), req));
            writer.flush();
        } catch (IOException e) {
            LOG.error("error IOException", e);
        }
    }

    /**
     * Init get dispatcher.
     * @return get methods map.
     */
    private Map<String, Function<HttpServletRequest, String>> getDispatcherInit() {
        this.getDispatch.put(ID, this.findById());
        this.getDispatch.put(ALL, this.findAll());
        return this.getDispatch;
    }

    /**
     * Init post dispatcher.
     * @return post methods map.
     */
    private Map<String, Function<HttpServletRequest, String>> postDispatcherInit() {
        this.postDispatch.put(ADD, this.add());
        this.postDispatch.put(DEL, this.delete());
        this.postDispatch.put(UPDATE, this.update());
        return this.postDispatch;
    }

    /**
     * Update User.
     * @return answer.
     */
    public Function<HttpServletRequest, String> update() {
        return req -> {
            String result = UNF;
            User user = this.logic.update(req);
            if (user != null) {
                result = Joiner.on(" ").join(UPDATE_TO, user);
            }
            return result;
        };
    }

    /**
     * Delete User.
     * @return answer.
     */
    public Function<HttpServletRequest, String> delete() {
        return req -> {
            User user = this.logic.delete(req);
            String result = UNF;
            if (user != null) {
                result = Joiner.on(" ").join(DELETE, user);
            }
            return result;
        };
    }

    /**
     * Add new User.
     * @return answer.
     */
    public Function<HttpServletRequest, String> add() {
        return req -> {
            User user = this.logic.add(req);
            String result = UR;
            if (user != null) {
               result = Joiner.on(" ").join(ANU, user);
            }
            return result;
        };
    }

    /**
     * Find User by id.
     * @return answer.
     */
    public Function<HttpServletRequest, String> findById() {
        return req -> {
            User user = this.logic.findById(req);
            String result = UNF;
            if (user != null) {
                result = user.toString();
            }
            return result;
        };
    }

    /**
     * Find all User.
     * @return answer.
     */
    public Function<HttpServletRequest, String> findAll() {
        return req -> {
            Set<User> users = this.logic.findAll();
            String result = UNF;
            if (!users.isEmpty()) {
                result = Joiner.on(LS).join(users);
            }
            return result;
        };
    }

    /**
     * Dispatcher do.
     * @param dispatcher dispatcher map.
     * @param req request
     * @return answer
     */
    public String request(
            final Map<String, Function<HttpServletRequest, String>> dispatcher,
            final HttpServletRequest req) {
        return dispatcher.get(
                req.getParameterNames().nextElement()
        ).apply(req);
    }
}
