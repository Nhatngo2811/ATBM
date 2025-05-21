package vn.edu.hcmuaf.fit.project_fruit.controller.login;

import com.google.gson.JsonObject;
import vn.edu.hcmuaf.fit.project_fruit.dao.CustomerDao;
import vn.edu.hcmuaf.fit.project_fruit.dao.UserDao;
import vn.edu.hcmuaf.fit.project_fruit.dao.model.Customer;
import vn.edu.hcmuaf.fit.project_fruit.dao.model.User;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/google-login")
public class GoogleLoginServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String code = request.getParameter("code");

        if (code == null || code.isEmpty()) {
            response.sendRedirect("user/login.jsp?message=Thiếu mã xác thực từ Google");
            return;
        }

        try {
            String accessToken = GoogleLogin.getToken(code);
            JsonObject userInfo = GoogleLogin.getUserInfo(accessToken);

            String email = userInfo.get("email").getAsString();
            String googleId = userInfo.get("id").getAsString();
            String name = userInfo.get("name").getAsString();

            UserDao userDao = new UserDao();
            User user = userDao.getUserByEmail(email);

            if (user == null) {
                // Đăng ký mới
                user = new User();
                user.setEmail(email);
                user.setPassword(""); // Không dùng mật khẩu
                user.setRole("user");
                user.setGoogleId(googleId);

                boolean ok = userDao.registerUser(user, name);
                if (!ok) {
                    response.sendRedirect("user/login.jsp?message=Không thể đăng ký người dùng mới");
                    return;
                }
            } else if (user.getGoogleId() == null || user.getGoogleId().isEmpty()) {
                // Nếu chưa liên kết googleId
                userDao.linkGoogleAccount(email, googleId);
            }
            request.getSession().setAttribute("user", user);

            // ✅ Thêm đoạn này để lấy tên hiển thị
            CustomerDao customerDao = new CustomerDao();
            Customer customer = customerDao.getCustomerById(user.getIdCustomer());
            request.getSession().setAttribute("customer", customer);
            response.sendRedirect(request.getContextPath() + "/home?message=google-success");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("user/login.jsp?message=Lỗi khi đăng nhập bằng Google");
        }
    }
}
