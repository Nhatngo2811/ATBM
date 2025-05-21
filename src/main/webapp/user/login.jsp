<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    // Không cần setAttribute cho request, dùng trực tiếp trong EL
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login - VitaminFruit</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/login.css">
    <link href='https://cdn.jsdelivr.net/npm/boxicons@2.0.5/css/boxicons.min.css' rel='stylesheet'>
    <style>
        .overlay {
            display: none;
            position: fixed;
            z-index: 9999;
            left: 0; top: 0;
            width: 100vw; height: 100vh;
            background-color: rgba(0, 0, 0, 0.6);
            justify-content: center;
            align-items: center;
        }

        .overlay-content {
            background-color: white;
            padding: 25px 30px;
            border-radius: 8px;
            max-width: 400px;
            width: 90%;
            text-align: center;
            animation: fadeIn 0.3s ease-in-out;
        }

        .overlay-content h3 {
            margin-top: 0;
            color: #e67e22;
        }

        .close-btn {
            margin-top: 15px;
            background-color: #ccc;
            border: none;
            padding: 8px 18px;
            border-radius: 5px;
            font-weight: bold;
            cursor: pointer;
        }
        @keyframes fadeIn {
            from { opacity: 0; transform: scale(0.9); }
            to { opacity: 1; transform: scale(1); }
        }

    </style>
</head>
<body>

<div class="l-form">
    <div class="shape1"></div>
    <div class="shape2"></div>

    <div class="form">
        <img src="${pageContext.request.contextPath}/assets/img/authentication.svg" alt="Background 1">

        <!-- FORM ĐĂNG NHẬP -->
        <form action="${pageContext.request.contextPath}/login" method="POST" class="form__content">
            <h1 class="form__title">VitaminFruit</h1>

            <!-- Email -->
            <div class="form__div form__div-one">
                <div class="form__icon"><i class='bx bx-user-circle'></i></div>
                <div class="form__div-input">
                    <label class="form__label">Email</label>
                    <input type="text" id="useremail" name="useremail" class="form__input"
                           value="${param.useremail != null ? param.useremail : ''}" required>
                </div>
            </div>

            <!-- Password -->
            <div class="form__div">
                <div class="form__icon"><i class='bx bx-lock'></i></div>
                <div class="form__div-input">
                    <label class="form__label">Mật khẩu</label>
                    <input type="password" id="pass" name="pass" class="form__input" required>
                </div>
            </div>

            <!-- Thông báo lỗi -->
            <c:if test="${not empty errorMessage}">
                <p style="color: red; text-align: center; font-weight: bold;">${errorMessage}</p>
            </c:if>

            <!-- Quên mật khẩu -->
            <a href="${pageContext.request.contextPath}/user/forgotPassword.jsp" class="form__forgot">Quên mật khẩu ?</a>

            <!-- Submit -->
            <input type="submit" class="form__button" value="Đăng nhập">

            <!-- Đăng ký -->
            <div class="register">
                <p>Chưa có tài khoản?</p>
                <a href="${pageContext.request.contextPath}/user/register.jsp">Đăng Kí</a>
            </div>

            <!-- Mạng xã hội -->
            <input type="hidden" id="googleToken" name="googleToken" value="">
            <input type="hidden" id="facebookToken" name="facebookToken" value="">
            <div class="form__social">
                <span class="form__social-text">Đăng nhập bằng</span>
                <a href="https://www.facebook.com/v19.0/dialog/oauth?client_id=695103062919463
&redirect_uri=http://localhost:8091/project_fruit/login-facebook&scope=email,public_profile"
                   class="form__social-icon"><i class='bx bxl-facebook'></i></a>
                <a href="https://accounts.google.com/o/oauth2/auth?scope=email%20profile%20openid
&redirect_uri=http://localhost:8091/project_fruit/google-login&response_type=code
&client_id=1033832143997-6e97eqtcre3a5em76s2fo28rld9dpf8v.apps.googleusercontent.com
&prompt=select_account" class="form__social-icon"><i class='bx bxl-google'></i></a>
                <a href="#" class="form__social-icon"><i class='bx bxl-instagram'></i></a>
            </div>
        </form>

        <!-- Overlay Xác thực -->
        <c:if test="${pageContext.request.method eq 'POST'
    and not empty resendVerificationEmail
    and errorMessage == 'Tài khoản chưa được xác thực qua email. Vui lòng kiểm tra hộp thư.'}">
            <div id="verifyOverlay" class="overlay">
                <div class="overlay-content">
                    <h3>Xác thực tài khoản</h3>
                    <p>Tài khoản <strong>${resendVerificationEmail}</strong> chưa được xác thực.<br>Nhấn nút bên dưới để gửi lại email xác thực.</p>
                    <form action="${pageContext.request.contextPath}/resend-verification" method="post">
                        <input type="hidden" name="email" value="${resendVerificationEmail}" />
                        <button type="submit" class="form__button" style="background-color: #f39c12;">Gửi lại email xác thực</button>
                    </form>
                    <button onclick="closeOverlay()" class="close-btn">Đóng</button>
                </div>
            </div>
            <script>
                document.addEventListener("DOMContentLoaded", function () {
                    document.getElementById("verifyOverlay").style.display = "flex";
                });
                function closeOverlay() {
                    document.getElementById("verifyOverlay").style.display = "none";
                }
            </script>
        </c:if>


    </div>
</div>

<!-- Scripts -->
<script src="${pageContext.request.contextPath}/assets/js/formlogin.js"></script>
<script src="https://apis.google.com/js/platform.js" async defer></script>
<script src="https://connect.facebook.net/en_US/sdk.js"></script>


</body>
</html>
