package ru.job4j.servlet.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Logout extends HttpServlet {
    private final static Logger LOG = LogManager.getLogger(Logout.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().invalidate();
        try {
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        } catch (Exception e) {
           LOG.error("Exception", e);
        }
    }
}
