package vn.edu.hcmuaf.fit.project_fruit.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import vn.edu.hcmuaf.fit.project_fruit.dao.model.User;
import java.io.IOException;

@WebFilter("/admin/*")
public class AdminFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false); // không tạo mới nếu chưa có

        User user = (session != null) ? (User) session.getAttribute("user") : null;

        // Chưa đăng nhập hoặc không phải admin → chuyển về trang login
        if (user == null || !"admin".equals(user.getRole())) {
            res.sendRedirect(req.getContextPath() + "/user/login.jsp");
            return;
        }

        // ✅ Có quyền → cho đi tiếp
        chain.doFilter(request, response);
    }
}



