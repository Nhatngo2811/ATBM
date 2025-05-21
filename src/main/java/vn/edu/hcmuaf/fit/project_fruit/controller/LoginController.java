package vn.edu.hcmuaf.fit.project_fruit.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.project_fruit.dao.model.Customer;
import vn.edu.hcmuaf.fit.project_fruit.dao.model.User;
import vn.edu.hcmuaf.fit.project_fruit.service.CustomerService;
import vn.edu.hcmuaf.fit.project_fruit.service.UserService;

import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginController extends HttpServlet {
    private final UserService userService = new UserService();
    private final CustomerService customerService = new CustomerService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("useremail");
        String password = request.getParameter("pass");

        User user = userService.validateUser(email, password);

        if (user != null) {
            // ✅ Kiểm tra nếu tài khoản chưa xác thực
            if (!user.isVerified()) {
                request.setAttribute("errorMessage", "Tài khoản chưa được xác thực qua email. Vui lòng kiểm tra hộp thư.");
                request.setAttribute("resendVerificationEmail", email);
                request.getRequestDispatcher("/user/login.jsp").forward(request, response);
                return;
            }

            // ✅ Nếu đã xác thực → cho đăng nhập
            loginUser(request, response, user);
        } else {
            request.setAttribute("errorMessage", "Email hoặc mật khẩu không chính xác.");
            request.getRequestDispatcher("/user/login.jsp").forward(request, response);
        }
    }

    private void loginUser(HttpServletRequest request, HttpServletResponse response, User user) throws IOException, ServletException {
        // Lấy thông tin khách hàng
        Customer customer = customerService.getCustomerById(user.getIdCustomer());

        if (customer != null) {
            // Lưu thông tin vào session
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("customer", customer);

            // Chuyển hướng theo vai trò
            if ("admin".equals(user.getRole()) || "staff".equals(user.getRole())) {
                response.sendRedirect(request.getContextPath() + "/admin");
            } else if ("user".equals(user.getRole())) {
                response.sendRedirect(request.getContextPath() + "/home");
            } else {
                response.sendRedirect(request.getContextPath() + "/unauthorized");
            }
        } else {
            request.setAttribute("errorMessage", "Không thể tìm thấy thông tin khách hàng.");
            request.getRequestDispatcher("/user/login.jsp").forward(request, response);
        }
    }
}