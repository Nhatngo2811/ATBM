package vn.edu.hcmuaf.fit.project_fruit.controller.cart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.project_fruit.dao.cart.Cart;
import vn.edu.hcmuaf.fit.project_fruit.dao.model.Product;
import vn.edu.hcmuaf.fit.project_fruit.service.ProductService;

import java.io.IOException;

@WebServlet(name = "Add", value = "/add-cart")
public class Add extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Lấy tham số "pid" từ request và chuyển thành số nguyên
            int productId = Integer.parseInt(request.getParameter("addToCartPid"));
            int quantity = 1; // Mặc định là 1
            String quantityParam = request.getParameter("quantity");
            if (quantityParam != null) {
                try {
                    quantity = Integer.parseInt(quantityParam);
                } catch (NumberFormatException e) {
                    quantity = 1; // Nếu có lỗi thì đặt lại là 1
                }
            }
            // Sử dụng service để lấy thông tin sản phẩm
            ProductService productService = new ProductService();
            Product product = productService.getDetails(productId);

            // Kiểm tra nếu sản phẩm không tồn tại, chuyển hướng về danh sách sản phẩm
            if (product == null) {
                response.sendRedirect("list-products?addCart=false");
                return;
            }

            // Lấy giỏ hàng từ session, hoặc tạo mới nếu không tồn tại
            HttpSession session = request.getSession();
            Cart cart = (Cart) session.getAttribute("cart");

            if (cart == null) {
                cart = new Cart();  // Tạo mới giỏ hàng nếu chưa có
            }

            // Thêm sản phẩm vào giỏ hàng
            cart.addProduct(product, quantity);

            // Cập nhật giỏ hàng vào session
            session.setAttribute("cart", cart);

            // Chuyển hướng đến trang danh sách sản phẩm với thông báo thêm thành công
            response.sendRedirect(request.getHeader("Referer"));

        } catch (NumberFormatException e) {
            // Nếu tham số "pid" không phải là một số hợp lệ, chuyển hướng với thông báo lỗi
            response.sendRedirect("list-products?addCart=false");
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Không sử dụng doPost trong trường hợp này
    }
}
