package vn.edu.hcmuaf.fit.project_fruit.dao;

import vn.edu.hcmuaf.fit.project_fruit.dao.db.DbConnect;
import vn.edu.hcmuaf.fit.project_fruit.dao.model.Shipping;

import java.sql.*;

public class ShippingDAO {
    public boolean insertShipping(Shipping shipping) {
        String query = "INSERT INTO shipping (id_invoice, shipping_method_id, shipping_fee, status, address) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DbConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, shipping.getIdInvoice());
            stmt.setInt(2, shipping.getShippingMethodId());
            stmt.setDouble(3, shipping.getShippingFee());
            stmt.setString(4, shipping.getStatus());
            stmt.setString(5, shipping.getAddress());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

