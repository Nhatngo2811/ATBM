package vn.edu.hcmuaf.fit.project_fruit.controller.admin;
import com.google.gson.Gson;
import vn.edu.hcmuaf.fit.project_fruit.dao.SupplierDao;
import vn.edu.hcmuaf.fit.project_fruit.dao.model.Supplier;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
@WebServlet("/update-supplier")
public class UpdateSupplierServlet extends HttpServlet {
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
            BufferedReader reader = request.getReader();
            Gson gson = new Gson();
            Supplier supplier = gson.fromJson(reader, Supplier.class);

            SupplierDao dao = new SupplierDao();
            dao.updateSupplier(supplier);

            response.getWriter().write("success");

        }
    }



