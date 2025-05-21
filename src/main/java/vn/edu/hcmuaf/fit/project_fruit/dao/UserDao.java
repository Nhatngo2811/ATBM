package vn.edu.hcmuaf.fit.project_fruit.dao;

import vn.edu.hcmuaf.fit.project_fruit.dao.db.DbConnect;
import vn.edu.hcmuaf.fit.project_fruit.dao.model.User;

import java.sql.*;

public class UserDao {

    // Lấy thông tin người dùng qua email và mật khẩu
    public User getUserByEmailAndPassword(String email, String password) {
        User user = null;
        String query = "SELECT * FROM accounts WHERE email = ?";
        try (PreparedStatement ps = DbConnect.getPreparedStatement(query, true)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // Nếu tìm thấy người dùng, tạo đối tượng User
                user = new User(
                        rs.getInt("id_account"),
                        rs.getString("email"),
                        rs.getString("password"), // Mật khẩu hash
                        rs.getString("role"),
                        rs.getInt("id_customer"),
                        rs.getString("google_id") // Thêm Google ID
                );
                user.setVerified(rs.getInt("is_verified") == 1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    // Kiểm tra email đã tồn tại chưa
    public boolean isEmailExists(String email) {
        String query = "SELECT COUNT(*) FROM accounts WHERE email = ?";
        try (Connection connection = DbConnect.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Trả về true nếu email đã tồn tại
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Đăng ký người dùng vào bảng customers và accounts
    public boolean registerUser(User user, String fullName) {
        String insertCustomerQuery = "INSERT INTO customers (customer_name, customer_phone, address) VALUES (?, ?, ?)";
        String insertAccountQuery = "INSERT INTO accounts (email, password, role, create_date, id_customer, verify_token, otp_code, otp_expiry, is_verified) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection connection = null;
        PreparedStatement psCustomer = null;
        PreparedStatement psAccount = null;

        try {
            connection = DbConnect.getConnection();
            connection.setAutoCommit(false); // Bắt đầu transaction

            // Thêm vào bảng customers
            psCustomer = connection.prepareStatement(insertCustomerQuery, Statement.RETURN_GENERATED_KEYS);
            psCustomer.setString(1, fullName);
            psCustomer.setString(2, "unknown");
            psCustomer.setString(3, "unknown");
            int customerRows = psCustomer.executeUpdate();

            if (customerRows == 0) {
                System.out.println("❌ Không thể thêm customer");
                connection.rollback();
                return false;
            }

            ResultSet rs = psCustomer.getGeneratedKeys();
            int idCustomer = 0;
            if (rs.next()) {
                idCustomer = rs.getInt(1);
            } else {
                System.out.println("❌ Không lấy được ID của customer");
                connection.rollback();
                return false;
            }

            // Thêm vào bảng accounts
            psAccount = connection.prepareStatement(insertAccountQuery);
            psAccount.setString(1, user.getEmail());
            psAccount.setString(2, user.getPassword());
            psAccount.setString(3, user.getRole());
            psAccount.setDate(4, Date.valueOf(java.time.LocalDate.now()));
            psAccount.setInt(5, idCustomer);
            psAccount.setString(6, user.getVerifyToken());         // Giữ lại verify_token
            psAccount.setString(7, user.getOtpCode());             // Mã OTP
            psAccount.setTimestamp(8, user.getOtpExpiry());        // Thời hạn OTP
            psAccount.setBoolean(9, false);                        // is_verified
            int accountRows = psAccount.executeUpdate();

            if (accountRows == 0) {
                System.out.println("❌ Không thể thêm account");
                connection.rollback();
                return false;
            }

            connection.commit();
            System.out.println("✅ Đăng ký thành công cho email: " + user.getEmail());
            return true;

        } catch (SQLException e) {
            System.out.println("❌ Lỗi SQL trong quá trình đăng ký: " + e.getMessage());
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
        } finally {
            try {
                if (psCustomer != null) psCustomer.close();
                if (psAccount != null) psAccount.close();
                if (connection != null) connection.close();
            } catch (SQLException closeEx) {
                closeEx.printStackTrace();
            }
        }

        System.out.println("❌ Đăng ký thất bại hoàn toàn.");
        return false;
    }
    // Cập nhật mật khẩu theo email
    public boolean updatePasswordByEmail(String email, String hashedPassword) {
        String query = "UPDATE accounts SET password = ? WHERE email = ?";
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, hashedPassword);
            ps.setString(2, email);
            return ps.executeUpdate() > 0; // Trả về true nếu cập nhật thành công
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Lấy thông tin người dùng qua email
    public User getUserByEmail(String email) {
        String query = "SELECT * FROM accounts WHERE email = ?";
        try (PreparedStatement ps = DbConnect.getPreparedStatement(query, true)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id_account"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getInt("id_customer"),
                        rs.getString("google_id") // Lấy thêm Google ID
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Liên kết tài khoản Google với tài khoản email đã có trong hệ thống
    public boolean linkGoogleAccount(String email, String googleId) {
        String query = "UPDATE accounts SET google_id = ? WHERE email = ?";
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, googleId);
            ps.setString(2, email);
            return ps.executeUpdate() > 0; // Trả về true nếu cập nhật thành công
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean verifyEmailByToken(String token) {
        String sql = "UPDATE accounts SET is_verified = TRUE, verify_token = NULL WHERE verify_token = ?";
        try (Connection conn = DbConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, token);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean updateVerifyToken(String email, String token) {
        String query = "UPDATE accounts SET verify_token = ? WHERE email = ?";
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, token);
            ps.setString(2, email);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean verifyOtpCode(String email, String otp) {
        String sql = "SELECT otp_expiry FROM accounts WHERE email = ? AND otp_code = ?";
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, otp);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Timestamp expiry = rs.getTimestamp("otp_expiry");
                if (expiry != null && expiry.after(new Timestamp(System.currentTimeMillis()))) {
                    // Đúng OTP và chưa hết hạn → xác thực
                    String updateSql = "UPDATE accounts SET is_verified = TRUE, otp_code = NULL, otp_expiry = NULL WHERE email = ?";
                    try (PreparedStatement updatePs = conn.prepareStatement(updateSql)) {
                        updatePs.setString(1, email);
                        return updatePs.executeUpdate() > 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        // Tạo đối tượng User
        String email = "newuser1" + System.currentTimeMillis() + "@gmail.com"; // random để tránh trùng
        String password = "hashed_example_password"; // Nếu có bcrypt thì hash trước
        String role = "user";
        String fullName = "Nguyen Van A";

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);

        // Gọi hàm đăng ký
        UserDao userDao = new UserDao();
        boolean result = userDao.registerUser(user, fullName);

        // In kết quả
        if (result) {
            System.out.println("✅ Đăng ký thành công cho email: " + email);
        } else {
            System.out.println("❌ Đăng ký thất bại.");
        }
    }

}