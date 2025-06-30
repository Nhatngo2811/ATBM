package vn.edu.hcmuaf.fit.project_fruit.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.edu.hcmuaf.fit.project_fruit.dao.model.User;
import vn.edu.hcmuaf.fit.project_fruit.utils.EmailUtils;

import java.io.IOException;

@WebServlet("/send-otp-change-key")
public class SendOtpChangeKey extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        System.out.println("Gửi OTP đổi khóa");
        if (user == null) {
            System.out.println("Không có session đăng nhập.");
            resp.setStatus(401);
            resp.getWriter().write("Bạn chưa đăng nhập.");
            return;
        }

        Integer accountId = user.getId_account();
        String email = user.getEmail();

        System.out.println("Đăng nhập với accountId: " + accountId + ", email: " + email);

        String otp = String.valueOf((int)(Math.random() * 900000 + 100000));
        session.setAttribute("otp_change_key", otp);
        session.setAttribute("otp_expire_time", System.currentTimeMillis() + 5 * 60 * 1000);

        EmailUtils.sendOtpEmail(email, otp);
        System.out.println("Mã OTP đã gửi: " + otp + " đến " + email);

        resp.getWriter().write("Mã OTP đã được gửi đến email.");
    }
}
