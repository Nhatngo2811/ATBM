package vn.edu.hcmuaf.fit.project_fruit.dao;

import vn.edu.hcmuaf.fit.project_fruit.dao.db.DbConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PublicKeyDao {
    public static void savePublicKey(int accountId, String publicKeyBase64) throws SQLException {
        String sql = "INSERT INTO public_keys (account_id, public_key) VALUES (?, ?) " +
                "ON DUPLICATE KEY UPDATE public_key = VALUES(public_key)";
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, accountId);
            stmt.setString(2, publicKeyBase64);
            stmt.executeUpdate();
        }
    }

    public static String getPublicKeyByAccountId(int accountId) {
        String sql = "SELECT public_key FROM public_keys WHERE account_id = ?";
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("public_key");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // hoặc log
        }
        return null; // Không tìm thấy
    }

}
