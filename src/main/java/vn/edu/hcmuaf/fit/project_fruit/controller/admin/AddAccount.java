package vn.edu.hcmuaf.fit.project_fruit.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;
import vn.edu.hcmuaf.fit.project_fruit.dao.db.DbConnect;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
@WebServlet("/AddAccountServlet")
public class AddAccount extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy dữ liệu từ form
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm-password");
        String role = request.getParameter("role");

        // Kiểm tra mật khẩu khớp
        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Mật khẩu và xác nhận mật khẩu không khớp.");
            request.getRequestDispatcher("/admin.jsp").forward(request, response);
            return;
        }

        // Mã hóa mật khẩu bằng BCrypt
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        // SQL queries
        String customerInsertQuery = "INSERT INTO customers (customer_name, customer_phone, address) VALUES (?, ?, ?)";
        String customerSelectQuery = "SELECT id_customer FROM customers WHERE customer_name = ? AND customer_phone = ? AND address = ? ORDER BY id_customer DESC LIMIT 1";
        String accountInsertQuery = "INSERT INTO accounts (email, password, role, create_date, id_customer) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement psInsertCustomer = DbConnect.getPreparedStatement(customerInsertQuery, true);
             PreparedStatement psSelectCustomer = DbConnect.getPreparedStatement(customerSelectQuery, true);
             PreparedStatement psInsertAccount = DbConnect.getPreparedStatement(accountInsertQuery, true)) {

            // Bước 1: Thêm khách hàng
            psInsertCustomer.setString(1, username);
            psInsertCustomer.setString(2, "unknown"); // phone mặc định
            psInsertCustomer.setString(3, "unknown"); // address mặc định
            psInsertCustomer.executeUpdate();

            // Bước 2: Lấy ID của khách hàng vừa thêm
            psSelectCustomer.setString(1, username);
            psSelectCustomer.setString(2, "unknown");
            psSelectCustomer.setString(3, "unknown");
            int customerId = 0;
            try (ResultSet rs = psSelectCustomer.executeQuery()) {
                if (rs.next()) {
                    customerId = rs.getInt("id_customer");
                } else {
                    throw new SQLException("Không thể lấy ID của khách hàng mới.");
                }
            }

            // Bước 3: Thêm tài khoản với mật khẩu đã mã hóa
            psInsertAccount.setString(1, email);
            psInsertAccount.setString(2, hashedPassword); // Sử dụng mật khẩu đã mã hóa
            psInsertAccount.setString(3, role);
            psInsertAccount.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            psInsertAccount.setInt(5, customerId);
            psInsertAccount.executeUpdate();

            // Thành công
            response.sendRedirect(request.getContextPath() + "/admin?status=success");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin?status=error");
        }
    }
}
