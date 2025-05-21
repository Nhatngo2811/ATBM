package vn.edu.hcmuaf.fit.project_fruit.controller.cart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.project_fruit.dao.cart.Cart;

import java.io.IOException;

@WebServlet(name = "Remove", value = "/remove-cart")
public class Remove extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy id sản phẩm từ tham số "pid"
        int productId = Integer.parseInt(request.getParameter("pid"));

        // Lấy giỏ hàng từ session
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        // Nếu giỏ hàng tồn tại, xóa sản phẩm
        if (cart != null) {
            cart.remove(productId); // Gọi phương thức remove trong Cart để xóa sản phẩm
            session.setAttribute("cart", cart); // Cập nhật lại giỏ hàng trong session
        }

        // Chuyển hướng người dùng đến trang giỏ hàng sau khi xóa sản phẩm
        response.sendRedirect("show-cart");
    }
}
