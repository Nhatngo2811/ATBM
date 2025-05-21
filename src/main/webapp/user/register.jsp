<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- ===== CSS ===== -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/register.css">

    <!-- ===== BOX ICONS ===== -->
    <link href='https://cdn.jsdelivr.net/npm/boxicons@2.0.5/css/boxicons.min.css' rel='stylesheet'>

    <title>Register - VitaminFruit</title>
</head>

<body>
<div class="l-form">
    <div class="shape1"></div>
    <div class="shape2"></div>

    <div class="form">
        <img src="${pageContext.request.contextPath}/assets/img/authentication.svg" alt="Background 1">

        <!-- Form gửi dữ liệu đến RegisterController -->
        <form action="${pageContext.request.contextPath}/register" method="post" class="form__content">
            <h1 class="form__title">VitaminFruit</h1>

            <!-- Tên đầy đủ -->
            <div class="form__div form__div-one">
                <div class="form__icon">
                    <i class='bx bx-user-circle'></i>
                </div>
                <div class="form__div-input">
                    <label for="fullName" class="form__label">Tên đầy đủ</label>
                    <input type="text" id="fullName" name="fullName" class="form__input" required value="${oldFullName}">
                </div>
            </div>

            <!-- Email -->
            <div class="form__div">
                <div class="form__icon">
                    <i class='bx bx-envelope'></i>
                </div>
                <div class="form__div-input">
                    <label for="email" class="form__label">Email</label>
                    <input type="email" id="email" name="email" class="form__input" required value="${oldEmail}">
                </div>
            </div>

            <!-- Mật khẩu -->
            <div class="form__div">
                <div class="form__icon">
                    <i class='bx bx-lock'></i>
                </div>
                <div class="form__div-input">
                    <label for="password" class="form__label">Mật khẩu</label>
                    <input type="password" id="password" name="password" class="form__input" required>
                </div>
            </div>

            <!-- Xác nhận mật khẩu -->
            <div class="form__div">
                <div class="form__icon">
                    <i class='bx bx-lock'></i>
                </div>
                <div class="form__div-input">
                    <label for="confirmPassword" class="form__label">Xác nhận mật khẩu</label>
                    <input type="password" id="confirmPassword" name="confirmPassword" class="form__input" required>
                </div>
            </div>

            <!-- Nút Đăng Ký -->
            <input type="submit" class="form__button" value="Đăng Ký">

            <!-- Thông báo lỗi (nếu có) -->
            <c:if test="${not empty errorMessage}">
                <p style="color: red; text-align: center;">${errorMessage}</p>
            </c:if>

            <!-- Đăng nhập -->
            <div class="register">
                <p>Đã có tài khoản?</p>
                <a href="${pageContext.request.contextPath}/user/login.jsp">Đăng Nhập</a>
            </div>

            <!-- Mạng xã hội -->
            <div class="form__social">
                <span class="form__social-text">Đăng ký bằng</span>

                <a href="#" class="form__social-icon"><i class='bx bxl-facebook'></i></a>
                <a href="#" class="form__social-icon"><i class='bx bxl-google'></i></a>
                <a href="#" class="form__social-icon"><i class='bx bxl-instagram'></i></a>
            </div>
        </form>
    </div>
</div>

<script src="${pageContext.request.contextPath}/assets/js/formlogin.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</body>

</html>
