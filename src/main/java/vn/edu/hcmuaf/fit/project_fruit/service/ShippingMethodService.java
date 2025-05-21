package vn.edu.hcmuaf.fit.project_fruit.service;

import vn.edu.hcmuaf.fit.project_fruit.dao.ShippingMethodDAO;
import vn.edu.hcmuaf.fit.project_fruit.dao.model.ShippingMethod;

import java.util.List;

public class ShippingMethodService {
    private ShippingMethodDAO shippingMethodDAO;

    public ShippingMethodService() {
        this.shippingMethodDAO = new ShippingMethodDAO();
    }

    public List<ShippingMethod> getAllShippingMethods() {
        return shippingMethodDAO.getAllShippingMethods();
    }
    public static void main(String[] args) {
        // Tạo một đối tượng ShippingMethodService
        ShippingMethodService service = new ShippingMethodService();

        // Lấy danh sách các phương thức vận chuyển
        List<ShippingMethod> methods = service.getAllShippingMethods();

        // In ra danh sách các phương thức vận chuyển
        for (ShippingMethod method : methods) {
            System.out.println("ID: " + method.getId());
            System.out.println("Method Name: " + method.getMethodName());
            System.out.println("Carrier: " + method.getCarrier());
            System.out.println("Shipping Fee: " + method.getShippingFee());
            System.out.println("-------------------------------");
        }
    }
}
