package vn.edu.hcmuaf.fit.project_fruit.dao.model;

import java.io.Serializable;

public class Feedback implements Serializable {
        private int idFeedback;
        private String productName;
        private String cusName;
        private String content;
        private String dateCreate;
        private double rating;


    public Feedback(int idFeedback, String productName, String cusName, String content, String dateCreate, double rating) {
        this.idFeedback = idFeedback;
        this.productName = productName;
        this.cusName = cusName;
        this.content = content;
        this.dateCreate = dateCreate;
        this.rating = rating;
    }

    // Getter và Setter cho các thuộc tính
        public int getIdFeedback() {
            return idFeedback;
        }

        public void setIdFeedback(int idFeedback) {
            this.idFeedback = idFeedback;
        }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getDateCreate() {
            return dateCreate;
        }

        public void setDateCreate(String dateCreate) {
            this.dateCreate = dateCreate;
        }

        public double getRating() {
            return rating;
        }

        public void setRating(double rating) {
            this.rating = rating;
        }

    @Override
    public String toString() {
        return "Feedback{" +
                "idFeedback=" + idFeedback +
                ", idProduct=" +  productName+
                ", cusName=" + cusName +
                ", content='" + content + '\'' +
                ", dateCreate='" + dateCreate + '\'' +
                ", rating=" + rating +
                '}';
    }
}


