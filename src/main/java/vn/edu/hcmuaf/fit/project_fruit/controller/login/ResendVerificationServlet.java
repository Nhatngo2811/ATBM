package vn.edu.hcmuaf.fit.project_fruit.controller.login;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.project_fruit.dao.model.User;
import vn.edu.hcmuaf.fit.project_fruit.service.UserService;
import vn.edu.hcmuaf.fit.project_fruit.utils.EmailUtils;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/resend-verification")
public class ResendVerificationServlet extends HttpServlet {
    private final UserService userService = new UserService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + "/user/login.jsp?message=Hãy đăng nhập để tiếp tục.");
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        User user = userService.getUserByEmail(email);

        if (user != null && !user.isVerified()) {
            String token = user.getVerifyToken();
            if (token == null || token.isEmpty()) {
                token = UUID.randomUUID().toString();
                user.setVerifyToken(token);
                userService.updateVerifyToken(email, token);
            }

            EmailUtils.sendVerificationEmail(email, token);

            // ✅ Hiển thị thông báo gửi lại thành công
            response.sendRedirect(request.getContextPath() + "/user/login.jsp?message=Đã gửi lại email xác thực đến " + email);
        } else {
            // ✅ Nếu user đã xác thực hoặc không tồn tại
            response.sendRedirect(request.getContextPath() + "/user/login.jsp?message=Tài khoản đã xác thực hoặc không tồn tại.");
        }
    }
}


