package vn.edu.hcmuaf.fit.project_fruit.controller.login;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.http.client.fluent.Request;
import vn.edu.hcmuaf.fit.project_fruit.dao.CustomerDao;
import vn.edu.hcmuaf.fit.project_fruit.dao.UserDao;
import vn.edu.hcmuaf.fit.project_fruit.dao.model.Customer;
import vn.edu.hcmuaf.fit.project_fruit.dao.model.User;
import vn.edu.hcmuaf.fit.project_fruit.iconstant.IconstantFace;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/login-facebook")
public class LoginFacebookServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");

        if (code == null || code.isEmpty()) {
            resp.sendRedirect("user/login.jsp?message=Không có mã xác thực Facebook");
            return;
        }

        try {
            // B1: Lấy access_token từ code
            String tokenUrl = IconstantFace.FACEBOOK_LINK_GET_TOKEN +
                    "?client_id=" + IconstantFace.FACEBOOK_CLIENT_ID +
                    "&redirect_uri=" + IconstantFace.FACEBOOK_REDIRECT_URI +
                    "&client_secret=" + IconstantFace.FACEBOOK_CLIENT_SECRET +
                    "&code=" + code;

            String tokenResponse = Request.Get(tokenUrl).execute().returnContent().asString();
            JsonObject tokenObj = new Gson().fromJson(tokenResponse, JsonObject.class);
            String accessToken = tokenObj.get("access_token").getAsString();

            // B2: Lấy thông tin người dùng
            String infoUrl = IconstantFace.FACEBOOK_LINK_GET_USER_INFO + accessToken;
            String infoResponse = Request.Get(infoUrl).execute().returnContent().asString();
            JsonObject userInfo = new Gson().fromJson(infoResponse, JsonObject.class);

            String facebookId = userInfo.get("id").getAsString();
            String name = userInfo.get("name").getAsString();
            String email = userInfo.has("email") ? userInfo.get("email").getAsString()
                    : facebookId + "@facebook.local";

            String avatarUrl = userInfo.getAsJsonObject("picture")
                    .getAsJsonObject("data").get("url").getAsString();

            // B3: Kiểm tra user
            UserDao userDao = new UserDao();
            User user = userDao.getUserByEmail(email);

            if (user == null) {
                // Nếu chưa có, tạo mới user
                user = new User();
                user.setEmail(email);
                user.setPassword(""); // không có mật khẩu
                user.setRole("user");


                userDao.registerUser(user, name);
                user = userDao.getUserByEmail(email);
            }

            // B4: Lưu session
            req.getSession().setAttribute("user", user);
            CustomerDao customerDao = new CustomerDao();
            Customer customer = customerDao.getCustomerById(user.getIdCustomer());
            req.getSession().setAttribute("customer", customer);

            resp.sendRedirect(req.getContextPath() + "/home");

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("user/login.jsp?message=Lỗi đăng nhập Facebook");
        }
    }
}
