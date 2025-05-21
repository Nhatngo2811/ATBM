<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">

  <!-- ===== CSS ===== -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/register.css">

  <!-- ===== BOX ICONS ===== -->
  <link href='https://cdn.jsdelivr.net/npm/boxicons@2.0.5/css/boxicons.min.css' rel='stylesheet'>

  <title>ForgotPassword - VitaminFruit</title>
</head>

<body>
<div class="l-form">
  <div class="shape1"></div>
  <div class="shape2"></div>

  <div class="form">
    <img src="${pageContext.request.contextPath}/assets/img/authentication.svg" alt="Background 1">

    <form action="${pageContext.request.contextPath}/forgot-password" method="post" class="form__content">
      <h1 class="form__title">Khôi Phục Mật Khẩu</h1>

      <!-- Email -->
      <div class="form__div">
        <div class="form__icon">
          <i class='bx bx-envelope'></i>
        </div>
        <div class="form__div-input">
          <label for="email" class="form__label">Email</label>
          <input type="email" id="email" name="email" class="form__input" required>
        </div>
      </div>

      <!-- Nút Khôi Phục -->
      <input type="submit" class="form__button" value="Khôi phục mật khẩu">


      <div class="register">
        <p>Đã nhớ mật khẩu?</p>
        <a href="${pageContext.request.contextPath}/user/login.jsp">Đăng Nhập</a>
      </div>
    </form>
  </div>
</div>

<script src="${pageContext.request.contextPath}/assets/js/formlogin.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<c:if test="${not empty errorMessage}">
  <script>
    Swal.fire({
      icon: 'error',
      title: 'Lỗi!',
      text: '${errorMessage}'
    });
  </script>
</c:if>

<c:if test="${not empty successMessage}">
  <script>
    Swal.fire({
      icon: 'success',
      title: 'Thành công!',
      text: '${successMessage}'
    });
  </script>
</c:if>
</body>

</html>
