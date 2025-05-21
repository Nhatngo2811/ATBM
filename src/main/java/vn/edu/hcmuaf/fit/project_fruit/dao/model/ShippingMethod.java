package vn.edu.hcmuaf.fit.project_fruit.dao.model;

public class ShippingMethod {
    private int id;
    private String methodName;  // Tên phương thức vận chuyển
    private String carrier;     // Đơn vị vận chuyển
    private double shippingFee; // Phí vận chuyển

    // Constructor, Getters, and Setters
    public ShippingMethod(int id, String methodName, String carrier, double shippingFee) {
        this.id = id;
        this.methodName = methodName;
        this.carrier = carrier;
        this.shippingFee = shippingFee;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public double getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(double shippingFee) {
        this.shippingFee = shippingFee;
    }
}

