package vn.edu.hcmuaf.fit.project_fruit.dao;

import vn.edu.hcmuaf.fit.project_fruit.dao.db.DbConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PublicKeyDao {
    public static void savePublicKey(int accountId, String publicKeyBase64) throws SQLException {
        System.out.println("[savePublicKey] Bắt đầu cập nhật public key cho accountId = " + accountId);
        System.out.println("PublicKey (base64): " + publicKeyBase64);

        String sql = "INSERT INTO public_keys (account_id, public_key) VALUES (?, ?) " +
                "ON DUPLICATE KEY UPDATE public_key = ?";
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, accountId);
            stmt.setString(2, publicKeyBase64);
            stmt.setString(3, publicKeyBase64);

            int rowsAffected = stmt.executeUpdate();
            System.out.println(" [savePublicKey] Số dòng bị ảnh hưởng: " + rowsAffected);

        } catch (SQLException e) {
            System.err.println(" [savePublicKey] Lỗi khi cập nhật public key:");
            e.printStackTrace();
            throw e;
        }
    }

    public static String getPublicKeyByAccountId(int accountId) {
        System.out.println(" [getPublicKeyByAccountId] Truy vấn khóa của accountId = " + accountId);

        String sql = "SELECT public_key FROM public_keys WHERE account_id = ?";
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String key = rs.getString("public_key");
                System.out.println(" [getPublicKeyByAccountId] Lấy được khóa: " + key);
                return key;
            } else {
                System.out.println("[getPublicKeyByAccountId] Không tìm thấy khóa.");
            }
        } catch (SQLException e) {
            System.err.println(" [getPublicKeyByAccountId] Lỗi khi truy vấn khóa:");
            e.printStackTrace();
        }
        return null;
    }
}
