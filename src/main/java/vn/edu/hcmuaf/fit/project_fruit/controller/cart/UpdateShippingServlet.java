// File: UpdateShippingServlet.java
package vn.edu.hcmuaf.fit.project_fruit.controller.cart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.edu.hcmuaf.fit.project_fruit.dao.ShippingMethodDAO;
import vn.edu.hcmuaf.fit.project_fruit.dao.model.ShippingMethod;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "UpdateShippingServlet", value = "/update-shipping")
public class UpdateShippingServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        HttpSession session = request.getSession();

        String shippingId = request.getParameter("shipping_method_id");
        double shippingFee = 0;

        if (shippingId != null && !shippingId.isEmpty()) {
            session.setAttribute("shipping_method", shippingId);
            try {
                int id = Integer.parseInt(shippingId);
                ShippingMethodDAO dao = new ShippingMethodDAO();
                ShippingMethod method = dao.getShippingMethodById(id);
                if (method != null) {
                    shippingFee = method.getShippingFee();
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        // Tính tổng tiền mới
        double tempTotal = 0;
        double discount = 0;
        Object cartObj = session.getAttribute("cart");
        if (cartObj != null) {
            tempTotal = ((vn.edu.hcmuaf.fit.project_fruit.dao.cart.Cart) cartObj).getTotalPrice();
        }
        Object discountObj = session.getAttribute("discount");
        if (discountObj instanceof Number) {
            discount = ((Number) discountObj).doubleValue();
        }

        double finalTotal = tempTotal - discount + shippingFee;

        try (PrintWriter out = response.getWriter()) {
            out.print("{" +
                    "\"shippingFee\": " + shippingFee + "," +
                    "\"finalTotal\": " + finalTotal +
                    "}");
        }
    }
}
