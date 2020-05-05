package ru.vsu.servlets.todo;

import ru.vsu.model.User;
import ru.vsu.services.TODOService;
import ru.vsu.servlets.user.LoginServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(IndexServlet.URL)
public class IndexServlet extends HttpServlet {
    public static final String URL = "/todo/index";
    public static final String DISPATCHER_PATH = "/todo/index.jsp";
    public static final String VIEW_TODO_ITEMS_KEY = "items";

    private TODOService todoService = new TODOService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long userId = (Long) req.getSession().getAttribute(LoginServlet.USER_ID_KEY);
        req.setAttribute(VIEW_TODO_ITEMS_KEY, todoService.getAllForUser(new User(userId)));
        req.getRequestDispatcher(DISPATCHER_PATH).forward(req, resp);
    }
}
