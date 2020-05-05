package ru.vsu.servlets.todo;

import ru.vsu.model.TODOItem;
import ru.vsu.services.TODOService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(DeleteServlet.URL)
public class DeleteServlet extends HttpServlet {

    public static final String URL = "/todo/delete";
    public static final String ID_KEY = "id";

    private TODOService todoService = new TODOService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter(ID_KEY);
        todoService.delete(new TODOItem(Long.parseLong(id)));
        resp.sendRedirect(req.getContextPath() + IndexServlet.URL);
    }
}
