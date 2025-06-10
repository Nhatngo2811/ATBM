package vn.edu.hcmuaf.fit.project_fruit.controller.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.project_fruit.dao.InvoiceDao;
import vn.edu.hcmuaf.fit.project_fruit.dao.InvoiceDetailDao;
import vn.edu.hcmuaf.fit.project_fruit.dao.cart.CartProduct;
import vn.edu.hcmuaf.fit.project_fruit.dao.model.Invoice;

import java.io.IOException;
import java.util.List;

@WebServlet("/user/view-invoice")
public class ViewInvoiceServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy ID hóa đơn từ parameter
        String invoiceId = request.getParameter("id");
        if (invoiceId == null || invoiceId.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/user/orders.jsp");
            return;
        }

        try {
            int id = Integer.parseInt(invoiceId);
            
            // Lấy thông tin hóa đơn
            Invoice invoice = InvoiceDao.getInvoiceById(id);
            if (invoice == null) {
                response.sendRedirect(request.getContextPath() + "/user/orders.jsp");
                return;
            }

            // Lấy chi tiết hóa đơn
            List<CartProduct> invoiceDetails = InvoiceDetailDao.getInvoiceDetails(id);

            // Đặt dữ liệu vào request
            request.setAttribute("invoice", invoice);
            request.setAttribute("invoiceDetails", invoiceDetails);

            // Chuyển hướng đến trang hiển thị hóa đơn
            request.getRequestDispatcher("/user/invoice.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/user/orders.jsp");
        }
    }
} 