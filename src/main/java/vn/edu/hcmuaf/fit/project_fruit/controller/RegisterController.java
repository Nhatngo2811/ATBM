package vn.edu.hcmuaf.fit.project_fruit.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.project_fruit.dao.model.User;
import vn.edu.hcmuaf.fit.project_fruit.service.UserService;

import java.io.IOException;
import java.util.UUID;

@WebServlet(name = "RegisterController", value = "/register")
public class RegisterController extends HttpServlet {
    private final UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String token = UUID.randomUUID().toString();
        User user = new User();

        // Gán lại để hiển thị lại form nếu lỗi
        request.setAttribute("oldFullName", fullName);
        request.setAttribute("oldEmail", email);

        // Xử lý lỗi từng bước
        if (userService.getUserByEmail(email) != null) {
            request.setAttribute("errorMessage", "Email đã tồn tại.");
            request.getRequestDispatcher("/user/register.jsp").forward(request, response);
            return;
        }

        if (!password.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "Mật khẩu xác nhận không khớp.");
            request.getRequestDispatcher("/user/register.jsp").forward(request, response);
            return;
        }

        if (!userService.isStrongPassword(password)) {
            request.setAttribute("errorMessage", "Mật khẩu phải có ít nhất 8 ký tự, chứa cả chữ và số.");
            request.getRequestDispatcher("/user/register.jsp").forward(request, response);
            return;
        }

        // Nếu không có lỗi, tiến hành đăng ký
        boolean isRegistered = userService.registerUser(email, password, confirmPassword, fullName);

        if (isRegistered) {
            request.setAttribute("email", email); // Truyền email để hiển thị trong form OTP
            request.getRequestDispatcher("/user/verify-otp.jsp").forward(request, response);
        }
        else {
            request.setAttribute("errorMessage", "Đăng ký thất bại do lỗi hệ thống. Vui lòng thử lại.");
            request.getRequestDispatcher("/user/register.jsp").forward(request, response);
        }
    }
}
