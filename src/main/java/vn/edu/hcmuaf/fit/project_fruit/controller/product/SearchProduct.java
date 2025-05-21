package vn.edu.hcmuaf.fit.project_fruit.controller.product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.project_fruit.dao.model.Product;
import vn.edu.hcmuaf.fit.project_fruit.service.ProductService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "SearchProduct", value = "/search")
public class SearchProduct extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        ProductService service = new ProductService();

        // Lấy danh sách sản phẩm theo từ khóa
        List<Product> products = service.searchProducts(keyword);
        System.out.println("Keyword: " + keyword);
        if (products.isEmpty()) {
            System.out.println("No products found for keyword: " + keyword);
        } else {
            System.out.println("Products found:");
            for (Product product : products) {
                System.out.println("Product ID: " + product.getId_product() +
                        ", Name: " + product.getName() +
                        ", Price: " + product.getPrice() +
                        ", Discounted Price: " + product.getDiscountedPrice());
            }
        }

        // Trả về JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print("[");
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            out.print("{");
            out.print("\"id_product\":" + product.getId_product() + ",");
            out.print("\"name\":\"" + product.getName() + "\",");
            out.print("\"price\":" + product.getPrice() + ",");
            out.print("\"discountedPrice\":" + product.getDiscountedPrice() + ",");
            out.print("\"imageUrl\":\"" + product.getImageUrl() + "\"");
            out.print("}");
            if (i < products.size() - 1) out.print(",");
        }
        out.print("]");
        out.flush();
    }
}


