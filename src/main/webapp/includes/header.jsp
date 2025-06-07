<div class="user-menu" id="userMenu" style="display: none;">
    <p>Xin chào, <span id="userNameDisplay">${sessionScope.user.email}</span></p>
    <ul>
        <li><a href="${pageContext.request.contextPath}/user/user.jsp"><i class="fas fa-user"></i> Thông tin cá nhân</a></li>
        <li><a href="${pageContext.request.contextPath}/user/orders"><i class="fas fa-box"></i> Quản lý đơn hàng</a></li>
        <li><a href="#"><i class="fas fa-eye"></i> Đã xem gần đây</a></li>
        <li><a href="${pageContext.request.contextPath}/logout"><i class="fas fa-sign-out-alt"></i> Đăng xuất</a></li>
    </ul>
</div>

<div class="nav-links">
    <a href="${pageContext.request.contextPath}/"><i class="fas fa-home"></i> Trang Chủ</a>
    <a href="${pageContext.request.contextPath}/trai-ngon-hom-nay">Trái Ngon Hôm Nay</a>
    <a href="${pageContext.request.contextPath}/trai-cay-viet-nam">Trái Cây Việt Nam</a>
    <a href="${pageContext.request.contextPath}/trai-cay-nhap-khau">Trái Cây Nhập Khẩu</a>
    <a href="${pageContext.request.contextPath}/trai-cay-cat-san">Trái Cây Cắt Sẵn</a>
    <a href="${pageContext.request.contextPath}/qua-tang-trai-cay">Quà Tặng Trái Cây</a>
    <a href="${pageContext.request.contextPath}/hop-qua-nguyet-cat">Hộp Quà Nguyệt Cát</a>
    <a href="${pageContext.request.contextPath}/trai-cay-say-kho">Trái Cây Sấy Khô</a>
    <a href="${pageContext.request.contextPath}/mut-trai-cay">Mứt Trái Cây</a>
    <a href="${pageContext.request.contextPath}/lien-he">Liên Hệ</a>
    <a href="${pageContext.request.contextPath}/user/orders"> Quản lý đơn hàng</a>
</div> 