package ru.vsu.servlets.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.model.User;
import ru.vsu.services.UserService;
import ru.vsu.services.exceptions.RegistrationException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(SignUpServlet.URL)
public class SignUpServlet extends HttpServlet {

    public static final String DISPATCHER_PATH = "/user/signup.jsp";
    public static final String EMAIL_KEY = "email";
    public static final String URL = "/user/signup";
    public static final String USERNAME_KEY = "username";
    public static final String PASSWORD_KEY = "password";
    public static final String ERROR_KEY = "error";
    public static final String ERROR_DISPATCHER_PATH = "/error.jsp";

    private static final Logger logger = LoggerFactory.getLogger(SignUpServlet.class);
    public static final String REGISTRATION_EXCEPTION_LOGGER = "Registration exception";

    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(DISPATCHER_PATH).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter(EMAIL_KEY);
        String username = req.getParameter(USERNAME_KEY);
        String password = req.getParameter(PASSWORD_KEY);
        User user = new User(username, email, password);
        try {
            userService.register(user);
            resp.sendRedirect(req.getContextPath() + LoginServlet.URL);
        } catch (RegistrationException e) {
            logger.error(REGISTRATION_EXCEPTION_LOGGER, e);
            req.setAttribute(ERROR_KEY, e);
            req.getRequestDispatcher(ERROR_DISPATCHER_PATH).forward(req, resp);
        } catch (SQLException e) {
            logger.error(e.getSQLState(), e);
        }
    }
}
