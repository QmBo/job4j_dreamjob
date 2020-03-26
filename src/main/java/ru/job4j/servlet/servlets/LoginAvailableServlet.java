package ru.job4j.servlet.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.servlet.logic.ValidateService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
/**
 * LoginAvailableServlet
 * @author Victor Egorov (qrioflat@gmail.com).
 * @version 0.1
 * @since 26.03.2020
 */
public class LoginAvailableServlet extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(LoginAvailableServlet.class);
    private final ValidateService service = ValidateService.getInstance();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        try {
            PrintWriter writer = new PrintWriter(resp.getOutputStream());
            writer.println(this.available(req));
            writer.flush();
        } catch (IOException e) {
            LOG.error("IOException", e);
        }
    }

    /**
     * Check available login. Create JSON object like {"login": true}
     * @param req request
     * @return JSON object
     */
    private String available(final HttpServletRequest req) {
        return String.format("{\"login\": %s}", this.service.available(req));
    }
}
