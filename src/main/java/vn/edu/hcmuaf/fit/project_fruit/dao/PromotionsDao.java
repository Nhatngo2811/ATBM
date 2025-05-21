package vn.edu.hcmuaf.fit.project_fruit.dao;

import vn.edu.hcmuaf.fit.project_fruit.dao.db.DbConnect;
import vn.edu.hcmuaf.fit.project_fruit.dao.model.Promotions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PromotionsDao {
    public List<Promotions> getAll() {
        String sql = "SELECT id_promotion, promotion_name, describe_1, start_date, end_date, percent_discount, type, code, min_order_amount " +
                "FROM promotions ORDER BY id_promotion ASC";
        List<Promotions> promotionsList = new ArrayList<>();

        try (PreparedStatement ps = DbConnect.getPreparedStatement(sql, true);
             ResultSet rs = ps != null ? ps.executeQuery() : null) {

            if (rs != null) {
                while (rs.next()) {
                    Promotions promotion = new Promotions(
                            rs.getInt("id_promotion"),
                            rs.getString("promotion_name"),
                            rs.getString("describe_1"),
                            rs.getString("start_date"),
                            rs.getString("end_date"),
                            rs.getDouble("percent_discount"),
                            rs.getString("type")
                    );
                    promotion.setCode(rs.getString("code"));
                    promotion.setMin_order_amount(rs.getDouble("min_order_amount"));

                    promotionsList.add(promotion);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return promotionsList;
    }



    // Method to retrieve a promotion by ID
    public Promotions getById(int id) {
        String query = "SELECT id_promotion, promotion_name, describe_1, start_date, end_date, percent_discount, type FROM promotions WHERE id_promotion = ?";
        try (PreparedStatement ps = DbConnect.getPreparedStatement(query, true)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Promotions(
                        rs.getInt("id_promotion"),
                        rs.getString("promotion_name"),
                        rs.getString("describe_1"),
                        rs.getString("start_date"),
                        rs.getString("end_date"),
                        rs.getDouble("percent_discount"),
                        rs.getString("type")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean deletePromotionById(String promotionId) throws SQLException {
        String query = "DELETE FROM promotions WHERE id_promotion = ?";
        try (PreparedStatement preparedStatement = DbConnect.getPreparedStatement(query, true)) {
            if (preparedStatement == null) {
                throw new SQLException("Không thể tạo PreparedStatement.");
            }
            preparedStatement.setString(1, promotionId);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public boolean updatePromotion(Promotions promotion) {
        String query = "UPDATE promotions SET promotion_name = ?, describe_1 = ?, start_date = ?, end_date = ?, percent_discount = ?, type = ? WHERE id_promotion = ?";
        try (PreparedStatement ps = DbConnect.getPreparedStatement(query, true)) {
            ps.setString(1, promotion.getPromotion_name());  // Lấy tên khuyến mãi
            ps.setString(2, promotion.getDescribe_1());      // Mô tả khuyến mãi
            ps.setString(3, promotion.getStart_date());      // Ngày bắt đầu
            ps.setString(4, promotion.getEnd_date());        // Ngày kết thúc
            ps.setDouble(5, promotion.getPercent_discount()); // Phần trăm giảm giá
            ps.setString(6, promotion.getType());            // Loại khuyến mãi
            ps.setInt(7, promotion.getId_promotion());       // ID khuyến mãi

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Promotions> getPromotionsByPage(int page, int recordsPerPage) {
        List<Promotions> promotionsList = new ArrayList<>();
        String query = "SELECT id_promotion, promotion_name, describe_1, start_date, end_date, percent_discount, type " +
                "FROM promotions " +
                "ORDER BY id_promotion ASC " +
                "LIMIT ?, ?";  // Phân trang ở đây

        try (PreparedStatement ps = DbConnect.getPreparedStatement(query, true)) {
            ps.setInt(1, (page - 1) * recordsPerPage);  // Tính offset
            ps.setInt(2, recordsPerPage);  // Giới hạn số bản ghi mỗi trang

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                promotionsList.add(new Promotions(
                        rs.getInt("id_promotion"),
                        rs.getString("promotion_name"),
                        rs.getString("describe_1"),
                        rs.getString("start_date"),
                        rs.getString("end_date"),
                        rs.getDouble("percent_discount"),
                        rs.getString("type")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return promotionsList;
    }

    // Phương thức lấy tổng số bản ghi để tính số trang
    public int getTotalRecords() {
        String query = "SELECT COUNT(*) FROM promotions";
        int totalRecords = 0;
        try (PreparedStatement ps = DbConnect.getPreparedStatement(query, true)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                totalRecords = rs.getInt(1);  // Trả về số bản ghi
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalRecords;
    }
    // Lấy khuyến mãi theo mã giảm giá
    public Promotions getPromotionByCode(String code) {
        String query = "SELECT id_promotion, code, promotion_name, describe_1, start_date, end_date, percent_discount, type, min_order_amount FROM promotions WHERE code = ?";
        try (PreparedStatement ps = DbConnect.getPreparedStatement(query, true)) {
            ps.setString(1, code);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Promotions promotion = new Promotions(
                        rs.getInt("id_promotion"),
                        rs.getString("promotion_name"),
                        rs.getString("describe_1"),
                        rs.getString("start_date"),
                        rs.getString("end_date"),
                        rs.getDouble("percent_discount"),
                        rs.getString("type")
                );
                promotion.setCode(rs.getString("code"));
                promotion.setMin_order_amount(rs.getDouble("min_order_amount"));
                return promotion;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void main(String[] args) {
        PromotionsDao promotionsDao = new PromotionsDao();
        List<Promotions> promotionsList = promotionsDao.getAll();

        if (promotionsList.isEmpty()) {
            System.out.println("Không có khuyến mãi nào trong hệ thống.");
        } else {
            System.out.println("Danh sách khuyến mãi:");
            for (Promotions promotion : promotionsList) {
                System.out.println("----------------------------------");
                System.out.println("ID: " + promotion.getId_promotion());
                System.out.println("Tên: " + promotion.getPromotion_name());
                System.out.println("Mô tả: " + promotion.getDescribe_1());
                System.out.println("Từ ngày: " + promotion.getStart_date());
                System.out.println("Đến ngày: " + promotion.getEnd_date());
                System.out.println("Giảm: " + promotion.getPercent_discount() + "%");
                System.out.println("Loại: " + promotion.getType());
                System.out.println("Mã giảm giá: " + promotion.getCode());
                System.out.println("Đơn tối thiểu: " + promotion.getMin_order_amount());
            }
        }
    }
}