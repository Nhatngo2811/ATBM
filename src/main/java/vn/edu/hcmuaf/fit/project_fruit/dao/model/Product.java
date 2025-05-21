package vn.edu.hcmuaf.fit.project_fruit.dao.model;

import java.io.Serializable;
import java.util.List;

public class Product implements Serializable {
    private int id_product;
    private String name;
    private List<ProductImg> listImg;
    private double price;
    private String rating;
    private boolean status;
    private String describe_1;
    private int quantity;
    private String origin;
    private String entry_date;
    private String shelf_life;
    private String warranty_period;
    private String characteristic;
    private String preserve_product;
    private String use_prodcut;
    private String benefit;
    private String promotionName;
    private double percentDiscount;
    private double discountedPrice;
    private int totalQuantity; // Tổng số lượng đã bán
    private double totalAmount;

//    private int categoryId;


    public Product() {
    }

    public Product(String name, int totalQuantity, double totalAmount) {
        this.name = name;
        this.totalQuantity = totalQuantity;
        this.totalAmount = totalAmount;
    }

    // danh sach san pham trang chu
    public Product(int id_product, String name, List<ProductImg> listImg, double price, String rating , double percentDiscount) {
        this.id_product = id_product;
        this.name = name;
        this.listImg = listImg;
        this.price = price;
        this.rating = rating;
        this.percentDiscount = percentDiscount;
        calculateDiscountedPrice();

    }
    // Chi tiet san pham
    public Product(int id_product, String name, List<ProductImg> listImg, double price, String rating, boolean status, String describe_1, int quantity, String origin, String entry_date, String shelf_life, String warranty_period, String characteristic, String preserve_product, String use_prodcut, String benefit, String promotionName, double percentDiscount) {
        this.id_product = id_product;
        this.name = name;
        this.listImg = listImg;
        this.price = price;
        this.rating = rating;
        this.status = status;
        this.describe_1 = describe_1;
        this.quantity = quantity;
        this.origin = origin;
        this.entry_date = entry_date;
        this.shelf_life = shelf_life;
        this.warranty_period = warranty_period;
        this.characteristic = characteristic;
        this.preserve_product = preserve_product;
        this.use_prodcut = use_prodcut;
        this.benefit = benefit;
        this.promotionName = promotionName;
        this.percentDiscount = percentDiscount;
    }

    public void setDiscountedPrice(double discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
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

    public List<ProductImg> getListimg() {
        return listImg;
    }

    public void setListImg(List<ProductImg> listImg) {
        this.listImg = listImg;
    }
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getEntry_date() {
        return entry_date;
    }

    public void setEntry_date(String entry_date) {
        this.entry_date = entry_date;
    }

    public String getShelf_life() {
        return shelf_life;
    }

    public void setShelf_life(String shelf_life) {
        this.shelf_life = shelf_life;
    }

    public String getWarranty_period() {
        return warranty_period;
    }

    public void setWarranty_period(String warranty_period) {
        this.warranty_period = warranty_period;
    }

    public String getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(String characteristic) {
        this.characteristic = characteristic;
    }

    public String getPreserve_product() {
        return preserve_product;
    }

    public void setPreserve_product(String preserve_product) {
        this.preserve_product = preserve_product;
    }

    public String getUse_prodcut() {
        return use_prodcut;
    }

    public void setUse_prodcut(String use_prodcut) {
        this.use_prodcut = use_prodcut;
    }

    public String getBenefit() {
        return benefit;
    }

    public void setBenefit(String benefit) {
        this.benefit = benefit;
    }
    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    public double getPercentDiscount() {
        return percentDiscount;
    }

    public void setPercentDiscount(double percentDiscount) {
        this.percentDiscount = percentDiscount;
    }


    // Phương thức tính giá giảm
    public void calculateDiscountedPrice() {
        if (percentDiscount > 0) {
            this.discountedPrice = price * (1 - percentDiscount / 100.0);
        } else {
            this.discountedPrice = price;
        }
    }

    // Getter cho `discountedPrice`
    public double getDiscountedPrice() {
        return discountedPrice;
    }
    // Lấy URL hình ảnh chính hoặc hình đầu tiên
    public String getImageUrl() {
        if (listImg == null || listImg.isEmpty()) {
            return "./assets/img/default.jpg"; // Hình ảnh mặc định nếu không có hình
        }
        for (ProductImg img : listImg) {
            if (img != null && img.isMainImage()) { // Kiểm tra hình ảnh chính
                return img.getUrl();
            }
        }
        return listImg.get(0).getUrl(); // Trả về hình đầu tiên nếu không có hình chính
    }
    public String getStatusDisplay() {
        return status ? "Còn hàng" : "Hết hàng";
    }

    @Override
    public String toString() {
        return "Product{" +
                "id_product=" + id_product +
                ", name='" + name + '\'' +
                ", listImg=" + listImg +
                ", price=" + price +
                ", rating='" + rating + '\'' +
                ", status=" + status +
                ", describe_1='" + describe_1 + '\'' +
                ", quantity=" + quantity +
                ", origin='" + origin + '\'' +
                ", entry_date='" + entry_date + '\'' +
                ", shelf_life='" + shelf_life + '\'' +
                ", warranty_period='" + warranty_period + '\'' +
                ", characteristic='" + characteristic + '\'' +
                ", preserve_product='" + preserve_product + '\'' +
                ", use_prodcut='" + use_prodcut + '\'' +
                ", benefit='" + benefit + '\'' +
                ", promotionName='" + promotionName + '\'' +
                ", percentDiscount=" + percentDiscount +
                '}';
    }
}


