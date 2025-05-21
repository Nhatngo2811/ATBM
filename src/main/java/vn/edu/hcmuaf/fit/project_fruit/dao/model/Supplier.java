package vn.edu.hcmuaf.fit.project_fruit.dao.model;

import java.io.Serializable;

public class Supplier implements Serializable {
    private int id_supplier;
    private String name;
    private String address;
    private String email;
    private String phone_number;
    private String status;
    private double rating;
    private String name_category;
    private int id_category;

    // Constructor đầy đủ
    public Supplier(int id_supplier, String name, String address, String email, String phone_number, String status, double rating, String name_category, int id_category) {
        this.id_supplier = id_supplier;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone_number = phone_number;
        this.status = status;
        this.rating = rating;
        this.name_category = name_category;
        this.id_category = id_category;
    }

    // Getters và Setters
    public int getId_supplier() {
        return id_supplier;
    }

    public void setId_supplier(int id_supplier) {
        this.id_supplier = id_supplier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getName_category() {
        return name_category;
    }

    public void setName_category(String name_category) {
        this.name_category = name_category;
    }

    public int getId_category() {
        return id_category;
    }

    public void setId_category(int id_category) {
        this.id_category = id_category;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "id_supplier=" + id_supplier +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", status='" + status + '\'' +
                ", rating=" + rating +
                ", name_category='" + name_category + '\'' +
                ", id_category=" + id_category +
                '}';
    }
}
