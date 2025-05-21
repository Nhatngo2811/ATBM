package vn.edu.hcmuaf.fit.project_fruit.dao;

import vn.edu.hcmuaf.fit.project_fruit.dao.db.DbConnect;
import vn.edu.hcmuaf.fit.project_fruit.dao.model.ShippingMethod;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShippingMethodDAO {
    public List<ShippingMethod> getAllShippingMethods() {
        List<ShippingMethod> shippingMethods = new ArrayList<>();
        String query = "SELECT * FROM shipping_methods";
        try (Connection conn = DbConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ShippingMethod method = new ShippingMethod(
                        rs.getInt("id"),
                        rs.getString("method_name"),
                        rs.getString("carrier"),
                        rs.getDouble("shipping_fee")
                );
                shippingMethods.add(method);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shippingMethods;
    }
    public ShippingMethod getShippingMethodById(int id) {
        String sql = "SELECT * FROM shipping_methods WHERE id = ?";
        try (Connection conn = DbConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new ShippingMethod(
                        rs.getInt("id"),
                        rs.getString("method_name"),
                        rs.getString("carrier"),
                        rs.getDouble("shipping_fee")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

