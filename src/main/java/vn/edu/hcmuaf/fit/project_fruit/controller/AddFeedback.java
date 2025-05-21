package vn.edu.hcmuaf.fit.project_fruit.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.project_fruit.dao.db.DbConnect;
import vn.edu.hcmuaf.fit.project_fruit.dao.model.User;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

@WebServlet("/AddFeedbackServlet")
public class AddFeedback extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy dữ liệu từ form
        String content = request.getParameter("content");
        String ratingStr = request.getParameter("rating");
        String idProductStr = request.getParameter("productId");

        // Lấy id_account từ session
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/product-detail?pid=" + idProductStr + "&message=not_logged_in");
            return;
        }
        int idAccount = user.getId_account();

        try {
            // Kiểm tra dữ liệu từ form
            if (content == null || content.trim().isEmpty() ||
                    ratingStr == null || ratingStr.trim().isEmpty() ||
                    idProductStr == null || idProductStr.trim().isEmpty()) {
                response.sendRedirect("/product-detail?message=invalid_input");
                return;
            }

            // Chuyển đổi giá trị từ chuỗi sang kiểu số
            int idProduct = Integer.parseInt(idProductStr);
            double rating = Double.parseDouble(ratingStr);

            // Lấy ngày hiện tại khi nhấn nút Submit
            LocalDate today = LocalDate.now();

            // Câu lệnh SQL để thêm dữ liệu
            String query = "INSERT INTO feedbacks (id_product, id_account, content, date_create, rating) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = DbConnect.getPreparedStatement(query, true)) {
                preparedStatement.setInt(1, idProduct);          // ID sản phẩm
                preparedStatement.setInt(2, idAccount);          // ID tài khoản
                preparedStatement.setString(3, content);         // Nội dung bình luận
                preparedStatement.setDate(4, java.sql.Date.valueOf(today)); // Ngày tạo
                preparedStatement.setDouble(5, rating);          // Đánh giá

                int rowsInserted = preparedStatement.executeUpdate();

                // Kiểm tra kết quả và điều hướng
                if (rowsInserted > 0) {
                    response.sendRedirect(request.getContextPath() + "/product-detail?pid=" + idProductStr + "&message=success");
                } else {
                    response.sendRedirect(request.getContextPath() + "/product-detail?pid=" + idProductStr + "&message=insert_failed");
                }
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("/product-detail?message=invalid_format");
        } catch (SQLException e) {
            response.sendRedirect(request.getContextPath() + "/product-detail?pid=" + idProductStr + "&message=error");
        }
    }
}
