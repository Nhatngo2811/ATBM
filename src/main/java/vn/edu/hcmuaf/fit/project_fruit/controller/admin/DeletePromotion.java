package vn.edu.hcmuaf.fit.project_fruit.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.project_fruit.dao.PromotionsDao;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "DeletePromotion", urlPatterns = {"/remove-promotion"})
public class DeletePromotion extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String promotionId = request.getParameter("pid");

        // Kiểm tra ID khuyến mãi
        if (promotionId == null || promotionId.trim().isEmpty()) {
            response.sendRedirect("promotion-list.jsp?status=invalid"); // ID không hợp lệ
            return;
        }
        try {
            // Xóa khuyến mãi sử dụng DAO
            boolean isDeleted = PromotionsDao.deletePromotionById(promotionId);

            if (isDeleted) {
                System.out.println("Xóa thành công khuyến mãi với ID: " + promotionId);
                response.sendRedirect(request.getContextPath() + "/admin?status=success"); // Thành công
            } else {
                System.err.println("Không tìm thấy khuyến mãi với ID: " + promotionId);
                response.sendRedirect(request.getContextPath() + "/admin?status=error"); // Xóa thất bại
            }
        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi xóa khuyến mãi với ID: " + promotionId);
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin?status=loi"); // Lỗi SQL
        }
    }
}
