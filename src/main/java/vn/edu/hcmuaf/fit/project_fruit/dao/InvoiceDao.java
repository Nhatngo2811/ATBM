package vn.edu.hcmuaf.fit.project_fruit.dao;

import vn.edu.hcmuaf.fit.project_fruit.dao.db.DbConnect;
import vn.edu.hcmuaf.fit.project_fruit.dao.model.Invoice;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDao {
    public int addInvoice(Invoice invoice) {
        String sql = """
            INSERT INTO invoices (id_account, receiver_name, phone, email, address_full,
                                  payment_method, shipping_method, total_price, shipping_fee, status, create_date)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())
        """;
        int generatedId = -1;

        try (PreparedStatement ps = DbConnect.getPreparedStatement(sql, true)) {
            ps.setInt(1, invoice.getAccountId());
            ps.setString(2, invoice.getReceiverName());
            ps.setString(3, invoice.getPhone());
            ps.setString(4, invoice.getEmail());
            ps.setString(5, invoice.getAddressFull());
            ps.setString(6, invoice.getPaymentMethod());
            ps.setString(7, invoice.getShippingMethod());
            ps.setDouble(8, invoice.getTotalPrice());
            ps.setDouble(9, invoice.getShippingFee());
            ps.setString(10, invoice.getStatus());

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedId = rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi thêm invoice:");
            e.printStackTrace();
        }

        return generatedId;
    }
    public List<Invoice> getAllInvoices() {
        List<Invoice> invoices = new ArrayList<>();

        String sql = """
            SELECT 
                i.id_invoice AS id,
                c.customer_name AS account_name,
                i.receiver_name,
                i.phone,
                i.email,
                i.payment_method,
                i.status,
                i.address_full,
                i.total_price,
                i.shipping_fee,
                i.create_date AS created_at
            FROM invoices i
            JOIN accounts a ON i.id_account = a.id_account
            JOIN customers c ON a.id_customer = c.id_customer
            ORDER BY i.create_date DESC
        """;

        try (PreparedStatement ps = DbConnect.getPreparedStatement(sql, true)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Invoice invoice = new Invoice();
                invoice.setIdInvoice(rs.getInt("id"));
                invoice.setAccountName(rs.getString("account_name")); // tên người mua từ bảng customers
                invoice.setReceiverName(rs.getString("receiver_name"));
                invoice.setPhone(rs.getString("phone"));
                invoice.setEmail(rs.getString("email"));
                invoice.setPaymentMethod(rs.getString("payment_method"));
                invoice.setStatus(rs.getString("status"));
                invoice.setShippingFee(rs.getDouble("shipping_fee"));
                invoice.setAddressFull(rs.getString("address_full"));
                invoice.setTotalPrice(rs.getDouble("total_price"));
                invoice.setCreateDate(rs.getTimestamp("created_at"));
                invoices.add(invoice);
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi truy vấn danh sách invoices:");
            e.printStackTrace();
        }

        return invoices;
    }
    public static Invoice getInvoiceById(int id) {
        String sql = """
        SELECT 
            i.id_invoice, c.customer_name, i.receiver_name, i.phone, i.email,
            i.address_full, i.total_price,i.shipping_fee, i.payment_method, i.status, i.create_date
        FROM invoices i
        JOIN accounts a ON i.id_account = a.id_account
        JOIN customers c ON a.id_customer = c.id_customer
        WHERE i.id_invoice = ?
    """;

        try (PreparedStatement ps = DbConnect.getPreparedStatement(sql, true)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Invoice invoice = new Invoice();
                invoice.setIdInvoice(id);
                invoice.setAccountName(rs.getString("customer_name")); // tài khoản
                invoice.setReceiverName(rs.getString("receiver_name")); // người nhận
                invoice.setPhone(rs.getString("phone"));
                invoice.setEmail(rs.getString("email"));
                invoice.setAddressFull(rs.getString("address_full"));
                invoice.setTotalPrice(rs.getDouble("total_price"));
                invoice.setShippingFee(rs.getDouble("shipping_fee"));
                invoice.setPaymentMethod(rs.getString("payment_method"));
                invoice.setStatus(rs.getString("status"));
                invoice.setCreateDate(rs.getTimestamp("create_date"));
                return invoice;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Invoice> getInvoicesByUserId(int userId) {
        List<Invoice> invoices = new ArrayList<>();
        String sql = """
            SELECT 
                i.id_invoice,
                c.customer_name,
                i.receiver_name,
                i.phone,
                i.email,
                i.address_full,
                i.total_price,
                i.shipping_fee,
                i.payment_method,
                i.status,
                i.create_date
            FROM invoices i
            JOIN accounts a ON i.id_account = a.id_account
            JOIN customers c ON a.id_customer = c.id_customer
            WHERE i.id_account = ?
            ORDER BY i.create_date DESC
        """;

        try (PreparedStatement ps = DbConnect.getPreparedStatement(sql, true)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Invoice invoice = new Invoice();
                invoice.setIdInvoice(rs.getInt("id_invoice"));
                invoice.setAccountName(rs.getString("customer_name"));
                invoice.setReceiverName(rs.getString("receiver_name"));
                invoice.setPhone(rs.getString("phone"));
                invoice.setEmail(rs.getString("email"));
                invoice.setAddressFull(rs.getString("address_full"));
                invoice.setTotalPrice(rs.getDouble("total_price"));
                invoice.setShippingFee(rs.getDouble("shipping_fee"));
                invoice.setPaymentMethod(rs.getString("payment_method"));
                invoice.setStatus(rs.getString("status"));
                invoice.setCreateDate(rs.getTimestamp("create_date"));
                invoices.add(invoice);
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi truy vấn danh sách hóa đơn của user:");
            e.printStackTrace();
        }

        return invoices;
    }


    public static String invoiceToText(Invoice invoice) {
        if (invoice == null) return "";
        return invoice.getIdInvoice()
                + "|" + invoice.getAccountName()
                + "|" + invoice.getReceiverName()
                + "|" + invoice.getPhone()
                + "|" + invoice.getEmail()
                + "|" + invoice.getAddressFull()
                + "|" + invoice.getPaymentMethod()
                + "|" + invoice.getShippingFee()
                + "|" + invoice.getTotalPrice();
    }

    public static void main(String[] args) {
        Invoice invoice = getInvoiceById(56);
        String text = invoiceToText(invoice);
        System.out.println(text);

    }
}
