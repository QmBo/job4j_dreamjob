package ru.job4j.servlets.userservlet;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static java.lang.String.format;

public class AuthFilter implements Filter {
    private final ValidateService service = ValidateService.getInstance();
    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        if (!request.getRequestURI().contains("/login")) {
            HttpSession session = request.getSession();
            if (session.getAttribute("login") == null) {
                ((HttpServletResponse) resp).sendRedirect(format("%s/login", request.getContextPath()));
            } else {
                String login = (String) session.getAttribute("login");
                for (User user : this.service.findAll()) {
                    if (login.equals(user.getLogin())) {
                        session.setAttribute(
                                "userRole",
                                this.service.getRoles().get(
                                        user.getRole().getId()
                                ).getName()
                        );
                    }
                }
                chain.doFilter(req, resp);
            }
        } else {
            if (request.getSession().getAttribute("login") != null) {
                ((HttpServletResponse) resp).sendRedirect(format("%s/", request.getContextPath()));
            }
            chain.doFilter(req, resp);
        }
    }

    @Override
    public void destroy() {

    }
}
