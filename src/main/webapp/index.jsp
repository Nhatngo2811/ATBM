<%@ page import="vn.edu.hcmuaf.fit.project_fruit.dao.model.User" %>
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
    <!-- Thêm CSS của Swiper -->
    <link rel="stylesheet" href="https://unpkg.com/swiper/swiper-bundle.min.css">


    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css">
    <!-- link style css -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <!-- Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"/>
    <!-- link logo anh -->
    <link rel="icon" href="${pageContext.request.contextPath}/assets/img/logoBank/logoweb.png" type="image/x-icon">
    <title>Selling Fruit</title>
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
    <div class="branch-selection" id="branchSelection" style="display: none;">
        <h2>KHU VỰC MUA HÀNG</h2>
        <div class="form-group">
            <label for="city">Tỉnh/Thành</label>
            <select id="city">
                <option>- Chọn Thành phố/ tỉnh -</option>
                <option>Hồ Chí Minh</option>
            </select>
        </div>
        <div class="form-group">
            <label for="district">Quận/Huyện</label>
            <select id="district">
                <option>- Chọn Quận/Huyện -</option>
                <option>- Thủ Đức -</option>
                <option>- Quận 1 -</option>
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
                <p>SAV.7-00.01, Tầng trệt Tháp 7, The Sun Avenue, 28 Mai Chí Thọ, phường An Phú, Thành phố Thủ Đức</p>
            </div>
        </div>
    </div>
</header>
<!-- Menu Bar dưới Header -->
<!-- Menu Bar dưới Header -->
<nav class="menu-bar">
    <ul>
        <li class="active"><a href="/project_fruit/home" onclick="setActive(this)"><i class="fas fa-home"></i> Trang chủ</a>
        </li>
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
        <li class="active"><a href="/project_fruit/home" onclick="setActive(this)"><i class="fas fa-home"></i> Trang chủ</a>
        </li>
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

<!-- home section starts -->
<section class="home" id="home">
    <div class="swiper-container background-slider">
        <div class="swiper-wrapper">
            <div class="swiper-slide"><img src="./assets/img/back6.webp" alt="Background 4"></div>
            <div class="swiper-slide"><img src="./assets/img/back5.webp" alt="Background 1"></div>
            <div class="swiper-slide"><img src="./assets/img/back7.webp" alt="Background 2"></div>
            <div class="swiper-slide"><img src="./assets/img/back3.jpg" alt="Background 3"></div>
            <div class="swiper-slide"><img src="./assets/img/back3.webp" alt="Background 4"></div>
        </div>
    </div>
    <div class="swiper-button-pre"><i class="fa-solid fa-angle-left"></i></div>
    <div class="swiper-button-nex"><i class="fa-solid fa-angle-right"></i></div>
</section>

<!-- home section ends -->

<!-- features section starts -->
<section class="products" id="products">
    <h1 class="heading"><span>quà tặng trái cây</span></h1>
    <div class="swiper product-slider">
        <div class="swiper-wrapper">
            <c:forEach var="product" items="${data6}">
                <div class="swiper-slide box">
                    <a href="${pageContext.request.contextPath}/product-detail?pid=${product.id_product}">
                        <!-- Hiển thị hình ảnh sản phẩm -->
                        <img src="${product.imageUrl != null ? product.imageUrl : '/assets/img/default.jpg'}"
                             alt="${product.name}"/>
                        <!-- Phần giảm giá -->
                            <%--                        <div class="discount">${product.percentDiscount != null ? product.percentDiscount : 0}%</div>--%>
                        <!-- Hiển thị thông tin sản phẩm -->
                        <h4 style="color: red">Mã sản phẩm: ${product.id_product}</h4>
                        <h3>${product.name}</h3>
                        <h3 class="price">${product.discountedPrice}đ/ <span
                                style="color: gray; text-decoration: line-through"><del>${product.price}đ</del> </span>
                        </h3>
                        <div class="stars">
                            <i>Đánh giá: ${product.rating} <i class="fas fa-star"></i></i>
                        </div>
                        <!-- Nút thêm vào giỏ hàng -->
                        <a href="${pageContext.request.contextPath}/add-cart?addToCartPid=${product.id_product}"
                           class="btn">Thêm vào giỏ hàng</a>
                    </a>
                </div>
            </c:forEach>
        </div>
    </div>
    <div class="swiper product-slider">
        <div class="swiper-wrapper">
            <c:forEach var="product" items="${data6}">
                <div class="swiper-slide box">
                    <a href="${pageContext.request.contextPath}/product-detail?pid=${product.id_product}">
                        <!-- Hiển thị hình ảnh sản phẩm -->
                        <img src="${product.imageUrl != null ? product.imageUrl : '/assets/img/default.jpg'}"
                             alt="${product.name}"/>
                        <!-- Phần giảm giá -->
                            <%--                        <div class="discount">${product.percentDiscount != null ? product.percentDiscount : 0}%</div>--%>
                        <!-- Hiển thị thông tin sản phẩm -->
                        <h4 style="color: red">Mã sản phẩm: ${product.id_product}</h4>
                        <h3>${product.name}</h3>
                        <h3 class="price">${product.discountedPrice}đ/ <span
                                style="color: gray; text-decoration: line-through"><del>${product.price}đ</del> </span>
                        </h3>
                        <div class="stars">
                            <i>Đánh giá: ${product.rating} <i class="fas fa-star"></i></i>
                        </div>
                        <!-- Nút thêm vào giỏ hàng -->
                        <a href="${pageContext.request.contextPath}/add-cart?addToCartPid=${product.id_product}"
                           class="btn">Thêm vào giỏ hàng</a>
                    </a>
                </div>
            </c:forEach>
        </div>
    </div>
    <div class="view-more-container">
        <a href="/project_fruit/home?category=quatangtraicay" class="view-more" id="view-more-btn">Xem thêm sản
            phẩm quà tặng</a>
    </div>
</section>
<!-- features section ends -->
<!-------------------------------------------------------- trái cây hôm nay -------------------------------------->
<section class="products" id="products">
    <h1 class="heading"><span>trái cây hôm nay</span></h1>
    <div class="swiper product-slider">
        <div class="swiper-wrapper">
            <c:forEach var="product" items="${data3}">
                <div class="swiper-slide box">
                    <a href="${pageContext.request.contextPath}/product-detail?pid=${product.id_product}">
                        <!-- Hiển thị hình ảnh sản phẩm -->
                        <img src="${product.imageUrl != null ? product.imageUrl : '/assets/img/default.jpg'}"
                             alt="${product.name}"/>
                        <!-- Phần giảm giá -->
                            <%--                        <div class="discount">${product.percentDiscount != null ? product.percentDiscount : 0}%</div>--%>
                        <!-- Hiển thị thông tin sản phẩm -->
                        <h4 style="color: red">Mã sản phẩm: ${product.id_product}</h4>
                        <h3>${product.name}</h3>
                        <h3 class="price">${product.discountedPrice}đ/ <span
                                style="color: gray; text-decoration: line-through"><del>${product.price}đ</del> </span>
                        </h3>
                        <div class="stars">
                            <i>Đánh giá: ${product.rating} <i class="fas fa-star"></i></i>
                        </div>
                        <!-- Nút thêm vào giỏ hàng -->
                        <a href="${pageContext.request.contextPath}/add-cart?addToCartPid=${product.id_product}"
                           class="btn">Thêm vào giỏ hàng</a>
                    </a>
                </div>
            </c:forEach>
        </div>
    </div>
    <div class="swiper product-slider">
        <div class="swiper-wrapper">
            <c:forEach var="product" items="${data3}">
                <div class="swiper-slide box">
                    <a href="${pageContext.request.contextPath}/product-detail?pid=${product.id_product}">
                        <!-- Hiển thị hình ảnh sản phẩm -->
                        <img src="${product.imageUrl != null ? product.imageUrl : '/assets/img/default.jpg'}"
                             alt="${product.name}"/>
                        <!-- Phần giảm giá -->
                            <%--                        <div class="discount">${product.percentDiscount != null ? product.percentDiscount : 0}%</div>--%>
                        <!-- Hiển thị thông tin sản phẩm -->
                        <h4 style="color: red">Mã sản phẩm: ${product.id_product}</h4>
                        <h3>${product.name}</h3>
                        <h3 class="price">${product.discountedPrice}đ/ <span
                                style="color: gray; text-decoration: line-through"><del>${product.price}đ</del> </span>
                        </h3>
                        <div class="stars">
                            <i>Đánh giá: ${product.rating} <i class="fas fa-star"></i></i>
                        </div>
                        <!-- Nút thêm vào giỏ hàng -->
                        <a href="#" onclick="addToCart(${product.id_product})" class="btn">Thêm vào giỏ hàng</a>
                </div>
            </c:forEach>
        </div>
    </div>
    <div class="view-more-container">
        <a href="/project_fruit/home?category=traicayhomnay" class="view-more" id="view-more-btn">Xem thêm sản
            phẩm hôm nay</a>
    </div>
</section>
<!--------------------------------------- sản phẩm việt nam ------------------------------------------------->
<section class="products" id="products">
    <h1 class="heading"><span>trái cây việt nam</span></h1>

    <div class="swiper product-slider">
        <div class="swiper-wrapper">
            <c:forEach var="product" items="${data4}">
                <div class="swiper-slide box">
                    <a href="${pageContext.request.contextPath}/product-detail?pid=${product.id_product}">
                        <!-- Hiển thị hình ảnh sản phẩm -->
                        <img src="${product.imageUrl != null ? product.imageUrl : '/assets/img/default.jpg'}"
                             alt="${product.name}"/>
                        <!-- Phần giảm giá -->
                            <%--                        <div class="discount">${product.percentDiscount != null ? product.percentDiscount : 0}%</div>--%>
                        <!-- Hiển thị thông tin sản phẩm -->
                        <h4 style="color: red">Mã sản phẩm: ${product.id_product}</h4>
                        <h3>${product.name}</h3>
                        <h3 class="price">${product.discountedPrice}đ/ <span
                                style="color: gray; text-decoration: line-through"><del>${product.price}đ</del> </span>
                        </h3>
                        <div class="stars">
                            <i>Đánh giá: ${product.rating} <i class="fas fa-star"></i></i>
                        </div>
                        <!-- Nút thêm vào giỏ hàng -->
                        <a href="${pageContext.request.contextPath}/add-cart?addToCartPid=${product.id_product}"
                           class="btn">Thêm vào giỏ hàng</a>
                    </a>
                </div>
            </c:forEach>
        </div>
    </div>
    <div class="swiper product-slider">
        <div class="swiper-wrapper">
            <c:forEach var="product" items="${data4}">
                <div class="swiper-slide box">
                    <a href="${pageContext.request.contextPath}/product-detail?pid=${product.id_product}">
                        <!-- Hiển thị hình ảnh sản phẩm -->
                        <img src="${product.imageUrl != null ? product.imageUrl : '/assets/img/default.jpg'}"
                             alt="${product.name}"/>
                        <!-- Phần giảm giá -->
                            <%--                        <div class="discount">${product.percentDiscount != null ? product.percentDiscount : 0}%</div>--%>
                        <!-- Hiển thị thông tin sản phẩm -->
                        <h4 style="color: red">Mã sản phẩm: ${product.id_product}</h4>
                        <h3>${product.name}</h3>
                        <h3 class="price">${product.discountedPrice}đ/ <span
                                style="color: gray; text-decoration: line-through"><del>${product.price}đ</del> </span>
                        </h3>
                        <div class="stars">
                            <i>Đánh giá: ${product.rating} <i class="fas fa-star"></i></i>
                        </div>
                        <!-- Nút thêm vào giỏ hàng -->
                        <a href="${pageContext.request.contextPath}/add-cart?addToCartPid=${product.id_product}"
                           class="btn">Thêm vào giỏ hàng</a>
                    </a>
                </div>
            </c:forEach>
        </div>
    </div>
    <div class="view-more-container">
        <a href="/project_fruit/home?category=traicayvietnam" class="view-more" id="view-more-btn">Xem thêm sản
            phẩm việt nam</a>
    </div>
</section>
<!--------------------------------------------------- Trái cây nhập khẩu --------------------------------------->
<section class="products" id="products">
    <h1 class="heading"><span>trái cây nhập khẩu</span></h1>

    <div class="swiper product-slider">
        <div class="swiper-wrapper">
            <c:forEach var="product" items="${data5}">
                <div class="swiper-slide box">
                    <a href="${pageContext.request.contextPath}/product-detail?pid=${product.id_product}">
                        <!-- Hiển thị hình ảnh sản phẩm -->
                        <img src="${product.imageUrl != null ? product.imageUrl : '/assets/img/default.jpg'}"
                             alt="${product.name}"/>
                        <!-- Phần giảm giá -->
                            <%--                        <div class="discount">${product.percentDiscount != null ? product.percentDiscount : 0}%</div>--%>
                        <!-- Hiển thị thông tin sản phẩm -->
                        <h4 style="color: red">Mã sản phẩm: ${product.id_product}</h4>
                        <h3>${product.name}</h3>
                        <h3 class="price">${product.discountedPrice}đ/ <span
                                style="color: gray; text-decoration: line-through"><del>${product.price}đ</del> </span>
                        </h3>
                        <div class="stars">
                            <i>Đánh giá: ${product.rating} <i class="fas fa-star"></i></i>
                        </div>
                        <!-- Nút thêm vào giỏ hàng -->
                        <a href="${pageContext.request.contextPath}/add-cart?addToCartPid=${product.id_product}"
                           class="btn">Thêm vào giỏ hàng</a>
                    </a>
                </div>
            </c:forEach>
        </div>
    </div>
    <div class="swiper product-slider">
        <div class="swiper-wrapper">
            <c:forEach var="product" items="${data5}">
                <div class="swiper-slide box">
                    <a href="${pageContext.request.contextPath}/product-detail?pid=${product.id_product}">
                        <!-- Hiển thị hình ảnh sản phẩm -->
                        <img src="${product.imageUrl != null ? product.imageUrl : '/assets/img/default.jpg'}"
                             alt="${product.name}"/>
                        <!-- Phần giảm giá -->
                            <%--                        <div class="discount">${product.percentDiscount != null ? product.percentDiscount : 0}%</div>--%>
                        <!-- Hiển thị thông tin sản phẩm -->
                        <h4 style="color: red">Mã sản phẩm: ${product.id_product}</h4>
                        <h3>${product.name}</h3>
                        <h3 class="price">${product.discountedPrice}đ/ <span
                                style="color: gray; text-decoration: line-through"><del>${product.price}đ</del> </span>
                        </h3>
                        <div class="stars">
                            <i>Đánh giá: ${product.rating} <i class="fas fa-star"></i></i>
                        </div>
                        <!-- Nút thêm vào giỏ hàng -->
                        <a href="${pageContext.request.contextPath}/add-cart?addToCartPid=${product.id_product}"
                           class="btn">Thêm vào giỏ hàng</a>
                    </a>
                </div>
            </c:forEach>
        </div>
    </div>
    <div class="view-more-container">
        <a href="/project_fruit/home?category=traicaynhapkhau" class="view-more" id="view-more-btn">Xem thêm sản
            phẩm nhập khẩu</a>
    </div>
</section>
<!--------------------------------------------------------------- trái cây cắt sẵn ------------------------------------------->
<section class="products" id="products">
    <h1 class="heading"><span>trái cây cắt sẵn</span></h1>
    <div class="swiper product-slider">
        <div class="swiper-wrapper">
            <c:forEach var="product" items="${data6}">
                <div class="swiper-slide box">
                    <a href="${pageContext.request.contextPath}/product-detail?pid=${product.id_product}">
                        <!-- Hiển thị hình ảnh sản phẩm -->
                        <img src="${product.imageUrl != null ? product.imageUrl : '/assets/img/default.jpg'}"
                             alt="${product.name}"/>
                        <!-- Phần giảm giá -->
                            <%--                        <div class="discount">${product.percentDiscount != null ? product.percentDiscount : 0}%</div>--%>
                        <!-- Hiển thị thông tin sản phẩm -->
                        <h4 style="color: red">Mã sản phẩm: ${product.id_product}</h4>
                        <h3>${product.name}</h3>
                        <h3 class="price">${product.discountedPrice}đ/ <span
                                style="color: gray; text-decoration: line-through"><del>${product.price}đ</del> </span>
                        </h3>
                        <div class="stars">
                            <i>Đánh giá: ${product.rating} <i class="fas fa-star"></i></i>
                        </div>
                        <!-- Nút thêm vào giỏ hàng -->
                        <a href="${pageContext.request.contextPath}/add-cart?addToCartPid=${product.id_product}"
                           class="btn">Thêm vào giỏ hàng</a>
                    </a>
                </div>
            </c:forEach>
        </div>
    </div>
    <div class="swiper product-slider">
        <div class="swiper-wrapper">
            <c:forEach var="product" items="${data6}">
                <div class="swiper-slide box">
                    <a href="${pageContext.request.contextPath}/product-detail?pid=${product.id_product}">
                        <!-- Hiển thị hình ảnh sản phẩm -->
                        <img src="${product.imageUrl != null ? product.imageUrl : '/assets/img/default.jpg'}"
                             alt="${product.name}"/>
                        <!-- Phần giảm giá -->
                            <%--                        <div class="discount">${product.percentDiscount != null ? product.percentDiscount : 0}%</div>--%>
                        <!-- Hiển thị thông tin sản phẩm -->
                        <h4 style="color: red">Mã sản phẩm: ${product.id_product}</h4>
                        <h3>${product.name}</h3>
                        <h3 class="price">${product.discountedPrice}đ/ <span
                                style="color: gray; text-decoration: line-through"><del>${product.price}đ</del> </span>
                        </h3>
                        <div class="stars">
                            <i>Đánh giá: ${product.rating} <i class="fas fa-star"></i></i>
                        </div>
                        <!-- Nút thêm vào giỏ hàng -->
                        <a href="${pageContext.request.contextPath}/add-cart?addToCartPid=${product.id_product}"
                           class="btn">Thêm vào giỏ hàng</a>
                    </a>
                </div>
            </c:forEach>
        </div>
    </div>
    <div class="view-more-container">
        <a href="/project_fruit/home?category=traicaycatsan" class="view-more" id="view-more-btn">Xem thêm sản
            phẩm cắt sẵn</a>
    </div>
</section>
<!-- quà tặng trái cây -->
<section class="products" id="products">
    <h1 class="heading"><span>hộp quà nguyệt cát</span></h1>
    <div class="swiper product-slider">
        <div class="swiper-wrapper">
            <c:forEach var="product" items="${data7}">
                <div class="swiper-slide box">
                    <a href="${pageContext.request.contextPath}/product-detail?pid=${product.id_product}">
                        <!-- Hiển thị hình ảnh sản phẩm -->
                        <img src="${product.imageUrl != null ? product.imageUrl : '/assets/img/default.jpg'}"
                             alt="${product.name}"/>
                        <!-- Phần giảm giá -->
                            <%--                        <div class="discount">${product.percentDiscount != null ? product.percentDiscount : 0}%</div>--%>
                        <!-- Hiển thị thông tin sản phẩm -->
                        <h4 style="color: red">Mã sản phẩm: ${product.id_product}</h4>
                        <h3>${product.name}</h3>
                        <h3 class="price">${product.discountedPrice}đ/ <span
                                style="color: gray; text-decoration: line-through"><del>${product.price}đ</del> </span>
                        </h3>
                        <div class="stars">
                            <i>Đánh giá: ${product.rating} <i class="fas fa-star"></i></i>
                        </div>
                        <!-- Nút thêm vào giỏ hàng -->
                        <a href="${pageContext.request.contextPath}/add-cart?addToCartPid=${product.id_product}"
                           class="btn">Thêm vào giỏ hàng</a>
                    </a>
                </div>
            </c:forEach>
        </div>
    </div>
    <div class="swiper product-slider">
        <div class="swiper-wrapper">
            <c:forEach var="product" items="${data7}">
                <div class="swiper-slide box">
                    <a href="${pageContext.request.contextPath}/product-detail?pid=${product.id_product}">
                        <!-- Hiển thị hình ảnh sản phẩm -->
                        <img src="${product.imageUrl != null ? product.imageUrl : '/assets/img/default.jpg'}"
                             alt="${product.name}"/>
                        <!-- Phần giảm giá -->
                            <%--                        <div class="discount">${product.percentDiscount != null ? product.percentDiscount : 0}%</div>--%>
                        <!-- Hiển thị thông tin sản phẩm -->
                        <h4 style="color: red">Mã sản phẩm: ${product.id_product}</h4>
                        <h3>${product.name}</h3>
                        <h3 class="price">${product.discountedPrice}đ/ <span
                                style="color: gray; text-decoration: line-through"><del>${product.price}đ</del> </span>
                        </h3>
                        <div class="stars">
                            <i>Đánh giá: ${product.rating} <i class="fas fa-star"></i></i>
                        </div>
                        <!-- Nút thêm vào giỏ hàng -->
                        <a href="${pageContext.request.contextPath}/add-cart?addToCartPid=${product.id_product}"
                           class="btn">Thêm vào giỏ hàng</a>
                    </a>
                </div>
            </c:forEach>
        </div>
    </div>
    <div class="view-more-container">
        <a href="/project_fruit/home?category=hopquanguyencat" class="view-more" id="view-more-btn">Xem thêm sản
            phẩm quà tặng nguyên cát</a>
    </div>
</section>
<section class="products" id="products">
    <h1 class="heading"><span>trái cây sấy khô</span></h1>
    <div class="swiper product-slider">
        <div class="swiper-wrapper">
            <c:forEach var="product" items="${data8}">
                <div class="swiper-slide box">
                    <a href="${pageContext.request.contextPath}/product-detail?pid=${product.id_product}">
                        <!-- Hiển thị hình ảnh sản phẩm -->
                        <img src="${product.imageUrl != null ? product.imageUrl : '/assets/img/default.jpg'}"
                             alt="${product.name}"/>
                        <!-- Phần giảm giá -->
                            <%--                        <div class="discount">${product.percentDiscount != null ? product.percentDiscount : 0}%</div>--%>
                        <!-- Hiển thị thông tin sản phẩm -->
                        <h4 style="color: red">Mã sản phẩm: ${product.id_product}</h4>
                        <h3>${product.name}</h3>
                        <h3 class="price">${product.discountedPrice}đ/ <span
                                style="color: gray; text-decoration: line-through"><del>${product.price}đ</del> </span>
                        </h3>
                        <div class="stars">
                            <i>Đánh giá: ${product.rating} <i class="fas fa-star"></i></i>
                        </div>
                        <!-- Nút thêm vào giỏ hàng -->
                        <a href="${pageContext.request.contextPath}/add-cart?addToCartPid=${product.id_product}"
                           class="btn">Thêm vào giỏ hàng</a>
                    </a>
                </div>
            </c:forEach>
        </div>
    </div>
    <div class="swiper product-slider">
        <div class="swiper-wrapper">
            <c:forEach var="product" items="${data8}">
                <div class="swiper-slide box">
                    <a href="${pageContext.request.contextPath}/product-detail?pid=${product.id_product}">
                        <!-- Hiển thị hình ảnh sản phẩm -->
                        <img src="${product.imageUrl != null ? product.imageUrl : '/assets/img/default.jpg'}"
                             alt="${product.name}"/>
                        <!-- Phần giảm giá -->
                            <%--                        <div class="discount">${product.percentDiscount != null ? product.percentDiscount : 0}%</div>--%>
                        <!-- Hiển thị thông tin sản phẩm -->
                        <h4 style="color: red">Mã sản phẩm: ${product.id_product}</h4>
                        <h3>${product.name}</h3>
                        <h3 class="price">${product.discountedPrice}đ/ <span
                                style="color: gray; text-decoration: line-through"><del>${product.price}đ</del> </span>
                        </h3>
                        <div class="stars">
                            <i>Đánh giá: ${product.rating} <i class="fas fa-star"></i></i>
                        </div>
                        <!-- Nút thêm vào giỏ hàng -->
                        <a href="${pageContext.request.contextPath}/add-cart?addToCartPid=${product.id_product}"
                           class="btn">Thêm vào giỏ hàng</a>
                    </a>
                </div>
            </c:forEach>
        </div>
    </div>
    <div class="view-more-container">
        <a href="/project_fruit/home?category=traicaysaykho" class="view-more" id="view-more-btn">Xem thêm sản
            phẩm sấy khô</a>
    </div>
</section>
<!-- Mứt -->
<section class="products" id="products">
    <h1 class="heading"><span>Mứt trái cây</span></h1>
    <div class="swiper product-slider">
        <div class="swiper-wrapper">
            <c:forEach var="product" items="${data9}">
                <div class="swiper-slide box">
                    <a href="${pageContext.request.contextPath}/product-detail?pid=${product.id_product}">
                        <!-- Hiển thị hình ảnh sản phẩm -->
                        <img src="${product.imageUrl != null ? product.imageUrl : '/assets/img/default.jpg'}"
                             alt="${product.name}"/>
                        <!-- Phần giảm giá -->
                            <%--                        <div class="discount">${product.percentDiscount != null ? product.percentDiscount : 0}%</div>--%>
                        <!-- Hiển thị thông tin sản phẩm -->
                        <h4 style="color: red">Mã sản phẩm: ${product.id_product}</h4>
                        <h3>${product.name}</h3>
                        <h3 class="price">${product.discountedPrice}đ/ <span
                                style="color: gray; text-decoration: line-through"><del>${product.price}đ</del> </span>
                        </h3>
                        <div class="stars">
                            <i>Đánh giá: ${product.rating} <i class="fas fa-star"></i></i>
                        </div>
                        <!-- Nút thêm vào giỏ hàng -->
                        <a href="${pageContext.request.contextPath}/add-cart?addToCartPid=${product.id_product}"
                           class="btn">Thêm vào giỏ hàng</a>
                    </a>
                </div>
            </c:forEach>
        </div>
    </div>
    <div class="swiper product-slider">
        <div class="swiper-wrapper">
            <c:forEach var="product" items="${data9}">
                <div class="swiper-slide box">
                    <a href="${pageContext.request.contextPath}/product-detail?pid=${product.id_product}">
                        <!-- Hiển thị hình ảnh sản phẩm -->
                        <img src="${product.imageUrl != null ? product.imageUrl : '/assets/img/default.jpg'}"
                             alt="${product.name}"/>
                        <!-- Phần giảm giá -->
                            <%--                        <div class="discount">${product.percentDiscount != null ? product.percentDiscount : 0}%</div>--%>
                        <!-- Hiển thị thông tin sản phẩm -->
                        <h4 style="color: red">Mã sản phẩm: ${product.id_product}</h4>
                        <h3>${product.name}</h3>
                        <h3 class="price">${product.discountedPrice}đ/ <span
                                style="color: gray; text-decoration: line-through"><del>${product.price}đ</del> </span>
                        </h3>
                        <div class="stars">
                            <i>Đánh giá: ${product.rating} <i class="fas fa-star"></i></i>
                        </div>
                        <!-- Nút thêm vào giỏ hàng -->
                        <a href="${pageContext.request.contextPath}/add-cart?addToCartPid=${product.id_product}"
                           class="btn">Thêm vào giỏ hàng</a>
                    </a>
                </div>
            </c:forEach>
        </div>
    </div>
    <div class="view-more-container">
        <a href="/project_fruit/home?category=muttraicay" class="view-more" id="view-more-btn">Xem thêm sản phẩm
            mứt trái cây</a>
    </div>
</section>
<!-- products section end-->
<!-- categories section starts -->
<section class="categories" id="categories">
    <h1 class="heading">thể loại<span>sản phẩm</span></h1>
    <div class="box-container">
        <div class="box">
            <img src="./assets/img/traicayvietnam/mam_xoi_den.webp" alt=""/>
            <h3>Trái cây Việt Nam</h3>
            <p>Giảm giá tới 30%</p>
            <a href="./product/traicayvietnam.html" class="btn">mua ngay</a>
        </div>
        <div class="box">
            <img src="./assets/img/traicaynhapkhau/le_sua_noi_dia_trung.webp" alt=""/>
            <h3>Trái cây nhập khẩu</h3>
            <p>Giảm giá tới 25%</p>
            <a href="./product/traicaynhapkhau.html" class="btn">mua ngay</a>
        </div>
        <div class="box">
            <img src="./assets/img/traicaycatsan/cs01.webp" alt=""/>
            <h3>Trái cây cắt sẵn</h3>
            <p>Giảm giá tới 20%</p>
            <a href="./product/traicaycatsan.html" class="btn">mua ngay</a>
        </div>
        <div class="box">
            <img src="./assets/img/gq19.webp" alt=""/>
            <h3>Quà tặng trái cây</h3>
            <p>Giảm giá tới 30%</p>
            <a href="./product/quatangtraicay.html" class="btn">mua ngay</a>
        </div>
        <div class="box">
            <img src="./assets/img/hqnc1.png" alt=""/>
            <h3>Hộp quà nguyên cát</h3>
            <p>Giảm giá tới 30%</p>
            <a href="./product/hopqua.html" class="btn">mua ngay</a>
        </div>
        <div class="box">
            <img src="./assets/img/tcs1.jpg" alt=""/>
            <h3>Trái cây sấy khô</h3>
            <p>Giảm giá tới 50%</p>
            <a href="./product/traicaysaykho.html" class="btn">mua ngay</a>
        </div>
        <div class="box">
            <img src="./assets/img/mtc1.webp" alt=""/>
            <h3>Mứt trái cây</h3>
            <p>Giảm giá tới 45%</p>
            <a href="./product/muttraicay.html" class="btn">mua ngay</a>
        </div>
    </div>
</section>
<section class="brand" id="brand">
    <h1 class="heading"><span>Hợp tác thương hiệu</span></h1>
    <div class="swiper brand-slider">
        <div class="swiper-wrapper">
            <div class="swiper-slide box">
                <img src="./assets/img/logoBank/acb.webp" alt=""/>
            </div>
            <div class="swiper-slide box">
                <img src="./assets/img/logoBank/bidv.webp" alt=""/>
            </div>
            <div class="swiper-slide box">
                <img src="./assets/img/logoBank/helo.webp" alt=""/>
            </div>
            <div class="swiper-slide box">
                <img src="./assets/img/logoBank/map.webp" alt=""/>
            </div>
            <div class="swiper-slide box">
                <img src="./assets/img/logoBank/shahan.webp" alt=""/>
            </div>
            <div class="swiper-slide box">
                <img src="./assets/img/logoBank/bo.webp" alt=""/>
            </div>
            <div class="swiper-slide box">
                <img src="./assets/img/logoBank/ocb.webp" alt=""/>
            </div>
            <div class="swiper-slide box">
                <img src="./assets/img/logoBank/pep.webp" alt=""/>
            </div>
        </div>
        <div class="button-prev"><i class="fa-solid fa-angle-left"></i></div>
        <div class="button-next"><i class="fa-solid fa-angle-right"></i></div>
    </div>
</section>
<!-- blog section start -->
<section class="blogs" id="blogs">
    <h1 class="heading">blog <span>của chúng tôi</span></h1>
    <div class="box-container">
        <div class="box">
            <img src="./assets/img/bl1.0.jpg" alt=""/>
            <div class="content">
                <div class="icons">
                    <a href="#"> <i class="fas fa-user"></i>người dùng</a>
                    <a href="#"> <i class="fas fa-calendar"></i>1st 5/2023</a>
                </div>
                <h3>
                    Lợi Ích Sức Khỏe Của Trái Cây Tươi
                </h3>
                <p> - Giới thiệu về các loại trái cây phổ biến và lợi ích sức khỏe mà chúng mang lại. Ví dụ, cam giàu
                    vitamin
                    C giúp tăng cường hệ miễn dịch, táo chứa chất xơ tốt cho tiêu hóa, hay nho chứa chất chống oxy hóa
                    tốt cho
                    da.
                    <br>
                    - Nâng cao nhận thức về việc bổ sung trái cây vào chế độ ăn uống hàng ngày và khuyến khích khách
                    hàng lựa
                    chọn trái cây tươi cho sức khỏe.
                </p>
                <a href="#" class="btn">đọc thêm</a>
            </div>
        </div>
        <div class="box">
            <img src="./assets/img/bl2.jpg" alt=""/>
            <div class="content">
                <div class="icons">
                    <a href="#"> <i class="fas fa-user"></i>người dùng</a>
                    <a href="#"> <i class="fas fa-calendar"></i>2st 2/2021</a>
                </div>
                <h3>
                    Cách Bảo Quản Trái Cây Tươi Lâu Hơn
                </h3>
                <p>
                    - Cung cấp các mẹo và phương pháp bảo quản trái cây đúng cách để giữ được độ tươi lâu hơn. Hướng dẫn
                    về cách
                    bảo quản trái cây khác nhau như: để trong tủ lạnh, bảo quản ở nhiệt độ phòng, hoặc cách rửa và cất
                    trữ trái
                    cây. <br>
                    - Giúp khách hàng bảo quản trái cây mua về được lâu hơn, tránh lãng phí và đảm bảo chất lượng tốt
                    nhất khi
                    sử
                    dụng.
                </p>
                <a href="#" class="btn">đọc thêm</a>
            </div>
        </div>
        <div class="box">
            <img src="./assets/img/bl3.jpg" alt=""/>
            <div class="content">
                <div class="icons">
                    <a href="#"> <i class="fas fa-user"></i>người dùng</a>
                    <a href="#"> <i class="fas fa-calendar"></i>3st 7/2023</a>
                </div>
                <h3>
                    Những Món Ăn Ngon Với Trái Cây
                </h3>
                <p>
                    - Gợi ý các công thức món ăn hoặc đồ uống dễ làm từ trái cây, ví dụ như sinh tố trái cây, salad trái
                    cây, hoặc nước ép tươi mát. Kèm theo hình ảnh và hướng dẫn chi tiết để khách hàng dễ dàng thực hiện
                    tại nhà.
                    <br>
                    - Tạo cảm hứng cho khách hàng về cách sử dụng trái cây để chế biến các món ăn ngon, độc đáo, và
                    khuyến khích việc mua nhiều loại trái cây khác nhau.
                </p>
                <a href="#" class="btn">đọc thêm</a>
            </div>
        </div>
    </div>
</section>

<!-- blog section end -->
<!-- footer section star -------------------------------------------------------------->
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
            <p>"Chúng tôi sử dụng các đơn vị vận chuyển uy tín như Grab, Giao Hàng Tiết Kiệm, VNPost và nhiều đơn vị
                khác."
            </p>
            <div class="shipping-brands">
                <img src="./assets/img/logoBank/grab.jpg" alt="Grab"/>
                <img src="./assets/img/logoBank/giaohangtietkiem.png" alt="Giao Hàng Tiết Kiệm"/>
                <img src="./assets/img/logoBank/vnpost.webp" alt="VNPost"/>
            </div>
        </div>
    </div>
    <div class="credit">Copyright © 2024 <span>Nhom 55 - Trái Cây Chất Lượng Cao</span></div>
</section>
<!-- footer section end -->

<script src="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.js"></script>
<script src="https://unpkg.com/swiper/swiper-bundle.min.js"></script>
<script>
    var swiper = new Swiper(".background-slider", {
        loop: true,
        autoplay: {
            delay: 5000, // 5 giây giữa các slide
            disableOnInteraction: false,
        },
        effect: 'fade', // Hiệu ứng fade
        fadeEffect: {
            crossFade: true, // Chuyển mượt hơn
        },
        speed: 2000, // Thời gian chuyển slide (2 giây)
        navigation: {
            nextEl: '.swiper-button-nex',
            prevEl: '.swiper-button-pre',
        },
    });
</script>
<script src="${pageContext.request.contextPath}/assets/js/fruit.js" defer></script>
<script>
    function addToCart(productId) {
        let quantity = 1; // Mặc định số lượng là 1
        window.location.href = `${pageContext.request.contextPath}/add-cart?addToCartPid=` + productId + "&quantity=" + quantity;
    }
</script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script>
    const params = new URLSearchParams(window.location.search);
    if (params.get("message") === "google-success") {
        Swal.fire({
            icon: 'success',
            title: 'Đăng nhập Google thành công!',
            text: 'Chào mừng bạn đến với VitaminFruit!',
            timer: 2500,
            showConfirmButton: false
        });
    }
</script>

</body>

</html>