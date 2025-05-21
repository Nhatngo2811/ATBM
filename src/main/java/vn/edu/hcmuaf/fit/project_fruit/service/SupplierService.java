package vn.edu.hcmuaf.fit.project_fruit.service;

import vn.edu.hcmuaf.fit.project_fruit.dao.SupplierDao;
import vn.edu.hcmuaf.fit.project_fruit.dao.model.Supplier;

import java.util.List;

public class SupplierService {

    private SupplierDao supplierDao = new SupplierDao();

    // Lấy tất cả nhà cung cấp
    public List<Supplier> getAll() {
        return supplierDao.getAllSuppliers();
    }

    // Lấy danh sách phân trang
    public List<Supplier> getSuppliersByPage(int page, int recordsPerPage) {
        return supplierDao.getSuppliersByPage(page, recordsPerPage);
    }

    // Tổng số nhà cung cấp
    public int getTotalRecords() {
        return supplierDao.getTotalRecords();
    }

}
