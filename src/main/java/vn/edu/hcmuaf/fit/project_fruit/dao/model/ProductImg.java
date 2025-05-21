package vn.edu.hcmuaf.fit.project_fruit.dao.model;

public class ProductImg {
    private int id; // ID hình ảnh
    private String url; // URL hình ảnh
    private boolean isMainImage; // Có phải là hình ảnh chính hay không

    public ProductImg(int id, String url, boolean isMainImage) {
        this.id = id;
        this.url = url;
        this.isMainImage = isMainImage;
    }

    // Getter và Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isMainImage() {
        return isMainImage;
    }

    public void setMainImage(boolean mainImage) {
        isMainImage = mainImage;
    }

    @Override
    public String toString() {
        return   id + url + isMainImage ;
    }
}
