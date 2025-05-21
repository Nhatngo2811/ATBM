package vn.edu.hcmuaf.fit.project_fruit.controller.admin;

import org.json.JSONObject;
import vn.edu.hcmuaf.fit.project_fruit.dao.SupplierDao;
import vn.edu.hcmuaf.fit.project_fruit.dao.model.Supplier;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
@WebServlet("/add-supplier-ajax")
public class AddSupplier extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) sb.append(line);

        try {
            JSONObject json = new JSONObject(sb.toString());

            String name = json.getString("name");
            String address = json.getString("address");
            String email = json.getString("email");
            String phone = json.getString("phone_number");
            int idCategory = json.getInt("id_category");

            Supplier supplier = new Supplier(
                    0, name, address, email, phone,
                    "Active", 4.5, "", idCategory
            );

            SupplierDao dao = new SupplierDao();
            boolean ok = dao.addSupplier(supplier);

            JSONObject res = new JSONObject();
            res.put("success", ok);
            if (!ok) res.put("message", "Lỗi khi thêm vào DB");
            response.getWriter().write(res.toString());

        } catch (Exception e) {
            e.printStackTrace();
            JSONObject error = new JSONObject();
            error.put("success", false);
            error.put("message", e.getMessage());
            response.getWriter().write(error.toString());
        }
    }
}