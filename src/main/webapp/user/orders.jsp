<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <!-- link swiper -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.css"/>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css">
    <!-- link style css -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/detail.css">
    <!-- Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"/>
    <!-- link logo anh -->
    <link rel="icon" href="${pageContext.request.contextPath}/assets/img/logoBank/logoweb.png" type="image/x-icon">
    <title>Selling Fruit</title>
</head>


<!-- header section stars -->
<header>
    <div class="header-container">
        <div class="left">
            <div class="menu">
          <span id="menuToggle" onclick="toggleSidebarMenu()">
            <i id="menuIcon" class="fa-solid fa-bars"></i></span>
            </div>
            <div class="logo">
                <a href="/index.jsp">
                    <h1>Vitamin<span>FRUIT</span></h1>
                </a>
            </div>
            <div class="search" style="position: relative;">
                <form action="${pageContext.request.contextPath}/search-result" method="GET" id="searchForm">
                    <input type="text" id="search-input" placeholder="Tìm kiếm sản phẩm..." name="keyword"
                           oninput="fetchSuggestions(this.value)">
                    <button type="submit" class="search-btn">
                        <i class="fa-solid fa-search"></i>
                    </button>
                </form>
                <div id="search-results" class="search-results"></div>
            </div>
        </div>
        <div class="center">
            <div class="delivery" onclick="toggleBranchSelection()">
                <span>Giao hoặc đến lấy tại ▼</span>
                <p>Chi nhánh 1 - 43 Nguyễn Thái Học...</p>
            </div>
        </div>
        <div class="right">
            <div class="hotline">
                <i class="fas fa-phone"></i>
                <span>Hotline: 0865660775</span>
            </div>
            <div class="cart">
                <a href="${pageContext.request.contextPath}/show-cart" style="color: white">
                    <i class="fa-solid fa-cart-shopping"></i>
                    <span>Giỏ hàng</span>
                    <span class="cart-badge">${sessionScope.cart != null ? sessionScope.cart.getTotalQuantity() : 0}</span>
                </a>
            </div>
            <div class="account">
                <!-- Kiểm tra nếu người dùng đã đăng nhập -->
                <c:if test="${not empty sessionScope.user}">
                    <!-- Nếu người dùng đã đăng nhập, hiển thị avatar và thông tin -->
                    <a href="${pageContext.request.contextPath}/user/user.jsp">
                        <img src="${pageContext.request.contextPath}/assets/img/anhdaidien.jpg" alt="Avatar"
                             class="avatar" onclick="toggleUserMenu()">
                    </a>
                </c:if>
                <c:if test="${empty sessionScope.user}">
                    <!-- Nếu chưa đăng nhập, hiển thị icon tài khoản -->
                    <a href="${pageContext.request.contextPath}/user/login.jsp" style="color: white">
                        <i class="fa-solid fa-user"></i>
                        <span>Tài khoản</span>
                    </a>
                </c:if>
            </div>
        </div>
    </div>
    <!-- Branch Selection Form -->
    <div class="branch-selection" id="branchSelection">
        <h2>KHU VỰC MUA HÀNG</h2>
        <div class="form-group">
            <label for="city">Tỉnh/Thành</label>
            <select id="city">
                <option>- Chọn Thành phố/ tỉnh -</option>
                <option>Hồ Chí Minh</option>
                <!-- Thêm các tỉnh/thành khác nếu cần -->
            </select>
        </div>
        <div class="form-group">
            <label for="district">Quận/Huyện</label>
            <select id="district">
                <option>- Chọn Quận/Huyện -</option>
                <option>- Thủ Đức -</option>
                <option>- Quận 1 -</option>
                <!-- Thêm các quận/huyện khác nếu cần -->
            </select>
        </div>

        <div class="selected-branch">
            <p>Giao hoặc đến lấy tại:</p>
            <div class="branch-info-highlight">
                <p><strong>Chi nhánh 1 - 43 Nguyễn Thái Học, Phường Cầu Ông Lãnh, Quận 1</strong></p>
            </div>
        </div>

        <p>Chọn cửa hàng gần bạn nhất để tối ưu chi phí giao hàng. Hoặc đến lấy hàng</p>

        <div class="branch-list">
            <div class="branch">
                <p><i class="fas fa-map-marker-alt"></i> Chi nhánh 1</p>
                <p>43 Nguyễn Thái Học, Phường Cầu Ông Lãnh, Quận 1</p>
            </div>
            <div class="branch">
                <p><i class="fas fa-map-marker-alt"></i> Chi nhánh 2</p>
                <p>SAV.7-00.01, Tầng trệt Tháp 7, The Sun Avenue, 28 Mai Chí Thọ, phường An Phú, thành phố Thủ Đức,
                    Phường An
                    Phú, Thành phố Thủ Đức</p>
            </div>
        </div>
    </div>
    <!-- User Menu (ẩn khi chưa đăng nhập) -->
    <%--    <div class="user-menu" id="userMenu" style="display: none;">--%>
    <%--        <p>Xin chào, <span id="userNameDisplay">${sessionScope.user.email}</span></p>--%>
    <%--        <ul>--%>
    <%--            <li><a href="${pageContext.request.contextPath}/user/user.jsp"><i class="fas fa-box"></i> Thông tin cá nhân</a></li>--%>
    <%--            <li><a href="#"><i class="fas fa-eye"></i> Đã xem gần đây</a></li>--%>
    <%--            <li><a href="${pageContext.request.contextPath}/logout"><i class="fas fa-sign-out-alt"></i> Đăng xuất</a></li>--%>
    <%--        </ul>--%>
    <%--    </div>--%>
</header>
<!-- Menu Bar dưới Header -->
<!-- Menu Bar dưới Header -->
<nav class="menu-bar">
    <ul>
        <li><a href="/project_fruit/home" onclick="setActive(this)"><i class="fas fa-home"></i> Trang chủ</a></li>

        <li><a href="/project_fruit/home?category=traicayhomnay" onclick="setActive(this)">Trái ngon hôm nay</a></li>
        <li><a href="/project_fruit/home?category=traicayvietnam" onclick="setActive(this)">Trái cây Việt Nam</a></li>
        <li><a href="/project_fruit/home?category=traicaynhapkhau" onclick="setActive(this)">Trái cây nhập khẩu</a></li>
        <li><a href="/project_fruit/home?category=traicaycatsan" onclick="setActive(this)">Trái cây cắt sẵn</a></li>
        <li><a href="/project_fruit/home?category=quatangtraicay" onclick="setActive(this)">Quà tặng trái cây</a></li>
        <li><a href="/project_fruit/home?category=hopquanguyencat" onclick="setActive(this)">Hộp quà Nguyệt Cát</a></li>
        <li><a href="/project_fruit/home?category=traicaysaykho" onclick="setActive(this)">Trái cây sấy khô</a></li>
        <li><a href="/project_fruit/home?category=muttraicay" onclick="setActive(this)">Mứt trái cây</a></li>
        <li><a href="/project_fruit/user/contact.jsp" onclick="setActive(this)">Liên hệ</a></li>
    </ul>
</nav>
<%--<!-- Menu docj ban đầu ẩn , chỉ xuất hiện khi ấn icon -->--%>
<nav class="sidebar-menu" id="sidebarMenu">
    <ul>
        <li><a href="/project_fruit/home" onclick="setActive(this)"><i class="fas fa-home"></i> Trang chủ</a></li>
        <li><a href="/project_fruit/home?category=traicayhomnay" onclick="setActive(this)">Trái ngon hôm nay</a></li>
        <li><a href="/project_fruit/home?category=traicayvietnam" onclick="setActive(this)">Trái cây Việt Nam</a></li>
        <li><a href="/project_fruit/home?category=traicaynhapkhau" onclick="setActive(this)">Trái cây nhập khẩu</a></li>
        <li><a href="/project_fruit/home?category=traicaycatsan" onclick="setActive(this)">Trái cây cắt sẵn</a></li>
        <li><a href="/project_fruit/home?category=quatangtraicay" onclick="setActive(this)">Quà tặng trái cây</a></li>
        <li><a href="/project_fruit/home?category=hopquanguyencat" onclick="setActive(this)">Hộp quà Nguyệt Cát</a></li>
        <li><a href="/project_fruit/home?category=traicaysaykho" onclick="setActive(this)">Trái cây sấy khô</a></li>
        <li><a href="/project_fruit/home?category=muttraicay" onclick="setActive(this)">Mứt trái cây</a></li>
        <li><a href="/project_fruit/user/contact.jsp" onclick="setActive(this)">Liên hệ</a></li>
    </ul>
</nav>
<%--<!-- header section ends -->--%>
<title>Quản lý đơn hàng</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"/>
<style>
    .orders-container {
        max-width: 1200px;
        margin: 20px auto;
        padding: 20px;
        font-family: Arial, sans-serif;
    }

    .order-card {
        background: white;
        border: 1px solid #ddd;
        border-radius: 8px;
        padding: 15px;
        margin-bottom: 20px;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    }

    .order-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        border-bottom: 1px solid #eee;
        padding-bottom: 10px;
        margin-bottom: 15px;
        font-size: 18px;
    }

    .order-id {
        font-weight: bold;
        color: #333;
    }

    .order-status {
        padding: 5px 10px;
        border-radius: 15px;
        font-size: 14px;
    }

    .status-pending {
        background-color: #fff3cd;
        color: #856404;
    }

    .status-confirmed {
        background-color: #cce5ff;
        color: #004085;
    }

    .status-delivered {
        background-color: #d4edda;
        color: #155724;
    }

    .order-details {
        margin-bottom: 15px;
        font-size: 15px;
    }

    .order-details p {
        margin: 5px 0;
        color: #666;
    }

    .order-actions {
        display: flex;
        gap: 10px;
    }

    .btn {
        padding: 8px 15px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        font-size: 14px;
        transition: background-color 0.3s;
    }

    .btn-view {
        background-color: #007bff;
        color: white;
    }

    .btn-print {
        background-color: #28a745;
        color: white;
    }

    .btn-verifyInvoice {
        background-color: #ffa700;
        color: white;
    }

    .btn:hover {
        opacity: 0.9;
    }

    .empty-orders {
        text-align: center;
        padding: 50px;
        color: #666;
    }
</style>
</head>
<body>
<!-- Include header -->
<jsp:include page="/includes/header.jsp"/>

<div class="orders-container">
    <h1>Quản lý đơn hàng</h1>

    <c:if test="${empty userInvoices}">
        <div class="empty-orders">
            <i class="fas fa-box-open" style="font-size: 48px; color: #ddd;"></i>
            <p>Bạn chưa có đơn hàng nào</p>
        </div>
    </c:if>

    <c:forEach var="invoice" items="${userInvoices}">
        <div class="order-card">
            <div class="order-header">
                <span class="order-id">Đơn hàng #${invoice.idInvoice}</span>
                <c:choose>
                    <c:when test="${invoice.status eq 'Đang chờ xác thực'}">
                        <span class="order-status status-pending">${invoice.status}</span>
                    </c:when>
                    <c:when test="${invoice.status eq 'Đã xác thực'}">
                        <span class="order-status status-confirmed">${invoice.status}</span>
                    </c:when>
                    <c:otherwise>
                        <span class="order-status status-delivered">${invoice.status}</span>
                    </c:otherwise>
                </c:choose>
            </div>

            <div class="order-details">
                <p><strong>Ngày đặt:</strong> <fmt:formatDate value="${invoice.createDate}" pattern="dd/MM/yyyy HH:mm"/>
                </p>
                <p><strong>Người nhận:</strong> ${invoice.receiverName}</p>
                <p><strong>Địa chỉ:</strong> ${invoice.addressFull}</p>
                <p><strong>Tổng tiền:</strong> <fmt:formatNumber value="${invoice.totalPrice}" pattern="#,###"/> đ</p>
            </div>

            <div class="order-actions">
                <button class="btn btn-view" onclick="viewOrderDetails(${invoice.idInvoice})">
                    <i class="fas fa-eye"></i> Xem chi tiết
                </button>
                <button class="btn btn-print"
                        onclick="window.location.href='${pageContext.request.contextPath}/user/view-invoice?id=${invoice.idInvoice}'">
                    <i class="fas fa-print"></i> In hóa đơn
                </button>
                <button class="btn btn-verifyInvoice" data-orderid="${invoice.idInvoice}">Xác Thực Đơn Hàng</button>
                <div class="order-verification-overlay"></div>
                <div class="order-verification-popup">
                    <label>Nhập Chữ kí để xác thực đơn hàng #${invoice.idInvoice}:</label>
                    <input type="text" class="input-verify-order" style="margin: 16px 0; font-size: 18px;"/>
                    <p class="order-hash" style="font-size: 12px; text-transform: none">Mã đơn hàng:</p>
                    <div class="verify-btn-row">
                        <button class="btn btn-primary btn-order-verify-submit" style="font-size: 18px;">Xác nhận
                        </button>
                    </div>
                </div>
                <style>
                    .verify-btn-row {
                        display: flex;
                        justify-content: center;
                        align-items: center;
                        margin-top: 12px;
                    }

                    .order-verification-overlay {
                        position: fixed;
                        top: 0;
                        left: 0;
                        right: 0;
                        bottom: 0;
                        background: rgba(0, 0, 0, 0.45);
                        z-index: 9999;
                        opacity: 0;
                        pointer-events: none;
                        transition: opacity 0.25s;
                    }

                    .order-verification-overlay.active {
                        opacity: 1;
                        pointer-events: auto;
                    }

                    .order-verification-popup {
                        position: fixed;
                        z-index: 10000;
                        min-width: 400px;
                        max-width: 95vw;
                        background: #fff;
                        color: #222;
                        border-radius: 12px;
                        box-shadow: 0 6px 32px rgba(0, 0, 0, 0.2);
                        padding: 32px 28px 24px 28px;
                        top: 50%;
                        left: 50%;
                        opacity: 0;
                        pointer-events: none;
                        transform: translate(-50%, -50%) scale(0.95);
                        border: 2px solid #1976D2;
                        display: block;
                        transition: opacity 0.3s, transform 0.3s;
                    }

                    .order-verification-popup.active {
                        opacity: 1;
                        pointer-events: auto;
                        transform: translate(-50%, -50%) scale(1);
                    }

                    .order-verification-popup label {
                        font-weight: 600;
                        margin-bottom: 14px;
                        display: block;
                        font-size: 20px;
                    }

                    .order-verification-popup input {
                        width: 100%;
                        padding: 10px 16px;
                        border-radius: 7px;
                        border: 1.5px solid #aaa;
                        margin-bottom: 20px;
                        font-size: 18px;
                    }

                    .order-verification-popup .btn-order-verify-submit {
                        background: #1976d2;
                        color: #fff;
                        border: none;
                        border-radius: 6px;
                        padding: 11px 22px;
                        cursor: pointer;
                        font-size: 18px;
                        font-weight: 500;
                        margin-top: 5px;
                        text-align: center;
                    }
                </style>
                <script>
                    document.addEventListener('DOMContentLoaded', function () {
                        document.querySelectorAll('.btn-verifyInvoice').forEach(function (btn) {
                            btn.addEventListener('click', function (event) {
                                event.stopPropagation();
                                // Ẩn các popup và overlay khác
                                document.querySelectorAll('.order-verification-popup').forEach(box => box.classList.remove('active'));
                                document.querySelectorAll('.order-verification-overlay').forEach(ovl => ovl.classList.remove('active'));

                                // Hiện overlay và popup đúng chỗ
                                const popup = btn.nextElementSibling.nextElementSibling; // overlay -> popup
                                const overlay = btn.nextElementSibling;
                                if (
                                    popup && overlay &&
                                    popup.classList.contains('order-verification-popup') &&
                                    overlay.classList.contains('order-verification-overlay')
                                ) {
                                    popup.classList.add('active');
                                    overlay.classList.add('active');
                                    const input = popup.querySelector('input');
                                    if (input) input.focus();

                                    // Lấy orderId từ data-orderid của button
                                    const orderId = btn.getAttribute('data-orderid');
                                    // Tìm thẻ p hiển thị hash trong popup
                                    const hashP = popup.querySelector('.order-hash');
                                    if (orderId && hashP) {
                                        hashP.innerText = 'Đang lấy mã hash...';
                                        fetch('/project_fruit/hash-invoice?id=' + orderId)
                                            .then(res => res.json())
                                            .then(data => {
                                                if (data.success) {
                                                    // Hiện vừa id vừa hash (hoặc chỉ hash tuỳ ý)
                                                    hashP.innerText = 'Mã đơn hàng #' + orderId + ': ' + data.hash;
                                                } else {
                                                    hashP.innerText = 'Lỗi: ' + data.message;
                                                }
                                            })
                                            .catch(() => {
                                                hashP.innerText = 'Lỗi khi lấy mã hash!';
                                            });
                                    }
                                }
                            });
                        });

                        // Ẩn khi bấm ra ngoài hoặc bấm lên overlay
                        document.addEventListener('click', function (event) {
                            document.querySelectorAll('.order-verification-popup').forEach(box => {
                                if (!box.contains(event.target)) box.classList.remove('active');
                            });
                            document.querySelectorAll('.order-verification-overlay').forEach(ovl => {
                                if (event.target === ovl) ovl.classList.remove('active');
                            });
                        });

                        // Khi overlay mất active, tự ẩn popup
                        document.querySelectorAll('.order-verification-overlay').forEach(ovl => {
                            ovl.addEventListener('transitionend', function () {
                                if (!ovl.classList.contains('active')) {
                                    const popup = ovl.nextElementSibling;
                                    if (popup && popup.classList.contains('order-verification-popup')) {
                                        popup.classList.remove('active');
                                    }
                                }
                            });
                        });
                    });

                </script>
            </div>
        </div>
    </c:forEach>
</div>

<script>
    function viewOrderDetails(orderId) {
        // Thêm logic xem chi tiết đơn hàng ở đây
        alert('Xem chi tiết đơn hàng #' + orderId);
    }
</script>


<!-- footer section star -------------------------------------------------------------->
<section class=" footer
                ">
    <div class="box-container">
        <div class="box">
            <h3>VitaminFruit</h3>
            <p>
                "Chăm sóc sức khỏe bạn từ thiên nhiên! VitaminFruit – nguồn dinh dưỡng hoàn hảo cho cơ thể
                và tâm trí."
            </p>
            <div class="share">
                <a href="#" class="fab fa-facebook-f"></a>
                <a href="#" class="fab fa-twitter"></a>
                <a href="#" class="fab fa-instagram"></a>
                <a href="#" class="fab fa-youtube"></a>
                <a href="#" class="fab fa-tiktok"></a>
            </div>
        </div>
        <div class="box">
            <h3>liên hệ</h3>
            <p>Liên hệ chúng tôi tại đây :</p>
            <a href="#" class="links"><i class="fas fa-phone"></i>+334 286 049</a>
            <a href="#" class="links"><i class="fas fa-phone"></i>+263 463 463</a>

            <a href="#" class="links"><i class="fas fa-envelope"></i>nhom55ltw@gmail.com</a>


            <a href="#" class="links"><i class="fas fa-map-marker-alt"></i>VQCR+GP6, khu phố 6, Thủ Đức,
                Hồ Chí Minh</a>
        </div>
        <div class="box">

            <h3>Hỗ trợ khách hàng</h3>
            <p>
                Luôn hổ trợ khách hàng mọi lúc mọi nơi.
            </p>
            <a href="#home" class="links"><i class="fas fa-arrow-right"></i>Chính sách thương hiệu</a>
            <a href="#features" class="links"><i class="fas fa-arrow-right"></i>Chính sách thành viên</a>
            <a href="#products" class="links"><i class="fas fa-arrow-right"></i>Chính sách kiểm hàng</a>
            <a href="#categories" class="links"><i class="fas fa-arrow-right"></i>Chính sách giao hàng</a>
            <a href="#review" class="links"><i class="fas fa-arrow-right"></i>Chính sách thanh toán</a>
            <a href="#blogs" class="links"><i class="fas fa-arrow-right"></i>Chính sách bảo mật</a>
        </div>
        <div class="box">
            <h3>Đơn vị vận chuyển</h3>
            <p>"Chúng tôi sử dụng các đơn vị vận chuyển uy tín như Grab, Giao Hàng Tiết Kiệm, VNPost và
                nhiều đơn vị
                khác."</p>
            <div class="shipping-brands">
                <img src="../assets/img/logoBank/grab.jpg" alt="Grab"/>
                <img src="../assets/img/logoBank/giaohangtietkiem.png" alt="Giao Hàng Tiết Kiệm"/>
                <img src="../assets/img/logoBank/vnpost.webp" alt="VNPost"/>
            </div>
        </div>
    </div>
    <div class="credit">Copyright © 2024 <span>Nhom 55 - Trái Cây Chất Lượng Cao</span></div>
</section>
<!-- footer section end -->

<script src="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.js"></script>

<script src="${pageContext.request.contextPath}/assets/js/fruit.js" defer></script>
<script src="${pageContext.request.contextPath}/assets/js/login.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script>
    document.addEventListener("DOMContentLoaded", function () {
        const minusBtn = document.querySelector(".minus");
        const plusBtn = document.querySelector(".plus");
        const quantityInput = document.getElementById("quantity");
        const cartQuantity = document.getElementById("cartQuantity");

        minusBtn.addEventListener("click", function () {
            let value = parseInt(quantityInput.value);
            if (value > 1) {
                quantityInput.value = value - 1;
                cartQuantity.value = quantityInput.value;
            }
        });

        plusBtn.addEventListener("click", function () {
            let value = parseInt(quantityInput.value);
            quantityInput.value = value + 1;
            cartQuantity.value = quantityInput.value;
        });

        quantityInput.addEventListener("input", function () {
            cartQuantity.value = quantityInput.value;
        });
    });
</script>
</body>

</html>