package vn.edu.hcmuaf.fit.project_fruit.service;

import vn.edu.hcmuaf.fit.project_fruit.dao.ShippingDAO;
import vn.edu.hcmuaf.fit.project_fruit.dao.model.Shipping;

public class ShippingService {
    private ShippingDAO shippingDAO;

    public ShippingService() {
        this.shippingDAO = new ShippingDAO();
    }

    public boolean createShipping(Shipping shipping) {
        return shippingDAO.insertShipping(shipping);
    }
}

