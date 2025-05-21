package vn.edu.hcmuaf.fit.project_fruit.controller.login;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.project_fruit.service.UserService;

import java.io.IOException;

@WebServlet("/verify")
public class VerifyEmailServlet extends HttpServlet {
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String token = request.getParameter("token");

        if (token != null && userService.verifyEmailByToken(token)) {
            request.setAttribute("message", "Xác thực email thành công!");
        } else {
            request.setAttribute("message", "Xác thực thất bại hoặc token không hợp lệ.");
        }

        request.getRequestDispatcher("/user/verify-result.jsp").forward(request, response);
    }
}