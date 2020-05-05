package ru.vsu.servlets.todo;

import ru.vsu.model.TODOItem;
import ru.vsu.services.TODOService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(UpdateServlet.URL)
public class UpdateServlet extends HttpServlet {

    public static final String URL = "/todo/update";
    public static final String ID_KEY = "id";

    private TODOService todoService = new TODOService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter(ID_KEY);
        Optional<TODOItem> itemOptional = todoService.getById(new TODOItem(Long.parseLong(id)));
        if (itemOptional.isPresent()) {
            TODOItem item = itemOptional.get();
            item.setIsDone(!item.getIsDone());
            todoService.update(item);
        }
        resp.sendRedirect(req.getContextPath() + IndexServlet.URL);
    }
}
