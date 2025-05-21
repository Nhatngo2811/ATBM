package vn.edu.hcmuaf.fit.project_fruit.controller.product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.project_fruit.dao.model.Product;
import vn.edu.hcmuaf.fit.project_fruit.service.ProductService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@WebServlet(name = "ListProduct", value = "/home")
public class ListProduct extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String category = request.getParameter("category");
        String sortOption = request.getParameter("sort"); // Lấy danh mục từ URL
        int categoryId = 0;

        if (sortOption == null) {
            sortOption = "date"; // Mặc định sắp xếp theo sản phẩm nổi bật
        }

        // Xử lý id của danh mục dựa trên category từ URL
        if (category != null) {
            switch (category) {
                case "traicayhomnay":
                    categoryId = 1;
                    break;
                case "traicayvietnam":
                    categoryId = 2;
                    break;
                case "traicaynhapkhau":
                    categoryId = 3;
                    break;
                case "traicaycatsan":
                    categoryId = 4;
                    break;
                case "quatangtraicay":
                    categoryId = 5;
                    break;
                case "hopquanguyencat":
                    categoryId = 6;
                    break;
                case "traicaysaykho":
                    categoryId = 7;
                    break;
                case "muttraicay":
                    categoryId = 8;
                    break;
            }
        }

        ProductService service = new ProductService();
        List<Product> data1 = service.getProductsByIdRange(1, 7);
        List<Product> data2 = service.getProductsByIdRange(8, 15);
        List<Product> data3 = service.getProductsByIdRange(16, 25);
        List<Product> data4 = service.getProductsByIdRange(27, 36);
        List<Product> data5 = service.getProductsByIdRange(37, 46);
        List<Product> data6 = service.getProductsByIdRange(47, 56);
        List<Product> data7 = service.getProductsByIdRange(57, 66);
        List<Product> data8 = service.getProductsByIdRange(67, 76);
        List<Product> data9 = service.getProductsByIdRange(77, 86);
        List<Product> data10 = service.getProductsByIdRange(87, 96);

        List<Product> weeklyDiscountedProducts = service.getWeeklyDiscountedProducts();

        // Gửi danh sách sản phẩm theo dòng đến JSP
        request.setAttribute("data1", data1);
        request.setAttribute("data2", data2);
        request.setAttribute("data3", data3);
        request.setAttribute("data4", data4);
        request.setAttribute("data5", data5);
        request.setAttribute("data6", data6);
        request.setAttribute("data7", data7);
        request.setAttribute("data8", data8);
        request.setAttribute("data9", data9);
        request.setAttribute("data10", data10);
        request.setAttribute("weeklyDiscountedProducts", weeklyDiscountedProducts);



        // Thêm thông báo nếu danh sách rỗng
        if (data1.isEmpty() && data2.isEmpty() && data3.isEmpty() && data4.isEmpty() &&
                data5.isEmpty() && data6.isEmpty() && data7.isEmpty() && data8.isEmpty() &&
                data9.isEmpty() && data10.isEmpty() && weeklyDiscountedProducts.isEmpty()) {
            request.setAttribute("message", "Không tìm thấy sản phẩm nào.");
        }


        // Lấy sản phẩm theo categoryId (dành cho các danh mục riêng)
        List<Product> productsByCategory = service.getProductsByCategory(categoryId);

        if (sortOption.equals("price_asc")) {
            productsByCategory.sort(Comparator.comparingDouble(Product::getPrice));  // Giá tăng dần
        } else if (sortOption.equals("price_desc")) {
            productsByCategory.sort(Comparator.comparingDouble(Product::getPrice).reversed());  // Giá giảm dần
        } else if (sortOption.equals("date")) {
            // Nếu có sắp xếp theo ngày hoặc sản phẩm nổi bật, có thể sắp xếp dựa trên ngày
            // productsByCategory.sort(Comparator.comparing(Product::getCreateDate));  // Ví dụ sắp xếp theo ngày
        }
        // Phân chia danh sách sản phẩm theo nhóm (7 sản phẩm mỗi nhóm)
        List<List<Product>> productGroups = new ArrayList<>();
        int groupSize = 7;
        for (int i = 0; i < productsByCategory.size(); i += groupSize) {
            int end = Math.min(i + groupSize, productsByCategory.size());
            productGroups.add(productsByCategory.subList(i, end));
        }
//        // Gửi danh sách sản phẩm theo nhóm đến JSP
        request.setAttribute("productGroups", productGroups);

        // Thêm thông báo nếu không có sản phẩm
        if (productsByCategory.isEmpty()) {
            request.setAttribute("message", "Không tìm thấy sản phẩm nào.");
        }

        // Điều hướng đến trang chủ nếu không có category hoặc đến trang danh mục
        String destination = "/index.jsp"; // Trang chủ mặc định
        if (category != null && !category.isEmpty()) {
            // Điều hướng đến trang danh mục
            destination = "/product/" + category + ".jsp";
        }

        // Gửi request đến JSP
        request.getRequestDispatcher(destination).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response); // Chuyển POST thành GET
    }
}




