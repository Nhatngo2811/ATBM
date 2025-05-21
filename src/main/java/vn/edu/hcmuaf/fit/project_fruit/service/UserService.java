package vn.edu.hcmuaf.fit.project_fruit.service;

import org.mindrot.jbcrypt.BCrypt;
import vn.edu.hcmuaf.fit.project_fruit.dao.UserDao;
import vn.edu.hcmuaf.fit.project_fruit.dao.model.User;
import vn.edu.hcmuaf.fit.project_fruit.utils.EmailUtils;

import java.sql.Timestamp;
import java.util.UUID;

public class UserService {
    private final UserDao userDao = new UserDao();

    public User validateUser(String email, String password) {
        // Lấy user từ database
        User user = userDao.getUserByEmailAndPassword(email, null); // Không kiểm tra mật khẩu trong query

        if (user == null) {
            System.out.println("Không tìm thấy user với email: " + email);
            return null;
        }

        // Kiểm tra mật khẩu bằng BCrypt
        if (!BCrypt.checkpw(password, user.getPassword())) {
            System.out.println("Mật khẩu không chính xác.");
            return null;
        }

        // Kiểm tra vai trò hợp lệ
        if (!"admin".equals(user.getRole()) && !"user".equals(user.getRole()) && !"staff".equals(user.getRole())) {
            System.out.println("Vai trò không hợp lệ: " + user.getRole());
            return null;
        }

        return user; // Trả về user nếu hợp lệ
    }


    public boolean registerUser(String email, String password, String confirmPassword, String fullName) {
        if (userDao.isEmailExists(email)) {
            System.out.println("❌ Email đã tồn tại: " + email);
            return false;
        }

        if (!password.equals(confirmPassword)) {
            System.out.println("❌ Mật khẩu xác nhận không khớp");
            return false;
        }

        if (!isStrongPassword(password)) {
            System.out.println("❌ Mật khẩu phải dài ít nhất 8 ký tự, chứa cả chữ và số");
            return false;
        }

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        String otpCode = String.valueOf((int)(Math.random() * 900000) + 100000);
        Timestamp expiry = new Timestamp(System.currentTimeMillis() + 5 * 60 * 1000);

        // ✅ Khai báo đối tượng User trước khi sử dụng
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(hashedPassword);
        newUser.setRole("user");
        newUser.setOtpCode(otpCode);
        newUser.setOtpExpiry(expiry);
        newUser.setVerifyToken(null);
        newUser.setVerified(false);

        EmailUtils.sendOtpEmail(email, otpCode); // ✅ Gửi mã OTP

        boolean result = userDao.registerUser(newUser, fullName);

        if (result) {
            System.out.println("✅ Đăng ký thành công: " + email);
        } else {
            System.out.println("❌ Đăng ký thất bại trong DAO");
        }

        return result;
    }

    // ✅ Hàm kiểm tra mật khẩu mạnh
    public boolean isStrongPassword(String password) {
        return password.length() >= 8 &&
                password.matches(".*[A-Za-z].*") &&
                password.matches(".*[0-9].*");
    }
    // Tạo mật khẩu ngẫu nhiên
    public String generateRandomPassword() {
        return UUID.randomUUID().toString().substring(0, 8); // Tạo chuỗi ngẫu nhiên 8 ký tự
    }

    // Phục hồi mật khẩu
    public String recoverPassword(String email) {
        if (!userDao.isEmailExists(email)) return null;

        String newPassword = generateRandomPassword();
        String hashed = BCrypt.hashpw(newPassword, BCrypt.gensalt());

        return userDao.updatePasswordByEmail(email, hashed) ? newPassword : null;
    }
    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }
    public boolean linkGoogleAccount(String email, String googleId) {
        return userDao.linkGoogleAccount(email, googleId);
    }
    public boolean verifyEmailByToken(String token) {
        return userDao.verifyEmailByToken(token);
    }
    public boolean updateVerifyToken(String email, String token) {
        return userDao.updateVerifyToken(email, token);
    }
    public boolean verifyOtp(String email, String otp) {
        return userDao.verifyOtpCode(email, otp);
    }

}


