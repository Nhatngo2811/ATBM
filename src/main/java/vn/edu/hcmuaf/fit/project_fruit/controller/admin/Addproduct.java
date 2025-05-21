package vn.edu.hcmuaf.fit.project_fruit.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.project_fruit.dao.db.DbConnect;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet(name = "AddProduct", urlPatterns = "/addproduct")
public class Addproduct extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy dữ liệu từ form
        String productName = request.getParameter("product-name");
        String productType = request.getParameter("product-type");
        String origin = request.getParameter("origin");
        String productPrice = request.getParameter("product-price");
        String quantity = request.getParameter("quantity");
        String promotionCode = request.getParameter("promotion-code");
        String supplierId = request.getParameter("supplier-add");
        String warrantyPeriod = request.getParameter("warranty-period");
        String shelfLife = request.getParameter("shelf-life");
        String describe = request.getParameter("describe");
        String rating = request.getParameter("rating");
        String characteristic = request.getParameter("characteristic");
        String preserveProduct = request.getParameter("preserve-product");
        String useProduct = request.getParameter("use-product");
        String benefit = request.getParameter("benefit");
        String imageLink = request.getParameter("image-link"); // Link ảnh từ form

        // Lấy thời gian hiện tại làm ngày nhập
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String entryDate = now.format(formatter);

        // Câu lệnh SQL chèn dữ liệu sản phẩm
        String productInsertQuery = "INSERT INTO products (id_category, id_supplier, id_promotion, product_name, price, origin, quantity, warranty_period, entry_date, shelf_life, describe_1, rating, characteristic, preserve_product, use_prodcut, benefit, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // Câu lệnh SQL chèn dữ liệu hình ảnh
        String imageInsertQuery = "INSERT INTO product_images (id_product, url, main_image) VALUES (?, ?, ?)";

        try (Connection conn = DbConnect.getConnection()) {
            conn.setAutoCommit(false); // Bắt đầu transaction

            try (PreparedStatement productStmt = conn.prepareStatement(productInsertQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
                // Set giá trị cho câu truy vấn sản phẩm
                productStmt.setString(1, productType);
                productStmt.setString(2, supplierId);
                productStmt.setString(3, promotionCode);
                productStmt.setString(4, productName);
                productStmt.setDouble(5, Double.parseDouble(productPrice));
                productStmt.setString(6, origin);
                productStmt.setInt(7, Integer.parseInt(quantity));
                productStmt.setString(8, warrantyPeriod);
                productStmt.setString(9, entryDate); // Sử dụng thời gian hiện tại
                productStmt.setString(10, shelfLife);
                productStmt.setString(11, describe);
                productStmt.setString(12, rating);
                productStmt.setString(13, characteristic);
                productStmt.setString(14, preserveProduct);
                productStmt.setString(15, useProduct);
                productStmt.setString(16, benefit);
                productStmt.setInt(17, 1); // Set giá trị status = 1

                // Thực thi truy vấn thêm sản phẩm
                int rowsInserted = productStmt.executeUpdate();
                if (rowsInserted > 0) {
                    // Lấy ID sản phẩm vừa thêm
                    ResultSet generatedKeys = productStmt.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int productId = generatedKeys.getInt(1);

                        // Thêm ảnh vào bảng product_images
                        try (PreparedStatement imageStmt = conn.prepareStatement(imageInsertQuery)) {
                            imageStmt.setInt(1, productId); // ID sản phẩm
                            imageStmt.setString(2, imageLink); // URL ảnh
                            imageStmt.setBoolean(3, true); // Ảnh chính
                            imageStmt.executeUpdate();
                        }
                    }
                }

                conn.commit(); // Xác nhận transaction
                response.sendRedirect(request.getContextPath() + "/admin");
            } catch (SQLException e) {
                conn.rollback(); // Hoàn tác nếu có lỗi
                e.printStackTrace();
                response.getWriter().write("Lỗi: " + e.getMessage());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("Lỗi: " + e.getMessage());
        }
    }
}
