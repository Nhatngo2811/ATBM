package vn.edu.hcmuaf.fit.project_fruit.service;


import vn.edu.hcmuaf.fit.project_fruit.dao.PromotionsDao;
import vn.edu.hcmuaf.fit.project_fruit.dao.model.Promotions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
public class PromotionService {

    private PromotionsDao promotionsDao;
    // Constructor
    public PromotionService() {
        this.promotionsDao = new PromotionsDao();  // Khởi tạo đối tượng PromotionsDao
    }

    // Lấy tất cả khuyến mãi
    public List<Promotions> getAll() {
        return promotionsDao.getAll();  // Gọi phương thức getAll() từ PromotionsDao
    }

    // Lấy khuyến mãi theo ID
    public Promotions getPromotionById(int id) {
        return promotionsDao.getById(id);  // Gọi phương thức getById() từ PromotionsDao
    }
    // Cập nhật khuyến mãi
    public boolean updatePromotion(Promotions promotion) {
        // Thêm logic nghiệp vụ nếu cần
        return promotionsDao.updatePromotion(promotion);  // Gọi phương thức updatePromotion() từ PromotionsDao
    }
    // Lấy khuyến mãi theo phân trang
    public List<Promotions> getPromotionsByPage(int page, int recordsPerPage) {
        return promotionsDao.getPromotionsByPage(page, recordsPerPage);  // Gọi phương thức phân trang từ PromotionsDao
    }

    // Lấy tổng số bản ghi trong bảng promotions
    public int getTotalRecords() {
        return promotionsDao.getTotalRecords();  // Gọi phương thức lấy tổng số bản ghi từ PromotionsDao
    }
    public Promotions getPromotionByCode(String code) {
        return promotionsDao.getPromotionByCode(code);
    }
    public static void main(String[] args) {
        PromotionsDao promotionsDao = new PromotionsDao();
        List<Promotions> promotionsList = promotionsDao.getAll();

        if (promotionsList.isEmpty()) {
            System.out.println("Không có khuyến mãi nào trong hệ thống.");
        } else {
            System.out.println("🎁 Danh sách khuyến mãi hiện có:");
            for (Promotions promotion : promotionsList) {
                System.out.println("----------------------------------");
                System.out.println("🔖 ID: " + promotion.getId_promotion());
                System.out.println("📛 Tên: " + promotion.getPromotion_name());
                System.out.println("📝 Mô tả: " + promotion.getDescribe_1());
                System.out.println("📅 Từ ngày: " + promotion.getStart_date());
                System.out.println("📅 Đến ngày: " + promotion.getEnd_date());
                System.out.println("💸 Giảm giá: " + promotion.getPercent_discount() + "%");
                System.out.println("📂 Loại: " + promotion.getType());
                System.out.println("🏷️ Mã giảm giá: " + promotion.getCode());
                System.out.println("📦 Đơn hàng tối thiểu: " + promotion.getMin_order_amount() + " VND");
            }
        }
    }


}
