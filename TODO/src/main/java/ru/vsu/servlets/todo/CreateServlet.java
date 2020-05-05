package ru.vsu.servlets.todo;

import ru.vsu.model.TODOItem;
import ru.vsu.services.TODOService;
import ru.vsu.servlets.user.LoginServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(CreateServlet.URL)
public class CreateServlet extends HttpServlet {
    public static final String URL = "/todo/create";
    public static final String DISPATCHER_PATH = "/todo/create.jsp";
    public static final String COMMENT_KEY = "comment";
    public static final boolean IS_DONE_DEFAULT = false;

    private TODOService todoService = new TODOService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(DISPATCHER_PATH).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String comment = req.getParameter(COMMENT_KEY);
        Long userId = (Long) req.getSession().getAttribute(LoginServlet.USER_ID_KEY);
        todoService.create(new TODOItem(userId, IS_DONE_DEFAULT, comment));
        resp.sendRedirect(req.getContextPath() + IndexServlet.URL);
    }
}
