package vn.edu.hcmuaf.fit.project_fruit.controller.cart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.project_fruit.dao.cart.Cart;
import vn.edu.hcmuaf.fit.project_fruit.dao.model.Promotions;
import vn.edu.hcmuaf.fit.project_fruit.service.PromotionService;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet(name = "ApplyVoucherServlet", value = "/apply-voucher")
public class ApplyVoucherServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null) {
            response.sendRedirect("checkout");
            return;
        }

        String voucherCode = request.getParameter("voucherCode");
        if (voucherCode == null || voucherCode.trim().isEmpty()) {
            session.setAttribute("discountError", "Vui lòng nhập mã giảm giá.");
            response.sendRedirect("checkout");
            return;
        }

        PromotionService promotionService = new PromotionService();
        Promotions promotion = promotionService.getPromotionByCode(voucherCode.trim());

        if (promotion == null) {
            session.setAttribute("discountError", "Mã giảm giá không tồn tại.");
            response.sendRedirect("checkout");
            return;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate startDate = LocalDate.parse(promotion.getStart_date(), formatter);
            LocalDate endDate = LocalDate.parse(promotion.getEnd_date(), formatter);
            LocalDate today = LocalDate.now();

            if (today.isBefore(startDate) || today.isAfter(endDate)) {
                session.setAttribute("discountError", "Mã giảm giá đã hết hạn hoặc chưa có hiệu lực.");
                response.sendRedirect("checkout");
                return;
            }

            double totalPrice = cart.getTotalPrice();
            double minOrderAmount = promotion.getMin_order_amount();

            if (totalPrice < minOrderAmount) {
                session.setAttribute("discountError", "Đơn hàng cần tối thiểu " + minOrderAmount + "₫ để áp dụng mã giảm giá.");
                response.sendRedirect("checkout");
                return;
            }

            double discount = (promotion.getPercent_discount() / 100.0) * totalPrice;
            double newTotalPrice = totalPrice - discount;

            session.setAttribute("discount", discount);
            session.setAttribute("newTotalPrice", newTotalPrice);
            session.setAttribute("appliedPromotion", promotion);
            session.setAttribute("discountSuccess", "Áp dụng mã giảm giá thành công!");

        } catch (Exception e) {
            session.setAttribute("discountError", "Lỗi khi xử lý mã giảm giá: " + e.getMessage());
        }
        String currentShipping = request.getParameter("shipping_method");
        if (currentShipping != null && !currentShipping.isEmpty()) {
            session.setAttribute("shipping_method", currentShipping);
        }

        // 👉 Redirect về lại servlet /checkout để hiển thị trang payment.jsp
        response.sendRedirect("checkout");
    }
    public static void main(String[] args) {
        PromotionService ps = new PromotionService();
        List<Promotions> list = ps.getAll();
        for (Promotions p : list) {
            System.out.println(p.getCode() + " - " + p.getPercent_discount());
        }
    }

}
