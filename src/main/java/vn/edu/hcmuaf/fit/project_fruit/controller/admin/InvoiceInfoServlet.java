package vn.edu.hcmuaf.fit.project_fruit.controller.admin;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.project_fruit.dao.InvoiceDao;
import vn.edu.hcmuaf.fit.project_fruit.dao.model.Invoice;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/admin/invoice-info")
public class InvoiceInfoServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int invoiceId = Integer.parseInt(req.getParameter("id"));
        InvoiceDao dao = new InvoiceDao();
        Invoice invoice = dao.getInvoiceById(invoiceId); // cần viết phương thức này

        if (invoice == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        Gson gson = new Gson();
        out.print(gson.toJson(invoice));
        out.flush();
    }
}

