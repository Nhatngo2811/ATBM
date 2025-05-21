<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "f" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <!-- link swiper -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.css" />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css">
    <!-- link style css -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/user.css">
    <!-- Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" />
    <!-- link logo anh -->
    <link rel="icon" href="../assets/img/logoBank/logoweb.png" type="image/x-icon">
    <title>Selling Fruit</title>
    <style>
        .product-list {
            display: flex;
            flex-wrap: wrap;
            gap: 20px;
            margin-top: 20px;
        }

        .product-card {
            width: 180px;
            background: #fff;
            border: 1px solid #eee;
            padding: 10px;
            text-align: center;
            border-radius: 6px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.05);
        }

        .product-card img {
            width: 100%;
            height: 120px;
            object-fit: contain;
        }

        .product-card h4 {
            font-size: 16px;
            margin: 10px 0 5px;
        }

        .product-card h3 {
            color: red;
            font-weight: bold;
        }

        .view-detail-link {
            display: inline-block;
            padding: 8px 16px;
            background-color: #ff7800;
            color: #fff;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s ease;
            font-size: 14px;
        }

        .view-detail-link:hover {
            background-color: #e06700;
        }
        .no-viewed {
            text-align: center;
            color: #999;
            font-style: italic;
            margin-top: 30px;
        }
        /* Overlay nền mờ */
        #logoutOverlay {
            position: fixed;
            top: 0;
            left: 0;
            width: 100vw;
            height: 100vh;
            background: rgba(0, 0, 0, 0.4);
            display: none;
            justify-content: center;
            align-items: center;
            z-index: 9999;
        }

        /* Hộp xác nhận */
        .logout-confirm-box {
            background-color: white;
            padding: 20px 30px;
            border-radius: 8px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
            text-align: center;
            font-family: 'Segoe UI', sans-serif;
            animation: slideDown 0.3s ease-out;
        }
        .logout-confirm-box p{
            font-size: 14px;
        }

        /* Hiệu ứng xuất hiện */
        @keyframes slideDown {
            from { transform: translateY(-20px); opacity: 0; }
            to { transform: translateY(0); opacity: 1; }
        }

        /* Nút */
        .logout-confirm-box button {
            padding: 8px 16px;
            margin: 10px 5px 0 5px;
            border: none;
            border-radius: 5px;
            font-weight: bold;
            cursor: pointer;
            transition: background 0.2s ease;
        }

        /* Nút "Đăng xuất" */
        .logout-confirm-box .confirm-btn {
            background-color: #ff6700; /* cam chủ đạo */
            color: white;
        }

        .logout-confirm-box .confirm-btn:hover {
            background-color: #e55c00;
        }

        /* Nút "Hủy" */
        .logout-confirm-box .cancel-btn {
            background-color: #ccc;
            color: #333;
        }

        .logout-confirm-box .cancel-btn:hover {
            background-color: #aaa;
        }

    </style>
</head>

<body>
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
<!-- Menu docj ban đầu ẩn , chỉ xuất hiện khi ấn icon -->
<nav class="sidebar-menu" id="sidebarMenu">
    <ul>
        <li class="active"><a href="/project_fruit/home" onclick="setActive(this)"><i class="fas fa-home"></i> Trang chủ</a></li>
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
<!-- header section ends -->

<div class="container">
    <!-- Sidebar -->
    <div class="sidebar">
        <div class="profile">
            <img alt="Profile Picture" height="50" src="${pageContext.request.contextPath}/assets/img/anhdaidien.jpg" width="50" />
            <span>${sessionScope.customer.customerName}</span> <!-- Hiển thị tên từ session -->
        </div>
        <ul>
            <li><a class="active" href="#" onclick="showSection('account-info', this)"><i class="fas fa-user"></i> Thông tin tài khoản</a></li>
            <li><a href="#" onclick="showSection('order-management', this)"><i class="fas fa-box"></i> Quản lý đơn hàng</a></li>
            <li><a href="#" onclick="showSection('recent-viewed', this)"><i class="fas fa-clock"></i> Sản phẩm đã xem</a></li>
            <li><a href="#" onclick="showSection('change-password', this)"><i class="fas fa-key"></i> Đổi Mật Khẩu</a></li>
            <li>
                <a href="#" id="logoutBtn">
                    <i class="fas fa-sign-out-alt"></i> Đăng xuất
                </a>
            </li>
        </ul>
    </div>

    <!-- Content -->
    <div class="content">
        <!-- Thông tin tài khoản -->
        <div id="account-info" class="section active">
            <h2>Thông Tin Tài Khoản</h2>
            <!-- Hiển thị thông báo -->
            <c:if test="${param.success == 'true'}">
                <div class="alert alert-success">Cập nhật thông tin thành công!</div>
            </c:if>
            <c:if test="${param.error == 'true'}">
                <div class="alert alert-danger">Cập nhật thông tin thất bại. Vui lòng thử lại.</div>
            </c:if>
            <!-- Form cập nhật thông tin -->
            <form action="${pageContext.request.contextPath}/update-customer-info" method="post">
                <!-- Họ và tên -->
                <div class="form-group">
                    <label for="name">Họ Tên</label>
                    <input id="name" name="customerName" type="text" value="${sessionScope.customer.customerName}" placeholder="Nhập họ và tên" required />
                </div>
                <!-- Số điện thoại -->
                <div class="form-group">
                    <label for="phone">Số Điện Thoại</label>
                    <input id="phone" name="customerPhone" type="text" value="${sessionScope.customer.customerPhone}" placeholder="Nhập số điện thoại" required />
                </div>
                <!-- Địa chỉ -->
                <div class="form-group">
                    <label for="address">Địa Chỉ</label>
                    <input id="address" name="address" type="text" value="${sessionScope.customer.address}" placeholder="Nhập địa chỉ" required />
                </div>
                <!-- Email (readonly) -->
                <div class="form-group">
                    <label for="email">Email</label>
                    <input id="email" type="text" value="${sessionScope.customer.email}" readonly />
                </div>
                <!-- Nút Lưu Thay Đổi -->
                <div class="form-group">
                    <button type="submit">LƯU THAY ĐỔI</button>
                </div>
            </form>
        </div>

        <!-- Quản lý đơn hàng -->
        <div id="order-management" class="section">
            <h2>Quản Lý Đơn Hàng</h2>
            <div class="tabs">
                <button onclick="showTab('all-orders')" class="tab active">Tất cả</button>
                <button onclick="showTab('new-orders')" class="tab">Mới</button>
                <button onclick="showTab('processing-orders')" class="tab">Đang xử lý</button>
                <button onclick="showTab('shipping-orders')" class="tab">Đang vận chuyển</button>
                <button onclick="showTab('completed-orders')" class="tab">Hoàn thành</button>
                <button onclick="showTab('cancelled-orders')" class="tab">Hủy</button>
            </div>

            <div class="order-search">
                <input type="text" placeholder="Tìm đơn hàng theo Mã đơn hàng..." id="orderSearchInput">
                <button onclick="searchOrders()">Tìm đơn hàng</button>
            </div>

            <div class="tab-content">
                <div id="all-orders" class="tab-pane active">
                    <p>Quý khách chưa có đơn hàng nào.</p>
                </div>
                <div id="new-orders" class="tab-pane">
                    <p>Không có đơn hàng mới.</p>
                </div>
                <div id="processing-orders" class="tab-pane">
                    <p>Không có đơn hàng đang xử lý.</p>
                </div>
                <div id="shipping-orders" class="tab-pane">
                    <p>Không có đơn hàng đang vận chuyển.</p>
                </div>
                <div id="completed-orders" class="tab-pane">
                    <p>Không có đơn hàng hoàn thành.</p>
                </div>
                <div id="cancelled-orders" class="tab-pane">
                    <p>Không có đơn hàng bị hủy.</p>
                </div>
            </div>
        </div>
<%--        Xem gần đây--%>
        <!-- Sản phẩm đã xem gần đây -->
        <div id="recent-viewed" class="section">
            <h2>Sản phẩm đã xem gần đây</h2>
            <div class="recent-products-list" id="recentProductsList">
                <c:choose>
                    <c:when test="${not empty sessionScope.recentlyViewed}">
                        <div class="product-list">
                            <c:forEach var="p" items="${sessionScope.recentlyViewed}">
                                <div class="product-card">
                                    <img src="${p.imageUrl != null ? p.imageUrl : '/assets/img/default.jpg'}"
                                 alt="${p.name}" />
                                    <h4>${p.name}</h4>
                                    <h3 class="price">${p.price}đ</del></span></h3>
                                    <a href="${pageContext.request.contextPath}/product-detail?pid=${p.id_product}" class="view-detail-link">
                                        Xem Chi Tiết
                                    </a>
                                </div>
                            </c:forEach>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <p class="no-viewed">Chưa có sản phẩm nào được xem.</p>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    <%-- Đổi mật khẩu--%>
        <div id="change-password" class="section">
            <h2>Đổi Mật Khẩu</h2>
            <form action="${pageContext.request.contextPath}/change-password" method="post" class="change-password-form">
                <div class="form-group">
                    <label for="currentPassword">Mật khẩu hiện tại</label>
                    <input type="password" id="currentPassword" name="currentPassword" required placeholder="Nhập mật khẩu cũ">
                </div>
                <div class="form-group">
                    <label for="newPassword">Mật khẩu mới</label>
                    <input type="password" id="newPassword" name="newPassword" required placeholder="Nhập mật khẩu mới">
                </div>
                <div class="form-group">
                    <label for="confirmPassword">Nhập lại mật khẩu mới</label>
                    <input type="password" id="confirmPassword" name="confirmPassword" required placeholder="Xác nhận mật khẩu mới">
                </div>
                <div class="form-group">
                    <button type="submit">XÁC NHẬN ĐỔI MẬT KHẨU</button>
                </div>
            </form>
        </div>
    </div>
</div>



<!-- footer -->
<section class="footer">
    <div class="box-container">
        <div class="box">
            <h3>VitaminFruit</h3>
            <p>
                "Chăm sóc sức khỏe bạn từ thiên nhiên! VitaminFruit – nguồn dinh dưỡng hoàn hảo cho cơ thể và tâm trí."
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
            <p>"Chúng tôi sử dụng các đơn vị vận chuyển uy tín như Grab, Giao Hàng Tiết Kiệm, VNPost và nhiều đơn vị khác."</p>
            <div class="shipping-brands">
                <img src="./img/logoBank/grab.jpg" alt="Grab" />
                <img src="./img/logoBank/giaohangtietkiem.png" alt="Giao Hàng Tiết Kiệm" />
                <img src="./img/logoBank/vnpost.webp" alt="VNPost" />
            </div>
        </div>
    </div>
    <div class="credit">Copyright © 2024 <span>Nhom 55 - Trái Cây Chất Lượng Cao</span></div>
</section>
<div id="logoutOverlay">
    <div class="logout-confirm-box">
        <p>Bạn chắc chắn muốn đăng xuất tài khoản?</p>
        <button class="confirm-btn" onclick="window.location.href='${pageContext.request.contextPath}/logout'">Đăng Xuất</button>
        <button class="cancel-btn" onclick="document.getElementById('logoutOverlay').style.display='none'">Hủy</button>
    </div>
</div>
<script src="${pageContext.request.contextPath}/assets/js/user.js" defer></script>
<script src="${pageContext.request.contextPath}/assets/js/fruit.js" defer></script>
<script>
    // Script để chuyển đổi giữa các section
    function showSection(sectionId, element) {
        document.querySelectorAll('.section').forEach(section => section.classList.remove('active'));
        document.querySelector(`#${sectionId}`).classList.add('active');
        document.querySelectorAll('.sidebar ul li a').forEach(link => link.classList.remove('active'));
        element.classList.add('active');
    }
</script>
<script>
    document.getElementById("logoutBtn").addEventListener("click", function (e) {
        e.preventDefault();
        document.getElementById("logoutOverlay").style.display = "flex";
    });
</script>
</body>

</html>