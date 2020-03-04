package ru.job4j.servlets.userservlet;

import com.google.common.base.Joiner;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static java.lang.String.format;

public class ImageUploader {
    private static final Logger LOG = LogManager.getLogger(ImageUploader.class);
    private static final String PHOTO_ID = "photoId";
    private static final String IMAGES_FOLDER = "WEB-INF/bin/images";

    public static HttpServletRequest upload(HttpServlet servlet, HttpServletRequest req) {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletContext servletContext = servlet.getServletConfig().getServletContext();
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);
        ServletFileUpload upload = new ServletFileUpload(factory);
        try {
            List<FileItem> items = upload.parseRequest(req);
            File folder = new File(servletContext.getRealPath(IMAGES_FOLDER));
            if (!folder.exists()) {
                if (!folder.mkdirs()) {
                    throw new IOException(format("%s make exception", folder));
                }
                //TODO UPLOAD DEFAULT IMAGE
            }
            for (FileItem item : items) {
                if (!item.isFormField() && item.getSize() != 0L) {
                    String fileName = format("%s_%s", System.currentTimeMillis(), item.getName());
                    File file = new File(Joiner.on(File.separatorChar).join(folder, fileName));
                    try (FileOutputStream out = new FileOutputStream(file)) {
                        out.write(item.getInputStream().readAllBytes());
                        req.setAttribute(PHOTO_ID, fileName);
                    }
                } else {
                    String name = item.getFieldName();
                    req.setAttribute(name, item.getString());
                }
            }
        } catch (Exception e) {
            LOG.error("Exception", e);
        }
        return req;
    }

    public static boolean delete(HttpServlet servlet, String photoId) {
        boolean result = false;
        File folder = new File(servlet.getServletConfig().getServletContext().getRealPath(IMAGES_FOLDER));
        File image = new File(Joiner.on(File.separatorChar).join(folder, photoId));
        if (image.exists() && image.isFile()) {
            result = image.delete();
        }
        return result;
    }
}
