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

@WebServlet(name = "SearchResult", value = "/search-result")
public class SearchResult extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("keyword");

        ProductService service = new ProductService();
        List<Product> products = service.searchProducts(keyword);

        // Debug: Kiểm tra danh sách sản phẩm
        System.out.println("Keyword: " + keyword);
        if (products.isEmpty()) {
            System.out.println("No products found for keyword: " + keyword);
        } else {
            for (Product product : products) {
                System.out.println("Product ID: " + product.getId_product());
                System.out.println("Name: " + product.getName());
                System.out.println("Price: " + product.getPrice());
                System.out.println("Discounted Price: " + product.getDiscountedPrice());
            }
        }

        request.setAttribute("products", products);
        request.setAttribute("keyword", keyword);

        request.getRequestDispatcher("/product/resultSearch.jsp").forward(request, response);
    }
}
