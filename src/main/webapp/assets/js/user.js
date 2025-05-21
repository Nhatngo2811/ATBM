// su kien trang web
function closeAllForms() {
    document.getElementById("loginForm").style.display = "none";
    document.getElementById("cartForm").style.display = "none";
    document.getElementById("branchSelection").style.display = "none";
}

function setPositionRelativeToButton(form, button) {
    const buttonRect = button.getBoundingClientRect();
    const offset = 20;
    form.style.position = "fixed";
    form.style.top = `${buttonRect.bottom + offset}px`;
    form.style.left = `${buttonRect.right - form.offsetWidth - 280}px`;
}


function toggleBranchForm() {
    closeAllForms();
    const branchForm = document.getElementById("branchSelection");
    const button = document.querySelector(".delivery");
    setPositionRelativeToButton(branchForm, button);
    branchForm.style.display = branchForm.style.display === "none" || branchForm.style.display === "" ? "block" : "none";
}

document.querySelector(".account").addEventListener("click", toggleLoginForm);
document.querySelector(".delivery").addEventListener("click", toggleBranchForm);

window.onclick = function (event) {
    if (!event.target.closest('.login-form') &&
        !event.target.closest('.branch-selection') &&
        !event.target.closest('.account') && !event.target.closest('.cart') &&
        !event.target.closest('.delivery')) {
        closeAllForms();
    }
};

// Đóng form khi người dùng cuộn trang
window.addEventListener("scroll", function () {
    closeAllForms();
});


// Hàm để đặt mục được nhấn là active
function setActive(element) {
    // Loại bỏ lớp "active" khỏi tất cả các mục menu
    document.querySelectorAll(".menu-bar li").forEach(item => {
        item.classList.remove("active");
    });

    // Thêm lớp "active" vào mục cha <li> của phần tử <a> được nhấn
    element.closest("li").classList.add("active");
}


// cap nhat menu ben trai
function closeSidebarMenu() {
    const sidebarMenu = document.getElementById("sidebarMenu");
    const menuIcon = document.getElementById("menuIcon");
    if (sidebarMenu && sidebarMenu.classList.contains("active")) {
        sidebarMenu.classList.remove("active"); // Đóng menu
        if (menuIcon) {
            menuIcon.classList.remove("fa-x");
            menuIcon.classList.add("fa-bars");
        }
    }
}

// Hàm mở/đóng menu sidebar và thay đổi icon
function toggleSidebarMenu() {
    const sidebarMenu = document.getElementById("sidebarMenu");
    const menuIcon = document.getElementById("menuIcon");

    // Kiểm tra trạng thái của sidebar menu
    if (sidebarMenu.classList.contains("active")) {
        // Nếu menu đang mở, đóng menu và đổi icon về "fa-bars"
        closeSidebarMenu();
    } else {
        // Nếu menu đang đóng, mở menu và đổi icon thành "fa-x"
        sidebarMenu.classList.add("active");
        menuIcon.classList.remove("fa-bars");
        menuIcon.classList.add("fa-x");
    }
}

// Đóng menu khi nhấp ra ngoài
window.addEventListener("click", function(event) {
    const sidebarMenu = document.getElementById("sidebarMenu");
    const isClickInsideMenu = event.target.closest("#sidebarMenu");
    const isClickMenuToggle = event.target.closest("#menuToggle");

    // Kiểm tra nếu người dùng nhấp ra ngoài menu và không nhấn vào menuToggle
    if (!isClickInsideMenu && !isClickMenuToggle && sidebarMenu.classList.contains("active")) {
        closeSidebarMenu();
    }
});




// cuộn màn hình xuống thì ẩn menu lướt lên thì hiện menu
let lastScrollTop = 0;

window.addEventListener("scroll", function () {
    const currentScroll = window.pageYOffset || document.documentElement.scrollTop;

    if (currentScroll > lastScrollTop) {
        // Cuộn xuống
        document.body.classList.remove("scrolled-up");
        document.body.classList.add("scrolled-down");
    } else {
        // Cuộn lên
        document.body.classList.remove("scrolled-down");
        document.body.classList.add("scrolled-up");
    }

    lastScrollTop = currentScroll <= 0 ? 0 : currentScroll; // Đảm bảo giá trị không bị âm
});

// Chuyển đổi giữa các section trong sidebar
function showSection(sectionId, element) {
    // Ẩn tất cả các section
    document.querySelectorAll('.section').forEach(section => {
        section.classList.remove('active');
    });
    // Hiển thị section được chọn
    document.getElementById(sectionId).classList.add('active');

    // Cập nhật trạng thái active của sidebar
    document.querySelectorAll('.sidebar ul li a').forEach(link => {
        link.classList.remove('active');
    });
    element.classList.add('active');
}

// Chuyển đổi giữa các tab trong phần "Quản lý đơn hàng"
function showTab(tabId) {
    // Ẩn tất cả các tab-pane
    document.querySelectorAll('.tab-pane').forEach(tab => {
        tab.classList.remove('active');
    });
    // Hiển thị tab-pane được chọn
    document.getElementById(tabId).classList.add('active');

    // Cập nhật trạng thái active của các tab
    document.querySelectorAll('.tab').forEach(tab => {
        tab.classList.remove('active');
    });
    document.querySelector(`.tab[onclick="showTab('${tabId}')"]`).classList.add('active');
}

// Hàm giả lập tìm kiếm đơn hàng
function searchOrders() {
    const searchInput = document.getElementById('orderSearchInput').value.trim();
    if (searchInput === '') {
        alert('Vui lòng nhập mã đơn hàng để tìm kiếm.');
    } else {
        alert(`Đang tìm kiếm đơn hàng với mã: ${searchInput}`);
        // Thêm logic tìm kiếm thực tế ở đây
    }
}
// Sự kiện đăng xuất
document.getElementById("logout").addEventListener("click", function(event) {
    event.preventDefault();  // Ngừng hành động mặc định của link

    // Gửi yêu cầu tới servlet để đăng xuất
    window.location.href = "/logout";  // Đảm bảo URL này tương ứng với LogoutController
});


