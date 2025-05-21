<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <title>Xác thực Email</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/verify-result.css">
</head>
<body>
<div style="text-align: center; margin-top: 100px;">
  <h2>${message}</h2>
  <a href="${pageContext.request.contextPath}/user/login.jsp">Quay về đăng nhập</a>
</div>
</body>
</html>
