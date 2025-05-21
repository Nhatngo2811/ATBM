function register(event) {
    event.preventDefault();
    const username = document.getElementById("username").value;
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    const confirmPassword = document.getElementById("confirm-password").value;

    if (password !== confirmPassword) {
        Swal.fire({
            title: "Lỗi!",
            text: "Mật khẩu không khớp!",
            icon: "error",
            confirmButtonText: "Thử lại"
        });
        return;
    }

    // Lưu thông tin người dùng vào Local Storage
    localStorage.setItem("userName", username);
    localStorage.setItem("userEmail", email);
    localStorage.setItem("userPassword", password);

    Swal.fire({
        title: "Đăng ký thành công!",
        text: "Tài khoản của bạn đã được tạo.",
        icon: "success",
        confirmText: "OK",
    }).then(() => {
        window.location.href = "../index.jsp"; // Chuyển hướng về trang chủ
    });
}