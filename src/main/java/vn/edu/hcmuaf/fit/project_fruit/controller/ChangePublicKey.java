package vn.edu.hcmuaf.fit.project_fruit.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.edu.hcmuaf.fit.project_fruit.dao.PublicKeyDao;
import vn.edu.hcmuaf.fit.project_fruit.dao.model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.stream.Collectors;

@WebServlet("/change-public-key")
public class ChangePublicKey extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        HttpSession session = req.getSession();

        // Lấy user từ session
        User user = (User) session.getAttribute("user");
        JsonObject result = new JsonObject();

        if (user == null) {
            result.addProperty("success", false);
            result.addProperty("message", " Bạn chưa đăng nhập.");
            resp.getWriter().write(result.toString());
            return;
        }

        int accountId = user.getId_account(); //  lấy account ID từ User

        //  Đọc JSON body từ fetch
        String body = new BufferedReader(new InputStreamReader(req.getInputStream()))
                .lines().collect(Collectors.joining());
        JsonObject json = JsonParser.parseString(body).getAsJsonObject();
        String publicKey = json.get("publicKey").getAsString();
        String otpCode = json.get("otpCode").getAsString();

        //  Làm sạch public key nếu có BEGIN/END và xuống dòng
        publicKey = publicKey
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s+", "") // bỏ tất cả xuống dòng, tab, space
                .trim();

        //  Kiểm tra session OTP
        String sessionOtp = (String) session.getAttribute("otp_change_key");
        Long expireTime = (Long) session.getAttribute("otp_expire_time");

        if (sessionOtp == null || expireTime == null) {
            result.addProperty("success", false);
            result.addProperty("message", " Bạn chưa yêu cầu OTP.");
        } else if (System.currentTimeMillis() > expireTime) {
            result.addProperty("success", false);
            result.addProperty("message", " Mã OTP đã hết hạn.");
        } else if (!otpCode.equals(sessionOtp)) {
            result.addProperty("success", false);
            result.addProperty("message", " Mã OTP không đúng.");
        } else {
            try {
                PublicKeyDao.savePublicKey(accountId, publicKey);
                session.removeAttribute("otp_change_key");
                session.removeAttribute("otp_expire_time");
                result.addProperty("success", true);
                result.addProperty("message", " Cập nhật khóa thành công.");
            } catch (SQLException e) {
                result.addProperty("success", false);
                result.addProperty("message", " Lỗi khi cập nhật khóa.");
                e.printStackTrace();
            }
        }

        resp.getWriter().write(result.toString());
    }
}
