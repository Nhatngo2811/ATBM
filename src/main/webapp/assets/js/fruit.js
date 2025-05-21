// function addToCart(productId) {
//     fetch(`/project_fruit/add-cart?addToCartPid=${productId}`, {
//         method: 'GET',
//     })
//         .then(response => {
//             if (response.ok) {
//                 alert('Thêm vào giỏ hàng thành công!');
//             } else {
//                 alert('Không thể thêm vào giỏ hàng.');
//             }
//         })
//         .catch(error => console.error('Error:', error));
// }
function updateQuantity(productId, change) {
    const url = `/update-cart?pid=${productId}&change=${change}`;
    console.log("Fetching URL:", url);

    fetch(url, {
        method: 'GET',
    })
        .then(response => response.json())
        .then(data => {
            console.log("Response from server:", data);
        })
        .catch(error => {
            console.error("Fetch error:", error);
        });
}
function fetchSuggestions(keyword) {
    if (keyword.trim().length === 0) {
        const searchResults = document.getElementById("search-results");
        searchResults.style.display = "none";
        searchResults.innerHTML = "";
        return;
    }

    const xhr = new XMLHttpRequest();
    xhr.open("GET", "/project_fruit/search?keyword=" + encodeURIComponent(keyword), true);
    xhr.onload = function () {
        if (xhr.status === 200) {
            const products = JSON.parse(xhr.responseText); // Parse JSON từ backend
            let resultsHTML = "";

            if (products.length === 0) {
                resultsHTML = "<p>Không tìm thấy sản phẩm nào.</p>";
            } else {
                products.forEach(product => {
                    console.log("Product ID:", product.id_product);
                    resultsHTML += `
                            <div class="search-result-item" onclick="goToDetail(${product.id_product})">
                                 <img src="${product.imageUrl}" alt="${product.name}">
                            <div>
                            <span style="color: #1a1a1a">${product.name}</span>
                            <br>
                            <span style="color: #1a1a1a">${product.discountedPrice.toLocaleString()}đ</span>
                            </div>
                            </div>
                        `;
                });
            }

            const searchResults = document.getElementById("search-results");
            searchResults.innerHTML = resultsHTML;
            searchResults.style.display = "block"; // Hiển thị danh sách
        }
    };
    xhr.onerror = function () {
        console.error("Lỗi khi gửi yêu cầu AJAX.");
    };
    xhr.send();
}

// Chuyển hướng đến chi tiết sản phẩm
function goToDetail(id) {
    window.location.href = `/project_fruit/product-detail?pid=` + id;
}
// product
var swiper = new Swiper(".product-slider", {
    loop: true,
    spaceBetween: 20,
    autoplay: {
        delay: 10000,
        disableOnInteraction: false,
    },
    breakpoints: {
        0: {
            slidesPerView: 2,
        },
        768: {
            slidesPerView: 2,
        },
        1020: {
            slidesPerView: 5,
        },
    },
});
var swiper = new Swiper(".brand-slider", {
    loop: true,
    spaceBetween: 20,
    autoplay: {
        delay: 3000,
        disableOnInteraction: false,
    },
    navigation: {
        nextEl: '.button-next',
        prevEl: '.button-prev',
    },
    breakpoints: {
        0: {
            slidesPerView: 2,
        },
        768: {
            slidesPerView: 2,
        },
        1020: {
            slidesPerView: 6,
        },
    },
});

// ----------------------------------------------Sựkiện xem thêm sản phẩm
document.getElementById('view-more-btn').addEventListener('click', function(e) {
    e.preventDefault();
    const productSlider = document.getElementById('product-slider-1');
    if (productSlider.classList.contains('hidden')) {
        productSlider.classList.remove('hidden');
        productSlider.classList.add('visible');
        this.textContent = 'Ẩn sản phẩm';
    } else {
        productSlider.classList.remove('visible');
        productSlider.classList.add('hidden');
        this.textContent = 'Xem tất cả sản phẩm ưu đãi';
    }
});
// ---------------------------------------------sự khiện xem thêm trang hộp quà nguyệt cát
document.getElementById('view-more-btn').addEventListener('click', function(e) {
    e.preventDefault();
    const productSlider = document.getElementById('product-slider-2');
    if (productSlider.classList.contains('hidden')) {
        productSlider.classList.remove('hidden');
        productSlider.classList.add('visible');
        this.textContent = 'Ẩn sản phẩm';
    } else {
        productSlider.classList.remove('visible');
        productSlider.classList.add('hidden');
        this.textContent = 'Xem tất cả sản phẩm ưu đãi';
    }
});
// -------------------------------------------------------------------------------

// su kien trang web
function closeAllForms() {
    document.getElementById("loginForm").style.display = "none";
    document.getElementById("cartForm").style.display = "none";
    document.getElementById("branchSelection").style.display = "none";
    document.getElementById("userMenu").style.display = "none";
}

function setPositionRelativeToButton(form, button) {
    const buttonRect = button.getBoundingClientRect();
    const offset = 20; // Khoảng cách cụ thể giữa nút và form (20px)
    form.style.position = "fixed"; // Đặt form ở vị trí cố định trên màn hình
    form.style.top = `${buttonRect.bottom + offset}px`; // Đặt form bên dưới nút và cách 20px
    form.style.left = `${buttonRect.right - form.offsetWidth - 280}px`; // Căn chỉnh viền phải của form với viền phải của nút
}

function toggleLoginForm() {
    closeAllForms();
    const loginForm = document.getElementById("loginForm");
    const userMenu = document.getElementById("userMenu");
    const button = document.querySelector(".account");

    loginForm.style.display = loginForm.style.display === "none" || loginForm.style.display === "" ? "block" : "none";
    setPositionRelativeToButton(loginForm, button);
}

// Thêm sự kiện cho avatar để mở menu người dùng
function toggleUserMenu() {
    const userMenu = document.getElementById("userMenu");
    userMenu.style.display = userMenu.style.display === "none" || userMenu.style.display === "" ? "block" : "none";
}

// Sự kiện cho avatar
document.querySelector(".account").addEventListener("click", toggleLoginForm);
// Đăng xuất
function logout() {
    // Xóa thông tin người dùng từ giao diện
    const accountSection = document.querySelector(".account");
    const avatarImg = document.querySelector(".account .avatar");
    const userMenu = document.getElementById("userMenu");

    if (avatarImg) {
        avatarImg.remove(); // Xóa ảnh đại diện
    }

    // Đặt lại trạng thái mặc định (icon tài khoản)
    accountSection.classList.remove("logged-in");

    // Ẩn menu người dùng
    userMenu.style.display = "none";

    // Hiển thị lại form đăng nhập hoặc đặt lại giao diện icon tài khoản
    alert("Bạn đã đăng xuất thành công!");
    checkLoginStatus();
}
function checkLoginStatus() {
    const userMenu = document.getElementById("userMenu");
    const loginForm = document.getElementById("loginForm");
    const accountIcon = document.querySelector(".account");
    const avatar = document.querySelector(".avatar");
    const userNameDisplay = document.getElementById("userNameDisplay");

    // Example: Use sessionStorage for demo (can be replaced with backend session checking)
    const user = sessionStorage.getItem("user");  // Or use cookie/session for actual login check

    if (user) {
        // If user is logged in, show user menu and avatar, hide login form
        userMenu.style.display = "block";
        loginForm.style.display = "none"; // Hide login form
        accountIcon.style.display = "none"; // Hide account icon

        // Show user info (like avatar and name)
        avatar.style.display = "block";  // Display avatar
        userNameDisplay.textContent = user.email;  // Show username
    } else {
        // If user is not logged in, show login form
        userMenu.style.display = "none";
        loginForm.style.display = "block";
        accountIcon.style.display = "block";  // Show account icon
        avatar.style.display = "none";  // Hide avatar
    }
}
// Run checkLoginStatus to update interface on page load
window.onload = function () {
    checkLoginStatus();
};


// Gắn sự kiện cho nút đăng xuất
document.getElementById("logout").addEventListener("click", (event) => {
    event.preventDefault(); // Ngăn tải lại trang
    logout();
});
// Đóng form khi người dùng cuộn trang
window.addEventListener("scroll", function () {
    closeAllForms();
});
function toggleCartForm() {
    closeAllForms();
    const cartForm = document.getElementById("cartForm");
    const button = document.querySelector(".cart");
    setPositionRelativeToButton(cartForm, button);
    cartForm.style.display = cartForm.style.display === "none" || cartForm.style.display === "" ? "block" : "none";
}

function toggleBranchForm() {
    closeAllForms();
    const branchForm = document.getElementById("branchSelection");
    const button = document.querySelector(".delivery");
    setPositionRelativeToButton(branchForm, button);
    branchForm.style.display = branchForm.style.display === "none" || branchForm.style.display === "" ? "block" : "none";
}

document.querySelector(".account").addEventListener("click", toggleLoginForm);
document.querySelector(".cart").addEventListener("click", toggleCartForm);
document.querySelector(".delivery").addEventListener("click", toggleBranchForm);
document.querySelector(".account").addEventListener("click", toggleLoginForm);

window.onclick = function (event) {
    if (!event.target.closest('.login-form') && !event.target.closest('.cart-form') &&
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

// cap nhat thoi gian dem nguoc
const countdownDate = new Date().getTime() + 3 * 60 * 60 * 1000; // 3 giờ tính từ bây giờ

function updateTimer() {
    const now = new Date().getTime();
    const distance = countdownDate - now;

    // Tính giờ, phút, giây
    const hours = Math.floor(
        (distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)
    );
    const minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
    const seconds = Math.floor((distance % (1000 * 60)) / 1000);

    // Cập nhật nội dung hiển thị
    document.querySelector(
        ".timer div:nth-child(1)"
    ).innerHTML = `${hours}<br>Giờ`;
    document.querySelector(
        ".timer div:nth-child(2)"
    ).innerHTML = `${minutes}<br>Phút`;
    document.querySelector(
        ".timer div:nth-child(3)"
    ).innerHTML = `${seconds}<br>Giây`;

    // Kiểm tra khi hết thời gian
    if (distance < 0) {
        clearInterval(countdownInterval);
        document.querySelector(".timer").innerHTML = "Hết hạn";
    }
}

// Cập nhật đồng hồ mỗi giây
const countdownInterval = setInterval(updateTimer, 1000);




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
window.addEventListener("click", function (event) {
    const sidebarMenu = document.getElementById("sidebarMenu");
    const isClickInsideMenu = event.target.closest("#sidebarMenu");
    const isClickMenuToggle = event.target.closest("#menuToggle");

    // Kiểm tra nếu người dùng nhấp ra ngoài menu và không nhấn vào menuToggle
    if (!isClickInsideMenu && !isClickMenuToggle && sidebarMenu.classList.contains("active")) {
        closeSidebarMenu();
    }
});




let lastScrollTop = 0;  // Biến lưu vị trí cuộn cuối cùng
const menu = document.querySelector('.menu-bar');  // Lấy phần tử menu thanh header

window.addEventListener("scroll", function () {
    const currentScroll = window.pageYOffset || document.documentElement.scrollTop;  // Lấy vị trí cuộn hiện tại

    if (currentScroll > lastScrollTop) {
        // Khi cuộn xuống, ẩn menu
        menu.classList.add("scrolled-down");
        menu.classList.remove("scrolled-up");
    } else {
        // Khi cuộn lên, hiện menu
        menu.classList.add("scrolled-up");
        menu.classList.remove("scrolled-down");
    }

    lastScrollTop = currentScroll <= 0 ? 0 : currentScroll;  // Cập nhật vị trí cuộn cuối cùng
});

// loi ngo search
window.onload = function () {
    document.querySelector(".search input").value = ""; // Đảm bảo ô input bên trong thẻ .search được làm rỗng
};

// chi tiet san pham
let currentIndex = 0;
const images = document.querySelectorAll('.carousel-images img');
const thumbnails = document.querySelectorAll('.thumbnails .thumbnail');
const intervalTime = 5000;
let slideInterval;

// Show image based on index
function showImage(index) {
    images.forEach((img, i) => {
        img.classList.toggle('active', i === index);
        thumbnails[i].classList.toggle('active-thumbnail', i === index);
    });
    currentIndex = index;
}

// Next image
function nextImage() {
    const nextIndex = (currentIndex + 1) % images.length;
    showImage(nextIndex);
}

// Previous image
function prevImage() {
    const prevIndex = (currentIndex - 1 + images.length) % images.length;
    showImage(prevIndex);
}

// Thumbnail click event
thumbnails.forEach((thumbnail, index) => {
    thumbnail.addEventListener('click', () => {
        showImage(index);
        resetInterval();
    });
});

// Controls click events
document.querySelector('.next').addEventListener('click', () => {
    nextImage();
    resetInterval();
});
document.querySelector('.prev').addEventListener('click', () => {
    prevImage();
    resetInterval();
});

// Automatic slide change
function startInterval() {
    slideInterval = setInterval(nextImage, intervalTime);
}

// Reset interval when manually changing image
function resetInterval() {
    clearInterval(slideInterval);
    startInterval();
}

// Initialize
showImage(currentIndex);
startInterval();
// Get the input field and buttons
const minusButton = document.querySelector('.minus');
const plusButton = document.querySelector('.plus');
const quantityInput = document.querySelector('.quantity input');

// Add event listeners to the buttons
minusButton.addEventListener('click', () => {
    let currentValue = parseInt(quantityInput.value) || 0;
    if (currentValue > 1) {
        quantityInput.value = currentValue - 1;
    }
});

plusButton.addEventListener('click', () => {
    let currentValue = parseInt(quantityInput.value) || 0;
    quantityInput.value = currentValue + 1;
});
// thêm gior hang
let cart = [];

// Hiển thị giỏ hàng
function toggleCart() {
    const cartForm = document.getElementById("cartForm");
    cartForm.style.display = cartForm.style.display === "none" || cartForm.style.display === "" ? "block" : "none";
}

// Thêm sản phẩm vào giỏ hàng
function addToCart(name, price, image) {
    const existingItem = cart.find(item => item.name === name);

    if (existingItem) {
        existingItem.quantity++;
    } else {
        cart.push({ name, price, image, quantity: 1 });
    }

    updateCart();
}

// Cập nhật giỏ hàng
function updateCart() {
    const cartContent = document.querySelector(".cart-content");
    const totalAmount = document.querySelector(".total-amount");

    cartContent.innerHTML = "";
    let total = 0;

    cart.forEach(item => {
        total += item.price * item.quantity;

        const cartItem = document.createElement("div");
        cartItem.classList.add("cart-item");
        cartItem.innerHTML = `
      <img src="${item.image}" alt="${item.name}">
      <div>
        <h4>${item.name}</h4>
        <p>${item.price.toLocaleString()}₫</p>
        <div>
          <button onclick="updateQuantity('${item.name}', -1)">-</button>
          <span>${item.quantity}</span>
          <button onclick="updateQuantity('${item.name}', 1)">+</button>
        </div>
      </div>
    `;
        cartContent.appendChild(cartItem);
    });

    totalAmount.textContent = `${total.toLocaleString()}₫`;

    // Cập nhật trạng thái khi giỏ hàng trống
    if (cart.length === 0) {
        cartContent.innerHTML = `<i class="fa-solid fa-cart-shopping cart-icon"></i><p>Hiện chưa có sản phẩm</p>`;
    }
}

// Cập nhật số lượng sản phẩm trong giỏ hàng
function updateQuantity(name, change) {
    const item = cart.find(item => item.name === name);
    if (item) {
        item.quantity += change;
        if (item.quantity <= 0) {
            cart = cart.filter(cartItem => cartItem.name !== name);
        }
        updateCart();
    }
}
const sidebar = document.querySelector('.sidebar-menu');
const toggleButton = document.querySelector('.toggle-button');

toggleButton.addEventListener('click', () => {
    sidebar.classList.toggle('active');
});
function showForgotPasswordForm() {
    // Ẩn form đăng nhập và hiển thị form khôi phục mật khẩu
    document.getElementById("loginForm").style.display = "none";  // Ẩn form đăng nhập
    document.getElementById("forgotPasswordForm").style.display = "block";  // Hiển thị form khôi phục mật khẩu
    document.getElementById("overlay").style.display = "block";  // Hiển thị overlay
}

function showLoginForm() {
    // Ẩn form khôi phục mật khẩu và hiển thị form đăng nhập
    document.getElementById("forgotPasswordForm").style.display = "none";  // Ẩn form khôi phục mật khẩu
    document.getElementById("loginForm").style.display = "block";  // Hiển thị form đăng nhập
    document.getElementById("overlay").style.display = "none";  // Ẩn overlay
}

function login() {
    // Xử lý đăng nhập (thêm logic của bạn ở đây)
    alert('Đăng nhập thành công!');
}

function resetPassword() {
    // Xử lý khôi phục mật khẩu (thêm logic của bạn ở đây)
    alert('Yêu cầu khôi phục mật khẩu đã được gửi!');
}























