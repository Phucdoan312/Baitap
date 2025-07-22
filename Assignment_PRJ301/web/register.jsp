<%-- 
    Document   : register
    Created on : Jul 8, 2025, 8:11:05 PM
    Author     : ad
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Đăng ký tài khoản</title>
        <link rel="stylesheet" href="assets/css/register.css">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <div class="register-container">
            <h2>Tạo tài khoản mới</h2>
            <form action="MainController" method="post">
                <input type="hidden" name="action" value="register" />
                <input type="text" name="name" placeholder="Họ tên" required />
                <input type="text" name="email" placeholder="Email" required />
                <input type="password" name="password" placeholder="Mật khẩu" required />
                <button type="submit">Đăng ký</button>
            </form><%
                String error = (String) request.getAttribute("ERROR");
                if (error != null) {
            %>
            <p style="color: red; margin-top: 10px;"><%= error%></p>
            <%
                }
            %>
            <a href="login.jsp">Đã có tài khoản? Đăng nhập</a>
        </div>
    </body>
</html>

