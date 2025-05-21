package vn.edu.hcmuaf.fit.project_fruit.dao;

import vn.edu.hcmuaf.fit.project_fruit.dao.db.DbConnect;
import vn.edu.hcmuaf.fit.project_fruit.dao.model.Feedback;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDao {

    // Lấy tất cả các phản hồi và in ra
    public List<Feedback> getAllFeedback() {
        // Sử dụng getPreparedStatement từ DbConnect để lấy PreparedStatement
        PreparedStatement ps = DbConnect.getPreparedStatement("SELECT f.id_feedback, f.id_account, f.content, f.date_create, f.rating, " +
                "c.customer_name AS cus_name, p.product_name " +
                "FROM feedbacks f " +
                "JOIN accounts a ON f.id_account = a.id_account " +
                "JOIN products p ON f.id_product = p.id_product " +
                "JOIN customers c ON a.id_customer = c.id_customer " +
                "ORDER BY f.id_feedback ASC", true);

        if (ps == null) return new ArrayList<>();  // Nếu không thể tạo PreparedStatement, trả về danh sách trống

        List<Feedback> feedbackList = new ArrayList<>();
        ResultSet rs = null;

        try {
            rs = ps.executeQuery();  // Thực thi câu truy vấn

            // Lặp qua tất cả các bản ghi phản hồi và thêm vào danh sách
            while (rs.next()) {
                // Lấy thông tin phản hồi và tên khách hàng
                Feedback feedback = new Feedback(
                        rs.getInt("id_feedback"),
                        rs.getString("product_name"),  // Lấy tên sản phẩm
                        rs.getString("cus_name"),  // Thay thế id_account bằng cus_name
                        rs.getString("content"),
                        rs.getString("date_create"),
                        rs.getDouble("rating")
                );
                feedbackList.add(feedback);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đảm bảo đóng ResultSet và PreparedStatement sau khi sử dụng
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return feedbackList;  // Trả về danh sách phản hồi
    }

    public void printAllFeedback() {
        // Giả sử getAllFeedback() là phương thức lấy tất cả phản hồi từ cơ sở dữ liệu
        List<Feedback> feedbacks = getAllFeedback();

        // Lặp qua danh sách phản hồi và in ra thông tin
        for (Feedback feedback : feedbacks) {
            System.out.println("Feedback ID: " + feedback.getIdFeedback());
            System.out.println("Product Name: " + feedback.getProductName());
            System.out.println("Customer Name: " + feedback.getCusName());
            System.out.println("Content: " + feedback.getContent());
            System.out.println("Date Created: " + feedback.getDateCreate());
            System.out.println("Rating: " + feedback.getRating());
            System.out.println("------------------------------");
        }
    }

    public List<Feedback> getFeedbacksByPage(int page, int recordsPerPage) {
        List<Feedback> feedbackList = new ArrayList<>();
        String query = "SELECT f.id_feedback, f.id_account, f.content, f.date_create, f.rating, " +
                "c.customer_name AS cus_name, p.product_name " +
                "FROM feedbacks f " +
                "JOIN accounts a ON f.id_account = a.id_account " +
                "JOIN products p ON f.id_product = p.id_product " +
                "JOIN customers c ON a.id_customer = c.id_customer " +
                "ORDER BY f.id_feedback ASC " +
                "LIMIT ?, ?";  // Phân trang ở đây

        try (PreparedStatement ps = DbConnect.getPreparedStatement(query, true)) {
            ps.setInt(1, (page - 1) * recordsPerPage);  // Tính offset
            ps.setInt(2, recordsPerPage);  // Giới hạn số bản ghi mỗi trang

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                feedbackList.add(new Feedback(
                        rs.getInt("id_feedback"),
                        rs.getString("product_name"),
                        rs.getString("cus_name"),
                        rs.getString("content"),
                        rs.getString("date_create"),
                        rs.getDouble("rating")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feedbackList;
    }

    // Phương thức lấy tổng số bản ghi để tính số trang
    public int getTotalRecords() {
        String query = "SELECT COUNT(*) FROM feedbacks";
        int totalRecords = 0;
        try (PreparedStatement ps = DbConnect.getPreparedStatement(query, true)) { // Sử dụng getPreparedStatement
            ResultSet rs = ps.executeQuery();  // Thực thi câu lệnh SQL
            if (rs.next()) {
                totalRecords = rs.getInt(1);  // Sửa lại từ rs.getInt(2) thành rs.getInt(1) vì COUNT(*) sẽ trả về kết quả ở cột đầu tiên
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalRecords;
    }

    public List<Feedback> getFeedbackByProductId(int idProduct) {
        List<Feedback> feedbackList = new ArrayList<>();
        String query = "SELECT f.id_feedback, f.id_account, f.content, f.date_create, f.rating, " +
                "c.customer_name AS cus_name, p.product_name " +
                "FROM feedbacks f " +
                "JOIN accounts a ON f.id_account = a.id_account " +
                "JOIN products p ON f.id_product = p.id_product " +
                "JOIN customers c ON a.id_customer = c.id_customer " +
                "WHERE p.id_product = ? " +
                "ORDER BY f.id_feedback ASC";

        try (PreparedStatement ps = DbConnect.getPreparedStatement(query, true)) {
            ps.setInt(1, idProduct); // Gán id sản phẩm vào câu truy vấn
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                feedbackList.add(new Feedback(
                        rs.getInt("id_feedback"),
                        rs.getString("product_name"),
                        rs.getString("cus_name"),
                        rs.getString("content"),
                        rs.getString("date_create"),
                        rs.getDouble("rating")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feedbackList;
    }

    public static boolean deleteFeedbackById(String feedbackId) throws SQLException {
        String query = "DELETE FROM feedbacks WHERE id_feedback = ?";
        try (PreparedStatement preparedStatement = DbConnect.getPreparedStatement(query, true)) {
            if (preparedStatement == null) {
                throw new SQLException("Không thể tạo PreparedStatement.");
            }
            preparedStatement.setString(1, feedbackId);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }
    }


    // Main để kiểm tra và in ra dữ liệu
    public static void main(String[] args) {
        FeedbackDao dao = new FeedbackDao();
        dao.printAllFeedback(); // Gọi phương thức để in ra tất cả phản hồi
    }
}
