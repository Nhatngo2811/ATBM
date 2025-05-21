package vn.edu.hcmuaf.fit.project_fruit.dao;

import vn.edu.hcmuaf.fit.project_fruit.dao.db.DbConnect;
import vn.edu.hcmuaf.fit.project_fruit.dao.cart.CartProduct;
import vn.edu.hcmuaf.fit.project_fruit.dao.model.Invoice;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDetailDao {
    public void addInvoiceDetail(int invoiceId, CartProduct item) {
        String sql = """
            INSERT INTO invoices_details (id_invoice, id_product, price, quantity, item_discount)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (PreparedStatement ps = DbConnect.getPreparedStatement(sql, true)) {
            ps.setInt(1, invoiceId);
            ps.setInt(2, item.getId_product());
            ps.setDouble(3, item.getPrice());
            ps.setInt(4, item.getQuantity());
            ps.setDouble(5, item.getDiscount()); // đảm bảo đã có discount trong CartProduct
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi thêm invoice detail:");
            e.printStackTrace();
        }
    }
    public static List<CartProduct> getInvoiceDetails(int invoiceId) {
        List<CartProduct> details = new ArrayList<>();
        String sql = """
    SELECT p.product_name, d.price, d.quantity, d.item_discount
    FROM invoices_details d
    JOIN products p ON d.id_product = p.id_product
    WHERE d.id_invoice = ?
""";


        try (PreparedStatement ps = DbConnect.getPreparedStatement(sql, true)) {
            ps.setInt(1, invoiceId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CartProduct item = new CartProduct();
                item.setName(rs.getString("product_name")); // tên đúng theo cột
                item.setPrice(rs.getDouble("price"));
                item.setQuantity(rs.getInt("quantity"));
                item.setDiscount(rs.getDouble("item_discount"));
                details.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return details;
    }



    // Main test
    public static void main(String[] args) {
        int invoiceId = 24; // 👈 Thay bằng ID đơn hàng thực tế bạn muốn kiểm tra
        List<CartProduct> details = InvoiceDetailDao.getInvoiceDetails(invoiceId);

        if (details.isEmpty()) {
            System.out.println("❌ Không có sản phẩm nào trong chi tiết hóa đơn ID: " + invoiceId);
        } else {
            System.out.println("📦 Danh sách sản phẩm trong đơn hàng ID = " + invoiceId);
            for (CartProduct item : details) {
                System.out.println("---------------");
                System.out.println("🛒 Tên sản phẩm: " + item.getName());
                System.out.println("💵 Giá: " + item.getPrice());
                System.out.println("🔢 Số lượng: " + item.getQuantity());
                System.out.println("🔻 Giảm giá: " + item.getDiscount());
            }
        }
    }

}
