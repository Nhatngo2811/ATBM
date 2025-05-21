package vn.edu.hcmuaf.fit.project_fruit.controller.admin;
import vn.edu.hcmuaf.fit.project_fruit.dao.SupplierDao;
import vn.edu.hcmuaf.fit.project_fruit.dao.model.Supplier;

import org.json.JSONObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
@WebServlet("/get-supplier")
public class GetSupplierServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        SupplierDao dao = new SupplierDao();
        Supplier s = dao.getSupplierById(id);

        JSONObject json = new JSONObject();
        json.put("id_supplier", s.getId_supplier());
        json.put("name", s.getName());
        json.put("address", s.getAddress());
        json.put("email", s.getEmail());
        json.put("phone_number", s.getPhone_number());
        json.put("id_category", s.getId_category());
        json.put("rating", s.getRating());

        response.setContentType("application/json");
        response.getWriter().write(json.toString());
    }
}

