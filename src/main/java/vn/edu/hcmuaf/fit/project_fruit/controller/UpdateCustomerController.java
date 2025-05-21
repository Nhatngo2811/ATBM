package vn.edu.hcmuaf.fit.project_fruit.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.project_fruit.dao.model.Customer;
import vn.edu.hcmuaf.fit.project_fruit.service.CustomerService;

import java.io.IOException;

@WebServlet(name = "UpdateCustomerController", value = "/update-customer-info")
public class UpdateCustomerController extends HttpServlet {
    private final CustomerService customerService = new CustomerService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy thông tin khách hàng từ session
        Customer currentCustomer = (Customer) request.getSession().getAttribute("customer");
        if (currentCustomer == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // Lấy thông tin từ form
        String customerName = request.getParameter("customerName");
        String customerPhone = request.getParameter("customerPhone");
        String address = request.getParameter("address");
        int customerId = currentCustomer.getIdCustomer();

        // Gọi service để cập nhật thông tin khách hàng
        boolean isUpdated = customerService.updateCustomerDetails(customerId, customerName, customerPhone, address);

        if (isUpdated) {
            // Lấy lại thông tin khách hàng từ cơ sở dữ liệu
            Customer updatedCustomer = customerService.getCustomerById(customerId);
            if (updatedCustomer != null) {
                // Cập nhật lại thông tin trong session
                request.getSession().setAttribute("customer", updatedCustomer);
                request.setAttribute("successMessage", "Cập nhật thông tin thành công!");
            } else {
                request.setAttribute("errorMessage", "Không thể lấy thông tin sau khi cập nhật.");
            }
        } else {
            request.setAttribute("errorMessage", "Cập nhật thông tin thất bại. Vui lòng thử lại.");
        }

        // Quay lại trang user.jsp
        request.getRequestDispatcher("/user/user.jsp").forward(request, response);
    }
}

