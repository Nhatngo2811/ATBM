// Khởi tạo danh sách tài khoản
let users = [];

// Hàm lưu dữ liệu vào Local Storage
function saveToLocalStorage() {
    localStorage.setItem("users", JSON.stringify(users));
}

// Hàm tải dữ liệu từ Local Storage
function loadFromLocalStorage() {
    const storedUsers = localStorage.getItem("users");
    users = storedUsers ? JSON.parse(storedUsers) : [];
}

// Hàm hiển thị danh sách tài khoản trên bảng
function renderUserTable() {
    const userTableBody = document.getElementById("userTableBody");
    userTableBody.innerHTML = ""; // Xóa nội dung cũ

    if (users.length === 0) {
        // Nếu danh sách trống, hiển thị thông báo
        const row = document.createElement("tr");
        row.innerHTML = `<td colspan="3" style="text-align: center; color: #888;">Chưa có tài khoản nào</td>`;
        userTableBody.appendChild(row);
    } else {
        // Hiển thị danh sách tài khoản
        users.forEach((user, index) => {
            const row = document.createElement("tr");
            row.innerHTML = `
                <td>${user.username}</td>
                <td>${user.role}</td>
                <td>
                    <button class="btn-edit" onclick="editUser(${index})">Sửa</button>
                    <button class="btn-delete" onclick="deleteUser(${index})">Xóa</button>
                </td>
            `;
            userTableBody.appendChild(row);
        });
    }
}

// Hàm thêm tài khoản mới
function addUser() {
    const username = document.getElementById("usernameInput").value.trim();
    const role = document.getElementById("userRoleInput").value;

    if (!username) {
        alert("Tên tài khoản không được để trống!");
        return;
    }

    // Kiểm tra tài khoản đã tồn tại
    if (users.some((user) => user.username.toLowerCase() === username.toLowerCase())) {
        alert("Tên tài khoản đã tồn tại!");
        return;
    }

    // Thêm tài khoản vào danh sách
    users.push({ username, role });
    renderUserTable(); // Cập nhật bảng
    alert("Tài khoản mới đã được thêm!");

    // Reset form
    document.getElementById("usernameInput").value = "";
    document.getElementById("userRoleInput").value = "admin";
}

// Hàm sửa tài khoản
function editUser(index) {
    const user = users[index];
    document.getElementById("usernameInput").value = user.username;
    document.getElementById("userRoleInput").value = user.role;

    // Gắn trạng thái chỉnh sửa
    document.getElementById("usernameInput").dataset.editIndex = index; // Lưu chỉ số tài khoản đang sửa
    alert("Đang chỉnh sửa tài khoản. Sau khi chỉnh sửa, nhấn Lưu để cập nhật.");
}

// Hàm lưu chỉnh sửa tài khoản
function saveChanges() {
    const editIndex = document.getElementById("usernameInput").dataset.editIndex;
    const username = document.getElementById("usernameInput").value.trim();
    const role = document.getElementById("userRoleInput").value;

    if (editIndex !== undefined) {
        // Cập nhật tài khoản nếu đang ở chế độ chỉnh sửa
        users[editIndex] = { username, role };
        delete document.getElementById("usernameInput").dataset.editIndex; // Xóa trạng thái chỉnh sửa
        alert("Tài khoản đã được cập nhật!");
    } else {
        alert("Không có thay đổi nào để lưu!");
    }

    // Lưu trạng thái vào Local Storage
    saveToLocalStorage();
    renderUserTable(); // Cập nhật bảng
}

// Hàm xóa tài khoản
function deleteUser(index) {
    if (confirm("Bạn có chắc chắn muốn xóa tài khoản này?")) {
        users.splice(index, 1); // Xóa tài khoản khỏi danh sách
        saveToLocalStorage(); // Lưu trạng thái vào Local Storage
        renderUserTable(); // Cập nhật bảng
    }
}

// Khi mở form "Quản lý người dùng"
function openUserManagementModal() {
    loadFromLocalStorage(); // Tải dữ liệu từ Local Storage
    renderUserTable(); // Hiển thị danh sách
    document.getElementById("userManagementModal").style.display = "block";
}
// Khởi tạo dữ liệu khi tải trang
window.onload = () => {
    loadFromLocalStorage(); // Tải dữ liệu từ Local Storage
    renderUserTable(); // Cập nhật bảng
};



// Hệ thống
function saveSystemConfig() {
    // Lấy giá trị từ các input trong form
    const systemConfigData = {
        systemName: document.getElementById("systemName").value,
        adminEmail: document.getElementById("adminEmail").value,
        language: document.getElementById("language").value,
        timeZone: document.getElementById("timeZone").value,
        maintenanceMode: document.getElementById("maintenanceMode").value,
        maxUsers: document.getElementById("maxUsers").value
    };

    // Lưu vào localStorage
    localStorage.setItem("systemConfig", JSON.stringify(systemConfigData));
    alert("Cấu hình hệ thống đã được lưu thành công!");
    closeModal("systemConfig");
}


function editProduct() {
    // Chuyển tất cả các trường thông tin từ chế độ xem sang chế độ chỉnh sửa
    document.getElementById('product-description-name').style.display = 'none';
    document.getElementById('edit-product-name').style.display = 'inline-block';

    document.getElementById('product-description-code').style.display = 'none';
    document.getElementById('edit-product-code').style.display = 'inline-block';

    document.getElementById('product-description-price').style.display = 'none';
    document.getElementById('edit-product-price').style.display = 'inline-block';

    document.getElementById('product-description-category').style.display = 'none';
    document.getElementById('edit-product-category').style.display = 'inline-block';

    document.getElementById('product-description-origin').style.display = 'none';
    document.getElementById('edit-product-origin').style.display = 'inline-block';

    document.getElementById('product-description-description').style.display = 'none';
    document.getElementById('edit-product-description').style.display = 'inline-block';

    // Hiển thị nút Lưu
    document.getElementById('edit-product-button').style.display = 'none';
    document.getElementById('save-product-button').style.display = 'inline-block';
}

function saveProduct() {
    // Lấy dữ liệu từ các input sau khi chỉnh sửa
    const productName = document.getElementById('edit-product-name').value;
    const productCode = document.getElementById('edit-product-code').value;
    const productPrice = document.getElementById('edit-product-price').value;
    const productCategory = document.getElementById('edit-product-category').value;
    const productOrigin = document.getElementById('edit-product-origin').value;
    const productDescription = document.getElementById('edit-product-description').value;

    // Cập nhật thông tin vào các span
    document.getElementById('product-description-name').innerText = productName;
    document.getElementById('product-description-code').innerText = productCode;
    document.getElementById('product-description-price').innerText = productPrice;
    document.getElementById('product-description-category').innerText = productCategory;
    document.getElementById('product-description-origin').innerText = productOrigin;
    document.getElementById('product-description-description').innerText = productDescription;

    // Chuyển các input trở lại chế độ xem
    document.getElementById('product-description-name').style.display = 'inline-block';
    document.getElementById('edit-product-name').style.display = 'none';

    document.getElementById('product-description-code').style.display = 'inline-block';
    document.getElementById('edit-product-code').style.display = 'none';

    document.getElementById('product-description-price').style.display = 'inline-block';
    document.getElementById('edit-product-price').style.display = 'none';

    document.getElementById('product-description-category').style.display = 'inline-block';
    document.getElementById('edit-product-category').style.display = 'none';

    document.getElementById('product-description-origin').style.display = 'inline-block';
    document.getElementById('edit-product-origin').style.display = 'none';

    document.getElementById('product-description-description').style.display = 'inline-block';
    document.getElementById('edit-product-description').style.display = 'none';

    // Ẩn nút Lưu và hiển thị nút Chỉnh sửa
    document.getElementById('edit-product-button').style.display = 'inline-block';
    document.getElementById('save-product-button').style.display = 'none';
}

