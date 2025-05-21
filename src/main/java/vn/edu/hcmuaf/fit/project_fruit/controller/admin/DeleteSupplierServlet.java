package vn.edu.hcmuaf.fit.project_fruit.controller.admin;
import org.json.JSONObject;
import vn.edu.hcmuaf.fit.project_fruit.dao.SupplierDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
@WebServlet("/delete-supplier")
public class DeleteSupplierServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json");
        int id = Integer.parseInt(req.getParameter("id"));

        SupplierDao dao = new SupplierDao();
        boolean deleted = dao.deleteSupplierById(id);

        JSONObject result = new JSONObject();
        result.put("success", deleted);
        resp.getWriter().write(result.toString());
    }
}
