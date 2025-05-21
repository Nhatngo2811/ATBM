package vn.edu.hcmuaf.fit.project_fruit.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.project_fruit.dao.ProductDao;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "DeleteProduct", urlPatterns = {"/remove-product"})
public class DeleteProduct extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productId = request.getParameter("pid");

        // Kiểm tra ID sản phẩm
        if (productId == null || productId.trim().isEmpty()) {
            response.sendRedirect("product-list.jsp?status=invalid"); // ID không hợp lệ
            return;
        }

        try {
            // Xóa sản phẩm sử dụng DAO
            boolean isDeleted = ProductDao.deleteProductById(productId);

            if (isDeleted) {
                System.out.println("Xóa thành công sản phẩm với ID: " + productId);
                response.sendRedirect(request.getContextPath() + "/admin?status=success"); // Thành công
            } else {
                System.err.println("Không tìm thấy sản phẩm với ID: " + productId);
                response.sendRedirect(request.getContextPath() + "/admin?status=error"); // Xóa thất bại
            }
        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi xóa sản phẩm với ID: " + productId);
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin?status=loi"); // Lỗi SQL
        }
    }
}