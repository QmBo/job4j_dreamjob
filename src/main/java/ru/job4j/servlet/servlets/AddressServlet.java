package ru.job4j.servlet.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.servlet.logic.ValidateService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
/**
 * AddressServlet
 * @author Victor Egorov (qrioflat@gmail.com).
 * @version 0.1
 * @since 26.03.2020
 */
public class AddressServlet extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(AddressServlet.class);

    private final ValidateService service = ValidateService.getInstance();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        try {
            PrintWriter writer = new PrintWriter(resp.getOutputStream());
            writer.println(this.getAddress(req));
            writer.flush();
        } catch (IOException e) {
            LOG.error("IOException", e);
        }
    }
    /**
     * Get addresses. Create JSON object like "e": [{"id": 1, "name": "Russia"}, {"id": 2, "name": "Germany"}]}
     * @param req request
     * @return JSON object
     */
    private String getAddress(HttpServletRequest req) {
        String result = "";
        LinkedHashMap<Integer, String> address = this.service.getAllAddresses(req);
        if (!address.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            sb.append("{\"e\": [");
            LinkedList<Integer> keySet = new LinkedList<>(address.keySet());
            while (!keySet.isEmpty()) {
                Integer key = keySet.getFirst();
                sb.append("{\"id\": ").append(key).append(", ");
                sb.append("\"name\": \"").append(address.get(key)).append("\"}");
                keySet.remove(key);
                if (!keySet.isEmpty()) {
                    sb.append(", ");
                }
            }
            sb.append("]}");
            result = sb.toString();
        }
        return result;
    }
}
