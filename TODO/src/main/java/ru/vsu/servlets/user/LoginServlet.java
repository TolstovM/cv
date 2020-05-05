package ru.vsu.servlets.user;

import ru.vsu.model.User;
import ru.vsu.services.UserService;
import ru.vsu.servlets.todo.IndexServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(LoginServlet.URL)
public class LoginServlet extends HttpServlet {

    public static final String URL = "/user/login";
    public static final String DISPATCHER_PATH = "/user/login.jsp";
    public static final String USERNAME_KEY = "username";
    public static final String PASSWORD_KEY = "password";
    public static final String USER_ID_KEY = "userId";
    public static final String LOGGED_KEY = "logged";
    public static final boolean LOGGED = true;

    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(DISPATCHER_PATH).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter(USERNAME_KEY);
        String password = req.getParameter(PASSWORD_KEY);
        Optional<Long> id = userService.login(username, password);
        if (id.isPresent()) {
            req.getSession().setAttribute(USER_ID_KEY, id.get());
            req.getSession().setAttribute(LOGGED_KEY, LOGGED);
            resp.sendRedirect(req.getContextPath() + IndexServlet.URL);
        } else {
            req.getRequestDispatcher(DISPATCHER_PATH).forward(req, resp);
        }
    }
}
