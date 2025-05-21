function login() {
    event.preventDefault();
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    const errorMessage = document.getElementById("error-message");
    const loginForm = document.getElementById("loginForm");

    // Thông tin tài khoản admin
    const adminEmail = "nhom55@gmail.com";
    const adminPassword = "nhom55";

    // Lấy thông tin người dùng từ Local Storage
    const storedEmail = localStorage.getItem("userEmail");
    const storedPassword = localStorage.getItem("userPassword");
    const userName = localStorage.getItem("userName");

    if (email === adminEmail && password === adminPassword) {
        // Chuyển hướng đến trang Admin
        Swal.fire({
            title: "Đăng nhập thành công!",
            text: "Chào mừng bạn đến với trang quản trị.",
            icon: "success",
            confirmButtonText: "Đến trang quản trị",
            customClass: {
                confirmButton: 'swal-button-large'
            }
        }).then(() => {
            window.location.href = "/project_fruit/admin/admin.jsp";
        });
    } else if (email === storedEmail && password === storedPassword) {
        // Đăng nhập người dùng thường thành công
        loginForm.style.display = "none";
        localStorage.setItem("isLoggedIn", true); // Lưu trạng thái đăng nhập

        // Hiển thị avatar và tên người dùng
        updateUserHeader(userName);

        Swal.fire({
            title: `Chào mừng, ${userName}!`,
            text: "Đăng nhập thành công.",
            icon: "success",
            confirmText: "OK",
        });
    } else {
        // Hiển thị thông báo lỗi nếu thông tin không đúng
        errorMessage.style.display = "block";
        document.getElementById("email").value = "";
        document.getElementById("password").value = "";
    }
}

function updateUserHeader(userName) {
    const accountSection = document.querySelector(".account");
    accountSection.innerHTML = `
        <img src="../img/anhdaidien.jpg" alt="Avatar" class="avatar" style="width: 30px; height: 30px; border-radius: 50%; cursor: pointer;">
`;

    // Hiển thị menu dropdown khi nhấp vào avatar
    const avatarImg = accountSection.querySelector(".avatar");
    avatarImg.addEventListener("click", toggleUserMenu);
    document.querySelector('.account img').style.display = "block";
    if (userNameDisplay) {
        userNameDisplay.textContent = userName; // Thay "User" bằng tên người dùng
    }
}

function toggleUserMenu() {
    const userMenu = document.getElementById("userMenu");
    userMenu.style.display = userMenu.style.display === "block" ? "none" : "block";
}

// Kiểm tra trạng thái đăng nhập khi tải trang
function checkLoginStatus() {
    const isLoggedIn = localStorage.getItem("isLoggedIn") === "true";
    const userName = localStorage.getItem("userName");

    if (isLoggedIn && userName) {
        updateUserHeader(userName);
    }
}

// Đăng xuất
function logout() {
    localStorage.removeItem("isLoggedIn");
    Swal.fire({
        title: "Đăng xuất thành công!",
        text: "Bạn sẽ được chuyển về trang chủ.",
        icon: "success",
        showConfirmButton: false,
        timer: 3000,
        timerProgressBar: true
    });


    setTimeout(() => {
        window.location.href = "../index.jsp";
    }, 3000);
}

// Gọi hàm kiểm tra trạng thái khi trang được tải
window.addEventListener("DOMContentLoaded", checkLoginStatus);
