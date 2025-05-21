package vn.edu.hcmuaf.fit.project_fruit.utils;

import jakarta.mail.Session;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class EmailUtils {
    // Cấu hình tài khoản gửi email (SMTP Gmail)
    private static final String FROM_EMAIL = "22130205@st.hcmuaf.edu.vn";
    private static final String FROM_PASSWORD = "mdui mlvf rnjj cnbg";

    // Gửi email xác thực
    public static void sendVerificationEmail(String toEmail, String token) {
        String subject = "Xác nhận đăng ký tài khoản VitaminFruit";
        String verificationLink = "http://localhost:8091/project_fruit/verify?token=" + token;
        String content = "<h3>Chào bạn,</h3>" +
                "<p>Vui lòng nhấn vào liên kết dưới đây để xác nhận đăng ký tài khoản:</p>" +
                "<a href='" + verificationLink + "'>Xác nhận tài khoản</a>" +
                "<p>Nếu bạn không thực hiện đăng ký, hãy bỏ qua email này.</p>";

        sendEmail(toEmail, subject, content);
    }

    // Hàm gửi email chung
    public static void sendEmail(String to, String subject, String htmlContent) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // TLS
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, FROM_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setContent(htmlContent, "text/html; charset=utf-8");

            Transport.send(message);
            System.out.println("✅ Gửi email xác thực đến: " + to);
        } catch (MessagingException e) {
            System.err.println("❌ Lỗi khi gửi email: " + e.getMessage());
        }
    }
    public static void sendOtpEmail(String toEmail, String otpCode) {
        String subject = "Mã xác thực OTP - VitaminFruit";
        String content = "<p>Mã OTP của bạn là: <strong>" + otpCode + "</strong></p>" +
                "<p>Mã sẽ hết hạn sau 5 phút.</p>";
        sendEmail(toEmail, subject, content);
    }

}
