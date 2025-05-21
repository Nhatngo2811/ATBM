package vn.edu.hcmuaf.fit.project_fruit.dao;

import vn.edu.hcmuaf.fit.project_fruit.dao.db.DbConnect;
import vn.edu.hcmuaf.fit.project_fruit.dao.model.Category;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao {
    public List<Category> getAll() {
        Statement s = DbConnect.get();
        if (s == null) return new ArrayList<>();
        ResultSet rs;
        try {
            ArrayList<Category> categories = new ArrayList<>();
            rs = s.executeQuery("SELECT * FROM category_products");
            while (rs.next()) {
                categories.add(new Category(
                        rs.getInt("id_category"),
                        rs.getString("name_category"),
                        rs.getString("describe_1")
                ));
            }
            return categories;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Category getCategoryById(int id) {
        Statement s = DbConnect.get();
        if (s == null) return null;
        ResultSet rs;
        try {
            rs = s.executeQuery("SELECT * FROM category_products WHERE id_category = " + id);
            if (rs.next()) {
                return new Category(
                        rs.getInt("id_category"),
                        rs.getString("name_category"),
                        rs.getString("describe_1")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
