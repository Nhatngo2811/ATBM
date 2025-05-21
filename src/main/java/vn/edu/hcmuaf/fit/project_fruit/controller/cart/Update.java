package vn.edu.hcmuaf.fit.project_fruit.controller.cart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.project_fruit.dao.cart.Cart;
import vn.edu.hcmuaf.fit.project_fruit.dao.cart.CartProduct;

import java.io.IOException;

@WebServlet(name = "Update", value = "/update-cart")
public class Update extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Lấy giỏ hàng từ session
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            response.sendRedirect("show-cart"); // Quay lại giỏ hàng nếu giỏ hàng rỗng
            return;
        }

        try {
            int productId = Integer.parseInt(request.getParameter("pid"));
            int change = Integer.parseInt(request.getParameter("change"));

            CartProduct product = cart.getProductById(productId);

            if (product != null) {
                int newQuantity = product.getQuantity() + change;

                if (newQuantity > 1) {
                    cart.update(productId, newQuantity);
                } else if (newQuantity == 1 && change == -1) {
                    session.setAttribute("quantityError", "Sản phẩm phải có ít nhất 1 số lượng.");
                } else {
                    cart.remove(productId);
                }

                // 👉 Mỗi khi thay đổi giỏ hàng thì reset giảm giá (nếu có)
                session.removeAttribute("discount");
                session.removeAttribute("newTotalPrice");
                session.removeAttribute("appliedPromotion");
                session.removeAttribute("discountSuccess");
                session.removeAttribute("discountError");

                session.setAttribute("cart", cart); // Cập nhật lại giỏ hàng
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        response.sendRedirect("show-cart"); // Load lại trang giỏ hàng
    }
}

