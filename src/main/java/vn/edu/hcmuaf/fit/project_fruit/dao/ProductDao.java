package vn.edu.hcmuaf.fit.project_fruit.dao;

import vn.edu.hcmuaf.fit.project_fruit.dao.db.DbConnect;
import vn.edu.hcmuaf.fit.project_fruit.dao.model.Product;
import vn.edu.hcmuaf.fit.project_fruit.dao.model.ProductImg;
import vn.edu.hcmuaf.fit.project_fruit.dao.model.ProductList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {

    // Lấy toàn bộ danh sách sản phẩm
    public List<Product> getAll() {
        try {
            ArrayList<Product> products = new ArrayList<>();
            String query = """
            SELECT p.*, pr.percent_discount
            FROM products p
            LEFT JOIN promotions pr ON p.id_promotion = pr.id_promotion;
            """;
            PreparedStatement ps = DbConnect.getPreparedStatement(query, true);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                List<ProductImg> listImg = getImagesByProductId(rs.getInt("id_product"));
                Product product = new Product(
                        rs.getInt("id_product"),
                        rs.getString("product_name"),
                        listImg,
                        rs.getDouble("price"),
                        rs.getString("rating"),
                        rs.getDouble("percent_discount")
                );
                product.calculateDiscountedPrice();
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    // Lấy sản phẩm theo danh mục
    public List<Product> getProductsByCategory(int categoryId) {
        try {
            ArrayList<Product> products = new ArrayList<>();
            String query = """
            SELECT p.*, pr.percent_discount 
            FROM products p 
            LEFT JOIN promotions pr ON p.id_promotion = pr.id_promotion 
            WHERE p.id_category = ?
            """;
            PreparedStatement ps = DbConnect.getPreparedStatement(query, true);
            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                List<ProductImg> listImg = getImagesByProductId(rs.getInt("id_product"));
                Product product = new Product(
                        rs.getInt("id_product"),
                        rs.getString("product_name"),
                        listImg,
                        rs.getDouble("price"),
                        rs.getString("rating"),
                        rs.getDouble("percent_discount")
                );
                product.calculateDiscountedPrice();
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    public List<Product> getProductsByIdRange(int startId, int endId) {
        try {
            ArrayList<Product> products = new ArrayList<>();
            String query = """
            SELECT p.*, pr.percent_discount
            FROM products p
            LEFT JOIN promotions pr ON p.id_promotion = pr.id_promotion
            WHERE p.id_product BETWEEN ? AND ?
            """;
            PreparedStatement ps = DbConnect.getPreparedStatement(query, true);
            ps.setInt(1, startId);
            ps.setInt(2, endId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                List<ProductImg> listImg = getImagesByProductId(rs.getInt("id_product"));
                Product product = new Product(
                        rs.getInt("id_product"),
                        rs.getString("product_name"),
                        listImg,
                        rs.getDouble("price"),
                        rs.getString("rating"),
                        rs.getDouble("percent_discount")
                );
                product.calculateDiscountedPrice();
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    // Lấy danh sách hình ảnh của sản phẩm từ bảng product_images
    private List<ProductImg> getImagesByProductId(int productId) {
        try {
            ArrayList<ProductImg> images = new ArrayList<>();
            String query = "SELECT * FROM product_images WHERE id_product = ?";
            PreparedStatement ps = DbConnect.getPreparedStatement(query, true);
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ProductImg img = new ProductImg(
                        rs.getInt("id_image"),
                        rs.getString("url"),
                        rs.getBoolean("main_image")
                );
                if (img != null) {
                    images.add(img);
                    System.out.println("Found image: " + img.getUrl() + " for product ID: " + productId);
                }
            }
            return images;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    public List<Product> getWeeklyDiscountedProducts() {
        try {
            ArrayList<Product> products = new ArrayList<>();
            String query = """
                    SELECT p.*, pr.percent_discount
                    FROM products p
                    LEFT JOIN promotions pr ON p.id_promotion = pr.id_promotion
                    WHERE pr.start_date <= NOW() AND pr.end_date >= NOW()\s
                    AND pr.type = 'weekly';
        """;
            PreparedStatement ps = DbConnect.getPreparedStatement(query, true);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // Lấy danh sách hình ảnh của sản phẩm
                List<ProductImg> listImg = getImagesByProductId(rs.getInt("id_product"));

                // Tạo đối tượng Product
                Product product = new Product(
                        rs.getInt("id_product"),
                        rs.getString("product_name"),
                        listImg,
                        rs.getDouble("price"),
                        rs.getString("rating"),
                        rs.getDouble("percent_discount")
                );
                product.calculateDiscountedPrice(); // Tính giá sau giảm giá
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    public List<Product> searchProductsByName(String keyword) {
        try {
            ArrayList<Product> products = new ArrayList<>();
            String query = """
        SELECT p.*, pr.percent_discount
        FROM products p
        LEFT JOIN promotions pr ON p.id_promotion = pr.id_promotion
        WHERE p.product_name LIKE ?;
        """;
            PreparedStatement ps = DbConnect.getPreparedStatement(query, true);
            ps.setString(1, "%" + keyword + "%"); // Tìm kiếm với từ khóa có chứa keyword
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                List<ProductImg> listImg = getImagesByProductId(rs.getInt("id_product"));
                Product product = new Product(
                        rs.getInt("id_product"),
                        rs.getString("product_name"),
                        listImg,
                        rs.getDouble("price"),
                        rs.getString("rating"),
                        rs.getDouble("percent_discount")
                );
                product.calculateDiscountedPrice();
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    public int getCategoryIdByProductId(int productId) {
        try {
            String query = "SELECT id_category FROM products WHERE id_product = ?";
            PreparedStatement ps = DbConnect.getPreparedStatement(query, true);
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_category");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Trả về -1 nếu không tìm thấy
    }
    // Lấy sản phẩm theo ID
    public Product getById(int id) {
        try {
            String query = """
            SELECT p.*, pr.promotion_name, pr.percent_discount
            FROM products p
            LEFT JOIN promotions pr ON p.id_promotion = pr.id_promotion
            WHERE p.id_product = ?
            """;
            PreparedStatement ps = DbConnect.getPreparedStatement(query, true);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                List<ProductImg> listImg = getImagesByProductId(rs.getInt("id_product"));
                System.out.println("Product found: " + rs.getString("product_name"));
                Product product = new Product(
                        rs.getInt("id_product"),
                        rs.getString("product_name"),
                        listImg,
                        rs.getDouble("price"),
                        rs.getString("rating"),
                        rs.getBoolean("status"),
                        rs.getString("describe_1"),
                        rs.getInt("quantity"),
                        rs.getString("origin"),
                        rs.getString("entry_date"),
                        rs.getString("shelf_life"),
                        rs.getString("warranty_period"),
                        rs.getString("characteristic"),
                        rs.getString("preserve_product"),
                        rs.getString("use_prodcut"),
                        rs.getString("benefit"),
                        rs.getString("promotion_name"),
                        rs.getDouble("percent_discount")
                );

                product.calculateDiscountedPrice();
                return product;
            } else {
                System.out.println("No product found in database for ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Lấy tất cả sản phẩm từ cơ sở dữ liệu
    public List<ProductList> getAllProducts() {
        List<ProductList> productList = new ArrayList<>();
        String query = """
                SELECT p.id_product, p.product_name, p.price, p.origin, p.status, c.name_category, p.describe_1, pi.url
                FROM products p
                JOIN category_products c ON p.id_category = c.id_category
                LEFT JOIN product_images pi ON p.id_product = pi.id_product
                ORDER BY p.id_product ASC
                """;

        try (PreparedStatement ps = DbConnect.getPreparedStatement(query, true)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ProductImg productImg = new ProductImg(0, rs.getString("url"), false);
                ProductList product = new ProductList(
                        rs.getInt("id_product"),
                        rs.getString("product_name"),
                        rs.getString("name_category"),
                        rs.getDouble("price"),
                        rs.getString("origin"),
                        rs.getBoolean("status"),
                        rs.getString("describe_1"),
                        productImg.getUrl()
                );
                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productList;
    }

   // Phương thức phân trang
    public List<ProductList> getProductsByPage(int page, int recordsPerPage) {
        List<ProductList> productList = new ArrayList<>();
        String query = """
                SELECT p.id_product, p.product_name, p.price, p.origin, p.status, c.name_category, p.describe_1, pi.url
                FROM products p
                JOIN category_products c ON p.id_category = c.id_category
                LEFT JOIN product_images pi ON p.id_product = pi.id_product
                ORDER BY p.id_product ASC
                LIMIT ?, ?
                """;

        try (PreparedStatement ps = DbConnect.getPreparedStatement(query, true)) {
            ps.setInt(1, (page - 1) * recordsPerPage);  // Tính offset
            ps.setInt(2, recordsPerPage);  // Giới hạn số bản ghi mỗi trang
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ProductImg productImg = new ProductImg(0, rs.getString("url"), false);
                ProductList product = new ProductList(
                        rs.getInt("id_product"),
                        rs.getString("product_name"),
                        rs.getString("name_category"),
                        rs.getDouble("price"),
                        rs.getString("origin"),
                        rs.getBoolean("status"),
                        rs.getString("describe_1"),
                        productImg.getUrl()
                );
                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productList;
    }

    // Lấy tổng số sản phẩm để tính số trang
    public int getTotalRecords() {
        String query = "SELECT COUNT(*) FROM products";
        int totalRecords = 0;
        try (PreparedStatement ps = DbConnect.getPreparedStatement(query, true)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                totalRecords = rs.getInt(1);  // Lấy giá trị COUNT(*) từ kết quả
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalRecords;
    }
    public List<Product> getBestSellingProducts() {
        List<Product> productList = new ArrayList<>();
        String query = "SELECT p.product_name, SUM(id.quantity) AS total_quantity, SUM(id.quantity * p.price) AS total_amount " +
                "FROM invoices_details id " +
                "JOIN products p ON id.id_product = p.id_product " +
                "JOIN invoices i ON id.id_invoice = i.id_invoice " +
                "WHERE i.status = 'Đã hoàn thành' " +
                "GROUP BY p.product_name " +
                "ORDER BY total_quantity DESC " +
                "LIMIT 10";  // Lấy 10 sản phẩm bán chạy nhất

        try (PreparedStatement ps = DbConnect.getPreparedStatement(query, true);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String productName = rs.getString("product_name");
                int totalQuantity = rs.getInt("total_quantity");
                double totalAmount = rs.getDouble("total_amount");

                Product product = new Product(productName, totalQuantity, totalAmount);
                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productList;
    }

    public static boolean deleteProductById(String productId) throws SQLException {
        String query = "DELETE FROM products WHERE id_product = ?";

        // Sử dụng DbConnect để lấy kết nối
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, productId);
            int rowsAffected = ps.executeUpdate();

            return rowsAffected > 0; // Trả về true nếu xóa thành công, ngược lại false
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Ném lại ngoại lệ
        }
    }
    public List<Product> getRelatedProducts(int categoryId, int excludeProductId) {
        List<Product> relatedProducts = new ArrayList<>();
        String query = """
            SELECT p.*, pr.percent_discount
            FROM products p
            LEFT JOIN promotions pr ON p.id_promotion = pr.id_promotion
            WHERE p.id_category = ? AND p.id_product != ?
            LIMIT 10
            """;

        try (Connection conn = DbConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, categoryId);
            ps.setInt(2, excludeProductId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                List<ProductImg> listImg = getImagesByProductId(rs.getInt("id_product"));
                Product product = new Product(
                        rs.getInt("id_product"),
                        rs.getString("product_name"),
                        listImg,
                        rs.getDouble("price"),
                        rs.getString("rating"),
                        rs.getDouble("percent_discount")
                );
                product.calculateDiscountedPrice();
                relatedProducts.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return relatedProducts;
    }

// ========================== MAIN TEST ==========================

    public static void main(String[] args) {
        ProductDao productDao = new ProductDao();

        // Kiểm tra phương thức lấy sản phẩm liên quan
        int sampleCategoryId = 1; // Giả sử danh mục 1
        int sampleProductId = 5;  // Giả sử sản phẩm có ID 5

        List<Product> relatedProducts = productDao.getRelatedProducts(sampleCategoryId, sampleProductId);
        System.out.println("Related Products:");

        for (Product product : relatedProducts) {
            System.out.println("Product ID: " + product.getId_product());
            System.out.println("Product Name: " + product.getName());
            System.out.println("Price: " + product.getPrice());
            System.out.println("Discounted Price: " + product.getDiscountedPrice());
            System.out.println("Rating: " + product.getRating());
            System.out.println("Images: " + product.getListimg().size() + " images available");
            System.out.println("--------------------------------------------");
        }
    }

}
