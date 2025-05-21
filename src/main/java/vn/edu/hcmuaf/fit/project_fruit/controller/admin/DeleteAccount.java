package vn.edu.hcmuaf.fit.project_fruit.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.project_fruit.dao.db.DbConnect;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/remove-account")
public class DeleteAccount extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy ID khách hàng từ tham số URL
        String idCustomer = request.getParameter("pid");

        if (idCustomer == null || idCustomer.isEmpty()) {
            // Nếu không có idCustomer, quay lại với lỗi
            response.sendRedirect(request.getContextPath() + "/admin?status=error");
            return;
        }

        // Câu lệnh SQL để xóa tài khoản và khách hàng liên quan
        String deleteAccountQuery = "DELETE FROM accounts WHERE id_customer = ?";
        String deleteCustomerQuery = "DELETE FROM customers WHERE id_customer = ?";

        try (PreparedStatement psAccount = DbConnect.getPreparedStatement(deleteAccountQuery, true);
             PreparedStatement psCustomer = DbConnect.getPreparedStatement(deleteCustomerQuery, true)) {

            // Xóa tài khoản
            psAccount.setString(1, idCustomer);
            psAccount.executeUpdate();

            // Xóa khách hàng
            psCustomer.setString(1, idCustomer);
            psCustomer.executeUpdate();

            // Chuyển hướng về trang quản lý với trạng thái thành công
            response.sendRedirect(request.getContextPath() + "/admin?status=deleted");
        } catch (SQLException e) {
            e.printStackTrace();
            // Chuyển hướng về trang quản lý với trạng thái lỗi
            response.sendRedirect(request.getContextPath() + "/admin?status=error");
        }
    }
}
