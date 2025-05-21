package vn.edu.hcmuaf.fit.project_fruit.controller.product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.project_fruit.dao.model.Product;
import vn.edu.hcmuaf.fit.project_fruit.service.ProductService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "RelatedProductsServlet", value = "/relatedProducts")
public class RelatedProductsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy tham số từ request
        String categoryIdParam = request.getParameter("categoryId");
        String excludeProductIdParam = request.getParameter("excludeProductId");

        int categoryId = 0;
        int excludeProductId = 0;

        // Chuyển đổi tham số sang kiểu số nguyên
        try {
            if (categoryIdParam != null) {
                categoryId = Integer.parseInt(categoryIdParam);
            }
            if (excludeProductIdParam != null) {
                excludeProductId = Integer.parseInt(excludeProductIdParam);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        // Gọi Service để lấy sản phẩm liên quan
        ProductService productService = new ProductService();
        List<Product> relatedProducts = productService.getRelatedProducts(categoryId, excludeProductId);

        // Đưa danh sách sản phẩm vào request
        request.setAttribute("relatedProducts", relatedProducts);

        // Điều hướng đến JSP hiển thị sản phẩm liên quan
        request.getRequestDispatcher("/product/related-products.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
