package ru.vsu.filter;

import ru.vsu.servlets.user.LoginServlet;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/todo/*")
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        Object logged = req.getSession().getAttribute(LoginServlet.LOGGED_KEY);
        if (logged == null || !(boolean) logged) {
            HttpServletResponse resp = (HttpServletResponse) servletResponse;
            resp.sendRedirect(req.getContextPath() + LoginServlet.URL);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
