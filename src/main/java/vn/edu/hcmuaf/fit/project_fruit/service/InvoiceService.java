package vn.edu.hcmuaf.fit.project_fruit.service;

import vn.edu.hcmuaf.fit.project_fruit.dao.InvoiceDao;
import vn.edu.hcmuaf.fit.project_fruit.dao.InvoiceDetailDao;
import vn.edu.hcmuaf.fit.project_fruit.dao.cart.Cart;
import vn.edu.hcmuaf.fit.project_fruit.dao.cart.CartProduct;
import vn.edu.hcmuaf.fit.project_fruit.dao.model.Invoice;
import vn.edu.hcmuaf.fit.project_fruit.dao.model.User;

import java.util.List;

public class InvoiceService {
    private final InvoiceDao invoiceDao = new InvoiceDao();
    private final InvoiceDetailDao detailDao = new InvoiceDetailDao();

    public int createInvoice(User user, String name, String phone, String email, String address,
                             String paymentMethod, String shippingMethod,
                             double totalPrice, double shippingFee, Cart cart) {

        Invoice invoice = new Invoice();
        invoice.setAccountId(user.getId_account());
        invoice.setReceiverName(name);
        invoice.setPhone(phone);
        invoice.setEmail(email);
        invoice.setAddressFull(address);
        invoice.setPaymentMethod(paymentMethod);
        invoice.setShippingMethod(shippingMethod);
        invoice.setTotalPrice(totalPrice);
        invoice.setShippingFee(shippingFee);
        invoice.setStatus("Chưa thanh toán");

        int invoiceId = invoiceDao.addInvoice(invoice);

        if (invoiceId > 0) {
            for (CartProduct item : cart.getList()) {
                detailDao.addInvoiceDetail(invoiceId, item);
            }
        }

        return invoiceId;
    }
    public List<Invoice> getAllInvoices() {
        return invoiceDao.getAllInvoices();
    }
    public Invoice getInvoiceById(int id) {
        return InvoiceDao.getInvoiceById(id);
    }

    public List<CartProduct> getInvoiceDetails(int invoiceId) {
        return InvoiceDetailDao.getInvoiceDetails(invoiceId);
    }

}
