package vn.edu.hcmuaf.fit.project_fruit.controller.cart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.project_fruit.dao.cart.Cart;

import java.io.IOException;

@WebServlet(name = "Show", value = "/show-cart")
public class Show extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String referer = request.getHeader("Referer");
        if (referer != null) {
            HttpSession session = request.getSession();
            session.setAttribute("previousPage", referer);  // Lưu URL trước đó
        }
        // Lấy giỏ hàng từ session
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        // Kiểm tra nếu giỏ hàng không có thì sẽ thông báo giỏ hàng trống
        if (cart == null || cart.getList().isEmpty()) {
            request.setAttribute("message", "Giỏ hàng của bạn đang trống!");
        }

        // Chuyển hướng đến trang giỏ hàng (Cart.jsp) để hiển thị giỏ hàng
        request.getRequestDispatcher("/card/card.jsp").forward(request, response);
    }

}
