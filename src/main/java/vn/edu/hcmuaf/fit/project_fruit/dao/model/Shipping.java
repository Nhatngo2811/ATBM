package vn.edu.hcmuaf.fit.project_fruit.dao.model;

public class Shipping {
    private int idShipping;
    private int idInvoice;  // Liên kết với bảng `invoices`
    private int shippingMethodId; // Phương thức vận chuyển (ID)
    private double shippingFee;    // Phí vận chuyển
    private String status;        // Trạng thái (Đang vận chuyển, Đã giao, ...)
    private String address;       // Địa chỉ giao hàng

    // Constructor, Getters, and Setters
    public Shipping(int idInvoice, int shippingMethodId, double shippingFee, String status, String address) {
        this.idInvoice = idInvoice;
        this.shippingMethodId = shippingMethodId;
        this.shippingFee = shippingFee;
        this.status = status;
        this.address = address;
    }

    // Getters and Setters
    public int getIdShipping() {
        return idShipping;
    }

    public void setIdShipping(int idShipping) {
        this.idShipping = idShipping;
    }

    public int getIdInvoice() {
        return idInvoice;
    }

    public void setIdInvoice(int idInvoice) {
        this.idInvoice = idInvoice;
    }

    public int getShippingMethodId() {
        return shippingMethodId;
    }

    public void setShippingMethodId(int shippingMethodId) {
        this.shippingMethodId = shippingMethodId;
    }

    public double getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(double shippingFee) {
        this.shippingFee = shippingFee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
