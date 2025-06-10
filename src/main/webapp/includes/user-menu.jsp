<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="user-menu" id="userMenu" style="display: none;">
    <p>Xin chào, <span id="userNameDisplay">${sessionScope.user.email}</span></p>
    <ul>
        <li><a href="${pageContext.request.contextPath}/user/user.jsp"><i class="fas fa-user"></i> Thông tin cá nhân</a></li>
        <li><a href="${pageContext.request.contextPath}/user/orders"><i class="fas fa-box"></i> Quản lý đơn hàng</a></li>
        <li><a href="#"><i class="fas fa-eye"></i> Đã xem gần đây</a></li>
        <li><a href="${pageContext.request.contextPath}/logout"><i class="fas fa-sign-out-alt"></i> Đăng xuất</a></li>
    </ul>
</div> 