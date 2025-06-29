package vn.edu.hcmuaf.fit.project_fruit.controller;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import vn.edu.hcmuaf.fit.project_fruit.dao.PublicKeyDao;
import vn.edu.hcmuaf.fit.project_fruit.dao.model.User;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@WebServlet("/upload-public-key")
@MultipartConfig
public class UploadPublicKey extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json; charset=UTF-8");

        try {
            // Lấy file
            Part filePart = request.getPart("publicKeyFile");
            if (filePart == null || filePart.getSize() == 0) {
                response.getWriter().write("{\"success\": false, \"message\": \"Chưa chọn file!\"}");
                return;
            }

            // Đọc và làm sạch nội dung
            InputStream input = filePart.getInputStream();
            String content = new String(input.readAllBytes(), StandardCharsets.UTF_8);
            content = content.replaceAll("-----BEGIN PUBLIC KEY-----", "")
                    .replaceAll("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s+", "");

            // Lấy accountId từ session
            User user = (User) request.getSession().getAttribute("user");
            if (user == null) {
                response.getWriter().write("{\"success\": false, \"message\": \"Không tìm thấy người dùng trong session.\"}");
                return;
            }

            int accountId = user.getId_account();

            // 🔍 Kiểm tra nếu đã tồn tại khóa trong DB
            String existing = PublicKeyDao.getPublicKeyByAccountId(accountId);
            if (existing != null) {
                response.getWriter().write("{\"success\": false, \"message\": \"Bạn đã tải lên khóa công khai trước đó.\"}");
                return;
            }

            // ✅ Lưu session + DB
            request.getSession().setAttribute("uploadedPublicKey", content);
            PublicKeyDao.savePublicKey(accountId, content);

            response.getWriter().write("{\"success\": true, \"message\": \"Đã tải lên và lưu khóa công khai thành công.\"}");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("{\"success\": false, \"message\": \"" + e.getMessage().replace("\"", "\\\"") + "\"}");
        }
    }
}
