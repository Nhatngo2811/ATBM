package vn.edu.hcmuaf.fit.project_fruit.controller.login;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.project_fruit.service.UserService;

import java.io.IOException;

@WebServlet("/verify-otp")
public class VerifyOtpServlet extends HttpServlet {
    private final UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String email = request.getParameter("email");
        String otp = request.getParameter("otp");

        if (userService.verifyOtp(email, otp)) {
            request.setAttribute("message", "success");
            request.getRequestDispatcher("/user/verify-otp.jsp").forward(request, response);
        } else {
            request.setAttribute("message", "error");
            request.setAttribute("errorText", "OTP không đúng hoặc đã hết hạn.");
            request.setAttribute("email", email);
            request.getRequestDispatcher("/user/verify-otp.jsp").forward(request, response);
        }
    }
}

