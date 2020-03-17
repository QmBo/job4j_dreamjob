package ru.job4j.servlet.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class GreetingServlet extends HttpServlet {
    private final static Logger LOG = LogManager.getLogger(GreetingServlet.class);

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        String name = req.getParameter("name");
        try {
            PrintWriter writer = new PrintWriter(resp.getOutputStream());
            writer.println(name);
            writer.flush();
        } catch (IOException e) {
           LOG.error("IOException", e);
        }
    }
}
