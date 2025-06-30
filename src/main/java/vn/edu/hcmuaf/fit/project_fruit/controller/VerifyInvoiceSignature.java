package vn.edu.hcmuaf.fit.project_fruit.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.project_fruit.dao.InvoiceDao;
import vn.edu.hcmuaf.fit.project_fruit.dao.PublicKeyDao;
import vn.edu.hcmuaf.fit.project_fruit.dao.model.Invoice;
import vn.edu.hcmuaf.fit.project_fruit.dao.model.User;

import javax.crypto.Cipher;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.sql.SQLException;
import java.util.Base64;

@WebServlet("/verify-invoice-signature")
public class VerifyInvoiceSignature extends HttpServlet {

    private PublicKey getPublicKeyFromBase64(String base64Key) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(base64Key);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("application/json; charset=UTF-8");

        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) sb.append(line);
        }

        try {
            JsonObject json = JsonParser.parseString(sb.toString()).getAsJsonObject();

            // 🔒 Kiểm tra invoiceId và signature có tồn tại không
            if (!json.has("invoiceId") || json.get("invoiceId").isJsonNull() ||
                    !json.has("signature") || json.get("signature").isJsonNull()) {
                response.getWriter().write("{\"success\": false, \"message\": \"Thiếu dữ liệu invoiceId hoặc signature!\"}");
                return;
            }

            int invoiceId = json.get("invoiceId").getAsInt();
            String signature = json.get("signature").getAsString();

            // 👤 Lấy accountId từ session
            User user = (User) request.getSession().getAttribute("user");
            if (user == null) {
                response.getWriter().write("{\"success\": false, \"message\": \"Không tìm thấy thông tin người dùng!\"}");
                return;
            }
            int accountId = user.getId_account();

            // 🔑 Lấy public key
            String publicKeyBase64 = PublicKeyDao.getPublicKeyByAccountId(accountId);
            if (publicKeyBase64 == null) {
                response.getWriter().write("{\"success\": false, \"message\": \"Không tìm thấy khóa công khai trong cơ sở dữ liệu!\"}");
                return;
            }

            // 🧾 Lấy hóa đơn
            Invoice invoice = InvoiceDao.getInvoiceById(invoiceId);
            if (invoice == null) {
                response.getWriter().write("{\"success\": false, \"message\": \"Không tìm thấy hóa đơn!\"}");
                return;
            }

            // 🧮 Hash nội dung hóa đơn
            String invoiceText = InvoiceDao.invoiceToText(invoice).trim();
            String realHash = HashInvoice.hashSHA256(invoiceText);

            // 🔓 Giải mã chữ ký
            PublicKey pubKey = getPublicKeyFromBase64(publicKeyBase64);
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, pubKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(signature));
            String decryptedText = new String(decryptedBytes, StandardCharsets.UTF_8);


            boolean valid = realHash.equalsIgnoreCase(decryptedText);
            if (valid) {
                InvoiceDao.updateInvoiceStatus(invoiceId, "Đã xác thực");
                System.out.println("🔄 Đã cập nhật trạng thái đơn hàng #" + invoiceId + " thành 'Đã xác thực'");
            }

            // 🧾 Ghi log
            System.out.println("========== KIỂM TRA CHỮ KÝ ==========");
            System.out.println("🔹 ID người dùng: " + accountId);
            System.out.println("🔹 Mã hóa đơn: " + invoiceId);
            System.out.println("🔹 Văn bản gốc:\n" + invoiceText);
            System.out.println("🔹 SHA-256 thực tế:\n" + realHash);
            System.out.println("🔹 Chữ ký đã giải mã:\n" + decryptedText);
            System.out.println("🔹 Kết quả xác minh: " + (valid ? "✅ HỢP LỆ" : "❌ KHÔNG HỢP LỆ"));
            System.out.println("======================================");

            response.getWriter().write("{\"success\": true," +
                    "\"valid\": " + valid + "," +
                    "\"realHash\": \"" + realHash + "\"," +
                    "\"signedHash\": \"" + decryptedText + "\"," +
                    "\"message\": \"" + (valid ? "Xác thực thành công!" : "Chữ ký không hợp lệ!") + "\"}");
        } catch (SQLException e) {
            response.getWriter().write("{\"success\": false, \"message\": \"Lỗi DB: " + e.getMessage().replace("\"", "\\\"") + "\"}");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("{\"success\": false, \"message\": \"" + e.getMessage().replace("\"", "\\\"") + "\"}");
        }
    }


    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) sb.append(String.format("%02x", b));
        return sb.toString();
    }
}

