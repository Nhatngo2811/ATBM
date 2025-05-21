package vn.edu.hcmuaf.fit.project_fruit.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.edu.hcmuaf.fit.project_fruit.service.UserService;
import vn.edu.hcmuaf.fit.project_fruit.utils.EmailUtils;

import java.io.IOException;

@WebServlet("/forgot-password")
public class ForgotPasswordController extends HttpServlet {
    private final UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        String newPassword = userService.recoverPassword(email);

        HttpSession session = request.getSession();

        if (newPassword != null) {
            String html = "<p>Bạn đã yêu cầu khôi phục mật khẩu tại VitaminFruit.</p>"
                    + "<p>Mật khẩu mới của bạn là: <b>" + newPassword + "</b></p>"
                    + "<p>Hãy đăng nhập và đổi lại mật khẩu.</p>";

            EmailUtils.sendEmail(email, "Khôi phục mật khẩu - VitaminFruit", html);

            session.setAttribute("successMessage", "Mật khẩu mới đã được gửi tới email của bạn.");
        } else {
            session.setAttribute("errorMessage", "Email không tồn tại hoặc lỗi khi gửi.");
        }

        // Dùng redirect để tránh lặp lại khi reload
        response.sendRedirect(request.getContextPath() + "/user/forgotPassword.jsp");
    }
}
