package vn.edu.hcmuaf.fit.project_fruit.dao.model;

import java.io.Serializable;

public class ProductList implements Serializable {
    private int id_product;
    private String name;
    private String categoryName;
    private double price;
    private String origin;
    private boolean status;
    private String describe_1;
    private String productImgUrl;

    public ProductList(int id_product, String name, String categoryName, double price, String origin, boolean status, String describe_1, String productImUrl) {
        this.id_product = id_product;
        this.name = name;
        this.categoryName = categoryName;
        this.price = price;
        this.origin = origin;
        this.status = status;
        this.describe_1 = describe_1;
        this.productImgUrl = productImUrl;
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getDescribe_1() {
        return describe_1;
    }

    public void setDescribe_1(String describe_1) {
        this.describe_1 = describe_1;
    }

    public String getProductImgUrl() {
        return productImgUrl;
    }

    public void setProductImgUrl(String productImgUrl) {
        this.productImgUrl = productImgUrl;
    }

    @Override
    public String toString() {
        return "ProductList{" +
                "id_product=" + id_product +
                ", name='" + name + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", price=" + price +
                ", origin='" + origin + '\'' +
                ", status=" + status +
                ", describe_1='" + describe_1 + '\'' +
                ", productImg=" + productImgUrl +
                '}';
    }
}
