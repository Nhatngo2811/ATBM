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

@WebServlet("/AddPromotionServlet")
public class AddPromotion extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy dữ liệu từ form
        String promotionName = request.getParameter("promotion_code");
        String description = request.getParameter("description_add");
        String startDate = request.getParameter("start_date");
        String endDate = request.getParameter("expiration_date");
        String discountPercent = request.getParameter("promotion_discount");
        String type = request.getParameter("promotion_type");

        // Câu lệnh SQL để thêm dữ liệu vào cơ sở dữ liệu
        String query = "INSERT INTO promotions (promotion_name, describe_1, start_date, end_date, percent_discount, type) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = DbConnect.getPreparedStatement(query, true)) {
            ps.setString(1, promotionName);
            ps.setString(2, description);
            ps.setString(3, startDate);
            ps.setString(4, endDate);
            ps.setString(5, discountPercent);
            ps.setString(6, type);

            // Thực hiện thêm dữ liệu
            ps.executeUpdate();

            // Điều hướng quay lại trang admin với anchor #
            response.sendRedirect(request.getContextPath() + "/admin#");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin#");
        }
    }
}
