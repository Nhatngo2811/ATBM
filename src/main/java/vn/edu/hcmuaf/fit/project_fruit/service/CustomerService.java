package vn.edu.hcmuaf.fit.project_fruit.service;


import vn.edu.hcmuaf.fit.project_fruit.dao.CustomerDao;
import vn.edu.hcmuaf.fit.project_fruit.dao.model.Customer;

import java.util.List;

public class CustomerService {
    private final CustomerDao customerDao = new CustomerDao();

    // Lấy tất cả khách hàng
    public List<Customer> getAllCustomers() {
        return customerDao.getAllCustomers();
    }

    // In ra thông tin tất cả khách hàng
    public void printAllCustomers() {
        customerDao.printAllCustomers();
    }

    // Lấy danh sách khách hàng theo phân trang
    public List<Customer> getCustomersByPage(int page, int recordsPerPage) {
        return customerDao.getCustomersByPage(page, recordsPerPage);
    }

    // Lấy tổng số bản ghi khách hàng
    public int getTotalRecords() {
        return customerDao.getTotalRecords();
    }
    // Lấy khách hàng theo ID
    public Customer getCustomerById(int customerId) {
        return customerDao.getCustomerById(customerId);
    }

    // Cập nhật thông tin khách hàng
    public boolean updateCustomerDetails(int customerId, String customerName, String customerPhone, String address) {
        return customerDao.updateCustomerDetails(customerId, customerName, customerPhone, address);
    }
    public List<Customer> getRecentCustomers() {
        return customerDao.getRecentCustomers();
    }
    public void printRecentCustomers() {
        List<Customer> recentCustomers = getRecentCustomers();
        if (recentCustomers != null && !recentCustomers.isEmpty()) {
            System.out.println("Danh sách khách hàng gần đây:");
            for (Customer customer : recentCustomers) {
                System.out.println("ID: " + customer.getIdCustomer());
                System.out.println("Tên: " + customer.getCustomerName());
                System.out.println("Số điện thoại: " + customer.getCustomerPhone());
                System.out.println("Địa chỉ: " + customer.getAddress());
                System.out.println("Ngày tạo tài khoản: " + customer.getDateRegister());
                System.out.println("Email: " + customer.getEmail());
                System.out.println("----------------------------");
            }
        } else {
            System.out.println("Không có khách hàng gần đây.");
        }
    }
    public static void main(String[] args) {
        CustomerService customerService = new CustomerService();

        // Test 1: Lấy và in ra danh sách khách hàng gần đây
        System.out.println("Test 1: Lấy danh sách khách hàng gần đây");
        customerService.printRecentCustomers();
    }
}
