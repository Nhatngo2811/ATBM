package vn.edu.hcmuaf.fit.project_fruit.dao.model;

public class Category {
    private int id_category;
    private String name_category;
    private String describe_1;

    public Category(int id_category, String name_category, String describe_1) {
        this.id_category = id_category;
        this.name_category = name_category;
        this.describe_1 = describe_1;
    }

    public int getId_category() {
        return id_category;
    }

    public void setId_category(int id_category) {
        this.id_category = id_category;
    }

    public String getName_category() {
        return name_category;
    }

    public void setName_category(String name_category) {
        this.name_category = name_category;
    }

    public String getDescribe_1() {
        return describe_1;
    }

    public void setDescribe_1(String describe_1) {
        this.describe_1 = describe_1;
    }
}
