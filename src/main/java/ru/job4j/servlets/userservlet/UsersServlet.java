package ru.job4j.servlets.userservlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.lang.String.format;

/**
 * UsersServlet
 * @author Victor Egorov (qrioflat@gmail.com).
 * @version 0.1
 * @since 13.02.2020
 */
public class UsersServlet extends HttpServlet {
    private static final String DEF_PHOTO = "default.png";
    private static final Logger LOG = LogManager.getLogger(UsersServlet.class);
    private final ValidateService service = ValidateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.setAttribute("users", service.findAll());
            req.getRequestDispatcher("/WEB-INF/views/list.jsp").forward(req, resp);
        } catch (Exception e) {
            LOG.error("Exception", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        this.deleteUser(req);
        try {
            resp.sendRedirect(format("%s/", req.getContextPath()));
        } catch (IOException e) {
            LOG.error("IOException", e);
        }
    }

    /**
     * Delete user. If User have a photo id, try to delete photo file from image`s directory.
     * @param req request
     */
    private void deleteUser(HttpServletRequest req) {
        User result = this.service.delete(req);
        if (result != null) {
            String photoId = result.getPhotoId();
            if (photoId != null && !DEF_PHOTO.equals(photoId)) {
                ImageUploader.delete(this, photoId);
            }
        }
    }
}
