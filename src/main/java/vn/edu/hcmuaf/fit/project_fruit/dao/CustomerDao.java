package vn.edu.hcmuaf.fit.project_fruit.dao;

import vn.edu.hcmuaf.fit.project_fruit.dao.db.DbConnect;
import vn.edu.hcmuaf.fit.project_fruit.dao.model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomerDao {

    // Lấy tất cả các khách hàng và in ra
    public List<Customer> getAllCustomers() {
        // Sử dụng getPreparedStatement từ DbConnect để lấy PreparedStatement
        PreparedStatement ps = DbConnect.getPreparedStatement(
                "SELECT c.id_customer, c.customer_name, c.customer_phone, c.address, a.create_date, a.email, a.role " +
                        "FROM customers c " +
                        "JOIN accounts a ON c.id_customer = a.id_customer " +
                        "ORDER BY c.id_customer ASC", true);

        if (ps == null) return new ArrayList<>();  // Nếu không thể tạo PreparedStatement, trả về danh sách trống

        List<Customer> customerList = new ArrayList<>();
        ResultSet rs = null;

        try {
            rs = ps.executeQuery();  // Thực thi câu truy vấn

            // Lặp qua tất cả các bản ghi khách hàng và thêm vào danh sách
            while (rs.next()) {
                // Lấy thông tin khách hàng từ bảng customers và bảng accounts
                Customer customer = new Customer(
                        rs.getInt("id_customer"),
                        rs.getString("customer_name"),  // Lấy tên khách hàng từ bảng accounts
                        rs.getString("customer_phone"),
                        rs.getString("address"),
                        rs.getDate("create_date"),
                        rs.getString("email"),
                        rs.getString("role") // Lấy thêm role
                );
                customerList.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đảm bảo đóng ResultSet và PreparedStatement sau khi sử dụng
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return customerList;  // Trả về danh sách khách hàng
    }


    // Phương thức in ra thông tin tất cả khách hàng
    public void printAllCustomers() {
        // Giả sử getAllCustomers() là phương thức lấy tất cả khách hàng từ cơ sở dữ liệu
        List<Customer> customers = getAllCustomers();

        // Lặp qua danh sách khách hàng và in ra thông tin
        for (Customer customer : customers) {
            System.out.println("Customer ID: " + customer.getIdCustomer());
            System.out.println("Customer Name: " + customer.getCustomerName());
            System.out.println("Customer Phone: " + customer.getCustomerPhone());
            System.out.println("Address: " + customer.getAddress());
            System.out.println("Date Registered: " + customer.getDateRegister());
            System.out.println("Email: " + customer.getEmail());
            System.out.println("Role: " + customer.getRole()); // In thêm vai trò (role)
            System.out.println("------------------------------");
        }
    }


    // Phương thức lấy khách hàng theo trang
    public List<Customer> getCustomersByPage(int page, int recordsPerPage) {
        List<Customer> customerList = new ArrayList<>();
        String query = "SELECT c.id_customer, c.customer_name, c.customer_phone, c.address, a.create_date, a.email, a.role " +
                "FROM customers c " +
                "JOIN accounts a ON c.id_customer = a.id_customer " +
                "ORDER BY c.id_customer ASC " +
                "LIMIT ?, ?";  // Phân trang ở đây

        try (PreparedStatement ps = DbConnect.getPreparedStatement(query, true)) {
            ps.setInt(1, (page - 1) * recordsPerPage);  // Tính offset
            ps.setInt(2, recordsPerPage);  // Giới hạn số bản ghi mỗi trang

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                customerList.add(new Customer(
                        rs.getInt("id_customer"),
                        rs.getString("customer_name"),
                        rs.getString("customer_phone"),
                        rs.getString("address"),
                        rs.getDate("create_date"),
                        rs.getString("email"),
                        rs.getString("role") // Thêm role
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerList;
    }


    // Phương thức lấy tổng số bản ghi để tính số trang
    public int getTotalRecords() {
        String query = "SELECT COUNT(*) FROM customers";
        int totalRecords = 0;
        try (PreparedStatement ps = DbConnect.getPreparedStatement(query, true)) { // Sử dụng getPreparedStatement
            ResultSet rs = ps.executeQuery();  // Thực thi câu lệnh SQL
            if (rs.next()) {
                totalRecords = rs.getInt(1);  // Lấy giá trị COUNT(*) từ kết quả
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalRecords;
    }

    public Customer getCustomerById(int customerId) {
        String query = "SELECT c.id_customer, c.customer_name, c.customer_phone, c.address, a.create_date, a.email, a.role " +
                "FROM customers c " +
                "JOIN accounts a ON c.id_customer = a.id_customer " +
                "WHERE c.id_customer = ?";
        try (PreparedStatement ps = DbConnect.getPreparedStatement(query, true)) {
            ps.setInt(1, customerId); // Gán ID khách hàng vào câu truy vấn
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Customer(
                            rs.getInt("id_customer"),
                            rs.getString("customer_name"),
                            rs.getString("customer_phone"),
                            rs.getString("address"),
                            rs.getDate("create_date"), // Lấy ngày tạo
                            rs.getString("email"),
                            rs.getString("role") // Lấy vai trò
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public boolean updateCustomerDetails(int customerId, String customerName, String customerPhone, String address) {
        String query = "UPDATE customers SET customer_name = ?, customer_phone = ?, address = ? WHERE id_customer = ?";
        try (PreparedStatement ps = DbConnect.getPreparedStatement(query, true)) {
            ps.setString(1, customerName);
            ps.setString(2, customerPhone);
            ps.setString(3, address);
            ps.setInt(4, customerId);

            return ps.executeUpdate() > 0; // Trả về true nếu cập nhật thành công
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Customer> getRecentCustomers() {
        List<Customer> customerList = new ArrayList<>();
        String query = "SELECT c.id_customer, c.customer_name, c.customer_phone, c.address, a.create_date, a.email, a.role " +
                "FROM customers c " +
                "JOIN accounts a ON c.id_customer = a.id_customer " +
                "ORDER BY a.create_date DESC LIMIT 10";  // Lấy 10 khách hàng mới nhất

        try (PreparedStatement ps = DbConnect.getPreparedStatement(query, true);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Customer customer = new Customer(
                        rs.getInt("id_customer"),
                        rs.getString("customer_name"),
                        rs.getString("customer_phone"),
                        rs.getString("address"),
                        rs.getDate("create_date"),
                        rs.getString("email"),
                        rs.getString("role") // Lấy thêm role
                );
                customerList.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customerList;
    }


    //moi
    public List<Customer> getCustomersByRoles(List<String> roles) {
        List<Customer> customerList = new ArrayList<>();

        // Tạo một chuỗi tham số động cho mệnh đề IN, ví dụ: "?, ?, ?"
        String placeholders = String.join(", ", roles.stream().map(role -> "?").toArray(String[]::new));

        String query = "SELECT c.id_customer, c.customer_name, c.customer_phone, c.address, a.create_date, a.email, a.role " +
                "FROM customers c " +
                "JOIN accounts a ON c.id_customer = a.id_customer " +
                "WHERE a.role IN (" + placeholders + ")";

        try (PreparedStatement ps = DbConnect.getPreparedStatement(query, true)) {
            // Gán các giá trị role vào các tham số của câu lệnh SQL
            for (int i = 0; i < roles.size(); i++) {
                ps.setString(i + 1, roles.get(i)); // Gán giá trị role tương ứng
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Customer customer = new Customer(
                            rs.getInt("id_customer"),
                            rs.getString("customer_name"),
                            rs.getString("customer_phone"),
                            rs.getString("address"),
                            rs.getDate("create_date"),
                            rs.getString("email"),
                            rs.getString("role") // Lấy thêm role
                    );
                    customerList.add(customer);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerList;
    }

//moi
 
    // Main để kiểm tra và in ra dữ liệu
    public static void main(String[] args) {
        CustomerDao dao = new CustomerDao();

        List<String> userRoles = Arrays.asList("user");
        // Lấy danh sách khách hàng có vai trò là "admin"
        List<Customer> adminCustomers = dao.getCustomersByRoles(userRoles);

        // In danh sách khách hàng ra console
        if (adminCustomers.isEmpty()) {
            System.out.println("Không có khách hàng với vai trò 'admin'.");
        } else {
            System.out.println("Danh sách khách hàng với vai trò 'admin':");
            for (Customer customer : adminCustomers) {
                System.out.println("ID: " + customer.getIdCustomer());
                System.out.println("Tên: " + customer.getCustomerName());
                System.out.println("Số điện thoại: " + customer.getCustomerPhone());
                System.out.println("Địa chỉ: " + customer.getAddress());
                System.out.println("Email: " + customer.getEmail());
                System.out.println("Ngày tạo: " + customer.getDateRegister());
                System.out.println("------------------------------");
            }
        }
    }

}
