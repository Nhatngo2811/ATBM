package vn.edu.hcmuaf.fit.project_fruit.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.project_fruit.dao.FeedbackDao;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "DeleteFeedback", urlPatterns = {"/remove-feedback"})
public class DeleteFeedback extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String feedbackId = request.getParameter("fid");

        if (feedbackId == null || feedbackId.trim().isEmpty()) {
            response.sendRedirect("admin.jsp?status=invalid");
            return;
        }

        try {
            boolean isDeleted = FeedbackDao.deleteFeedbackById(feedbackId);
            if (isDeleted) {
                response.sendRedirect(request.getContextPath() + "/admin.jsp?status=success");
            } else {
                response.sendRedirect(request.getContextPath() + "/admin.jsp?status=error");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin.jsp?status=sqlerror");
        }
    }
}

