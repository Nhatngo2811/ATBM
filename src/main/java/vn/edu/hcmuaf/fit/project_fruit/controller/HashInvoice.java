package vn.edu.hcmuaf.fit.project_fruit.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.project_fruit.dao.InvoiceDao;
import vn.edu.hcmuaf.fit.project_fruit.dao.model.Invoice;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@WebServlet("/hash-invoice")
public class HashInvoice extends HttpServlet {
    public static String hashSHA256(String input) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(input.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) sb.append(String.format("%02x", b));
        return sb.toString();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idStr = request.getParameter("id");
        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        String json;
        try {
            int id = Integer.parseInt(idStr);
            Invoice invoice = InvoiceDao.getInvoiceById(id);
            if (invoice == null) {
                json = "{\"success\": false, \"message\": \"Không tìm thấy hóa đơn!\"}";
            } else {
                String invoiceText = InvoiceDao.invoiceToText(invoice);
                String hash = hashSHA256(invoiceText);
                json = "{"
                        + "\"success\": true,"
                        + "\"invoiceText\": \"" + invoiceText.replace("\"", "\\\"") + "\","
                        + "\"hash\": \"" + hash + "\""
                        + "}";
            }
        } catch (Exception ex) {
            json = "{\"success\": false, \"message\": \"" + ex.getMessage().replace("\"", "\\\"") + "\"}";
        }
        response.getWriter().write(json);
    }
}
