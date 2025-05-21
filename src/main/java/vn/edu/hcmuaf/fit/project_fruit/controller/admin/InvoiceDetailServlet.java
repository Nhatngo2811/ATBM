package vn.edu.hcmuaf.fit.project_fruit.controller.admin;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.project_fruit.dao.InvoiceDetailDao;
import vn.edu.hcmuaf.fit.project_fruit.dao.cart.CartProduct;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/admin/invoice-detail")
public class InvoiceDetailServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int invoiceId = Integer.parseInt(req.getParameter("id"));
        InvoiceDetailDao dao = new InvoiceDetailDao();
        List<CartProduct> details = dao.getInvoiceDetails(invoiceId); // cần viết phương thức này

        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        Gson gson = new Gson();
        out.print(gson.toJson(details));
        out.flush();
    }
}
