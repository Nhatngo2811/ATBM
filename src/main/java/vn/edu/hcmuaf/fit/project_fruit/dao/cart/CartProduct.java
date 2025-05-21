package vn.edu.hcmuaf.fit.project_fruit.dao.cart;

import vn.edu.hcmuaf.fit.project_fruit.dao.model.ProductImg;

import java.io.Serializable;
import java.util.List;

public class CartProduct implements Serializable {
    private int id_product ;
    private String name;
    private double price;
    private List<ProductImg> listImg;
    private int quantity;
    private double discount = 0;

    public CartProduct(int id_product, String name, double price, List<ProductImg> listImg, int quantity) {
        this.id_product = id_product;
        this.name = name;
        this.price = price;
        this.listImg = listImg;
        this.quantity = quantity;
    }

    public CartProduct() {
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<ProductImg> getListImg() {
        return listImg;
    }

    public void setListImg(List<ProductImg> listImg) {
        this.listImg = listImg;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
