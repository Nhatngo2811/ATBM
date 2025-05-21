package vn.edu.hcmuaf.fit.project_fruit.controller.admin;

import vn.edu.hcmuaf.fit.project_fruit.dao.PromotionsDao;
import vn.edu.hcmuaf.fit.project_fruit.dao.model.Promotions;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/EditPromotionServlet")
public class EditPromotion extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        try {
            int id = Integer.parseInt(request.getParameter("id_promotion"));
            String name = request.getParameter("promotion_name");
            String desc = request.getParameter("describe_1");
            String start = request.getParameter("start_date");
            String end = request.getParameter("end_date");
            String discountStr = request.getParameter("percent_discount");
            String type = request.getParameter("type");
            String code = request.getParameter("code");
            String minOrderStr = request.getParameter("min_order_amount");
            String maxUsageStr = request.getParameter("max_usage");
            String usageCountStr = request.getParameter("usage_count");

            double discount = discountStr != null && !discountStr.trim().isEmpty()
                    ? Double.parseDouble(discountStr.trim()) : 0.0;
            double minOrderAmount = minOrderStr != null && !minOrderStr.trim().isEmpty()
                    ? Double.parseDouble(minOrderStr.trim()) : 0.0;
            int maxUsage = maxUsageStr != null && !maxUsageStr.trim().isEmpty()
                    ? Integer.parseInt(maxUsageStr.trim()) : 0;
            int usageCount = usageCountStr != null && !usageCountStr.trim().isEmpty()
                    ? Integer.parseInt(usageCountStr.trim()) : 0;

            Promotions updatedPromo = new Promotions(id, name, desc, start, end, discount, type,
                    code, minOrderAmount, maxUsage, usageCount);

            PromotionsDao dao = new PromotionsDao();
            boolean success = dao.updatePromotion(updatedPromo);

            if (success) {
                response.sendRedirect("admin?tab=promotions&update=success");
            } else {
                response.sendRedirect("admin?tab=promotions&update=fail");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("admin?tab=promotions&update=fail");
        }
    }

}
