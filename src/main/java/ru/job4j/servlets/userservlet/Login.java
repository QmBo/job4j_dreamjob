package ru.job4j.servlets.userservlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static java.lang.String.format;

public class Login extends HttpServlet {
    private final static Logger LOG = LogManager.getLogger(Login.class);
    private static final String LOGIN = "login";
    private final ValidateService service = ValidateService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        } catch (Exception e) {
            LOG.error("Represent Error", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        if (this.service.isCredentials(req)) {
            HttpSession session = req.getSession();
            session.setAttribute("login", req.getParameter(LOGIN));
            try {
                resp.sendRedirect(format("%s/", req.getContextPath()));
            } catch (IOException e) {
                LOG.error("IOException", e);
            }
        } else {
            req.setAttribute("error", "Credential error!");
            doGet(req, resp);
        }
    }
}
