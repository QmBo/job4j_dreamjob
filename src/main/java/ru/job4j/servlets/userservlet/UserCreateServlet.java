package ru.job4j.servlets.userservlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.lang.String.format;

/**
 * UserCreateServlet
 * @author Victor Egorov (qrioflat@gmail.com).
 * @version 0.1
 * @since 13.02.2020
 */
public class UserCreateServlet extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(UserCreateServlet.class);
    private static final String DEF_PHOTO = "default.png";
    private static final String PHOTO_ID = "photoId";
    private final ValidateService service = ValidateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.getRequestDispatcher("/WEB-INF/views/create.jsp").forward(req, resp);
        } catch (Exception e) {
            LOG.error("Exception", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            this.add(req);
            resp.sendRedirect(format("%s/", req.getContextPath()));
        } catch (IOException e) {
            LOG.error("IOException", e);
        }
    }

    private void add(HttpServletRequest req) {
        ImageUploader.upload(this, req);
        if (req.getAttribute(PHOTO_ID) == null) {
            req.setAttribute(PHOTO_ID, DEF_PHOTO);
        }
        this.service.add(req);
    }
}
