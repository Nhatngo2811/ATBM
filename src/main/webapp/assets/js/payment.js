function switchTab(tabId) {
    // Đặt trạng thái active cho tab
    document.querySelectorAll(".tab-content").forEach(tab => tab.classList.remove("active"));
    document.querySelectorAll(".tab").forEach(tab => tab.classList.remove("active"));

    document.getElementById(tabId).classList.add("active");
    if (tabId === "infoTab") {
        document.getElementById("infoTabButton").classList.add("active");
    } else {
        document.getElementById("paymentTabButton").classList.add("active");
    }
    const button = document.querySelector('.continue-button');
    if (tabId === "paymentTab") {
        button.textContent = "Thanh toán";
        button.onclick = handlePayment; // Nếu ở tab Thanh toán, đổi thành "Thanh toán"
    } else {
        button.textContent = "Tiếp tục"; // Nếu ở tab Thông tin, đổi thành "Tiếp tục"
    }
}

function validateInfoTab() {
    const name = document.getElementById("name").value.trim();
    const email = document.getElementById("email").value.trim();
    const phone = document.getElementById("phone").value.trim();
    const storeBranch = document.getElementById("storeBranch").value.trim();
    const province = document.getElementById("province").value.trim();
    const district = document.getElementById("district").value.trim();
    const ward = document.getElementById("ward").value.trim();
    const address = document.getElementById("address").value.trim();

    const deliveryMethod = document.querySelector(".method-option.active").textContent.trim();

    if (!name || !email || !phone) {
        alert("Vui lòng nhập đầy đủ thông tin khách hàng.");
        return false;
    }

    if (deliveryMethod === "Nhận tại cửa hàng" && !storeBranch) {
        alert("Vui lòng chọn chi nhánh.");
        return false;
    }

    if (deliveryMethod === "Giao hàng tận nơi" && (!province || !district || !ward || !address)) {
        alert("Vui lòng nhập đầy đủ thông tin giao hàng.");
        return false;
    }

    return true;
}

function nextTab() {
    if (validateInfoTab()) {
        switchTab("paymentTab");
    }
}
function handlePayment() {
    showSuccessImage();
}
function showSuccessImage() {
    // Create an overlay element
    const overlay = document.createElement('div');
    overlay.style.position = 'fixed';
    overlay.style.top = '0';
    overlay.style.left = '0';
    overlay.style.width = '100%';
    overlay.style.height = '100%';
    overlay.style.backgroundColor = 'rgba(0, 0, 0, 0.7)';
    overlay.style.display = 'flex';
    overlay.style.justifyContent = 'center';
    overlay.style.alignItems = 'center';
    overlay.style.zIndex = '1000';

    // Create the success image element
    const img = document.createElement('img');
    img.src = '/project_fruit/assets/img/logoBank/tichxanh.png'; // Replace with your image path
    img.style.width = '150px'; // Adjust size as needed
    img.style.height = '150px';
    img.style.animation = 'fadeInOut 1s ease-in-out';

    // Append the image to the overlay
    overlay.appendChild(img);

    // Add a click event to close the overlay when clicked
    overlay.onclick = function () {
        document.body.removeChild(overlay);
    };

    // Append the overlay to the body
    document.body.appendChild(overlay);
    setTimeout(() => {
        document.body.removeChild(overlay);
    }, 1000);
}

function goBack() {
    // Nếu đang ở tab "paymentTab", quay lại tab "infoTab"
    if (document.getElementById("paymentTab").classList.contains("active")) {
        switchTab("infoTab");
    } else {
        // Quay lại trang trước đó trong lịch sử trình duyệt
        window.history.back();
    }
}

function selectMethod(element, contentId) {
    document.querySelectorAll(".method-option").forEach(option => option.classList.remove("active"));
    element.classList.add("active");

    document.querySelectorAll(".method-content").forEach(content => content.classList.remove("active-content"));
    document.getElementById(contentId).classList.add("active-content");
}
function toggleAvailableDiscounts() {
    const discountList = document.getElementById("discountList");
    discountList.style.display = discountList.style.display === "block" ? "none" : "block";
}

function selectDiscount(discountType) {
    const discountInput = document.getElementById("discountCode");
    let discountAmount = 0;

    switch (discountType) {
        case "S-Student":
            discountInput.value = "S-Student";
            discountAmount = 400000;
            break;
        case "Freeship":
            discountInput.value = "Freeship";
            discountAmount = "Miễn phí";
            break;
        case "Discount10":
            discountInput.value = "Discount10";
            discountAmount = 200000; // Giả sử giảm tối đa 200.000đ
            break;
    }

    // Hiển thị giảm giá được chọn
    const discountElement = document.getElementById("discountAmount");
    discountElement.textContent = discountAmount === "Miễn phí" ? "Miễn phí" : `- ${discountAmount.toLocaleString()}đ`;

    // Tính toán lại tổng tiền
    const subtotal = 20590000; // Giả sử tiền hàng là 20.590.000đ
    const totalAmountElement = document.getElementById("totalAmount");

    if (typeof discountAmount === "number") {
        totalAmountElement.textContent = `${(subtotal - discountAmount).toLocaleString()}đ`;
    } else {
        totalAmountElement.textContent = `${subtotal.toLocaleString()}đ`;
    }

    // Ẩn danh sách mã giảm giá
    toggleAvailableDiscounts();
}

function applyDiscount() {
    const discountCode = document.getElementById("discountCode").value.trim();
    if (discountCode === "S-Student") {
        selectDiscount("S-Student");
    } else if (discountCode === "Freeship") {
        selectDiscount("Freeship");
    } else if (discountCode === "Discount10") {
        selectDiscount("Discount10");
    } else {
        alert("Mã giảm giá không hợp lệ!");
    }
}
function openOverlay() {
    document.getElementById("overlay").classList.remove("hidden");
}

function closeOverlay() {
    document.getElementById("overlay").classList.add("hidden");
}

function selectPaymentMethod(method, iconPath) {
    // Cập nhật tên phương thức thanh toán
    document.getElementById("selectedPaymentMethod").textContent = method;

    // Cập nhật icon phương thức thanh toán
    const selectedIcon = document.getElementById("selectedIcon").querySelector("img");
    if (selectedIcon) {
        selectedIcon.src = iconPath;
        selectedIcon.alt = method; // Cập nhật mô tả hình ảnh
    } else {
        console.error("Không tìm thấy thẻ <img> trong phần tử #selectedIcon.");
    }

    // Ẩn overlay
    closeOverlay();
}
function debugPaymentMethod() {
    const selectedIcon = document.getElementById("selectedIcon").querySelector("img");
    const selectedText = document.getElementById("selectedPaymentMethod").textContent;
    console.log("Icon hiện tại:", selectedIcon.src);
    console.log("Phương thức hiện tại:", selectedText);
}
function populateDeliveryInfo() {
    // Lấy thông tin từ form "Thông tin khách hàng"
    const customerName = document.getElementById("name").value.trim();
    const customerPhone = document.getElementById("phone").value.trim();
    const customerEmail = document.getElementById("email").value.trim();

    // Giao hàng tận nơi
    const deliveryProvince = document.getElementById("province").value.trim();
    const deliveryDistrict = document.getElementById("district").value.trim();
    const deliveryWard = document.getElementById("ward").value.trim();
    const deliveryAddress = document.getElementById("address").value.trim();

    // Hiển thị thông tin lên form
    document.getElementById("customerName").textContent = customerName;
    document.getElementById("customerPhone").textContent = customerPhone;
    document.getElementById("customerEmail").textContent = customerEmail;

    // Địa chỉ giao hàng
    document.getElementById("deliveryAddress").textContent =
        `${deliveryAddress}, ${deliveryWard}, ${deliveryDistrict}, ${deliveryProvince}`;

    // Thông tin người nhận
    document.getElementById("receiverInfo").textContent = `${customerName} - ${customerPhone}`;
}
function nextTab() {
    if (validateInfoTab()) {
        populateDeliveryInfo(); // Gọi hàm để cập nhật thông tin
        switchTab("paymentTab");
    }
}



