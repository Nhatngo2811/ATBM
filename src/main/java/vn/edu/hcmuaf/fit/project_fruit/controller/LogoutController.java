package vn.edu.hcmuaf.fit.project_fruit.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
@WebServlet(name = "LogoutController", value = "/logout")
public class LogoutController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Lấy session hiện tại
        HttpSession session = request.getSession(false);

        if (session != null) {
            // Xóa toàn bộ session
            session.invalidate(); // Xóa tất cả dữ liệu session
        }

        // Chuyển hướng người dùng về trang danh sách sản phẩm (list-product)
        response.sendRedirect("home");
    }
}
