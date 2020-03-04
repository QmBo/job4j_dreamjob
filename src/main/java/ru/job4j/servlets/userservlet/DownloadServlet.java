package ru.job4j.servlets.userservlet;

import com.google.common.base.Joiner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static java.lang.String.format;
/**
 * DownloadServlet
 * @author Victor Egorov (qrioflat@gmail.com).
 * @version 0.1
 * @since 03.03.2020
 */
public class DownloadServlet extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(DownloadServlet.class);
    private static final String IMAGES_FOLDER = "WEB-INF/bin/images";
    private static final String DEF_PHOTO = "default.png";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String name = req.getParameter("name");
        resp.setContentType(format("name=%s", name));
        resp.setContentType("image/png");
        resp.setHeader("Content-Disposition", format("attachment; filename=\"%s\"", name));
        File folder = new File(this.getServletConfig().getServletContext().getRealPath(IMAGES_FOLDER));
        File file = new File(Joiner.on(File.separatorChar).join(folder, name));
        try (FileInputStream in = new FileInputStream(file)) {
            resp.getOutputStream().write(in.readAllBytes());
        } catch (FileNotFoundException e) {
            file = new File(Joiner.on(File.separatorChar).join(folder, DEF_PHOTO));
            try (FileInputStream in = new FileInputStream(file)) {
                resp.getOutputStream().write(in.readAllBytes());
            } catch (Exception e1) {
                LOG.error("Default Image Exception", e1, e);
            }
        } catch (Exception e) {
            LOG.error("Exception", e);
        }
    }
}