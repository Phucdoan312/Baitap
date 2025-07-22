<%-- 
    Document   : login
    Created on : Jul 8, 2025, 8:10:47 PM
    Author     : ad
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Đăng nhập</title>
        <link rel="stylesheet" href="assets/css/login.css">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <div class="login-container">
            <h2>Welcome to Barber Zone</h2>
            <form action="MainController" method="post">
                <input type="hidden" name="action" value="login" />
                <input type="text" name="email" placeholder="Email" required />
                <input type="password" name="password" placeholder="Mật khẩu" required />
                <button type="submit">Đăng nhập</button>
            </form>
            <a href="register.jsp">Bạn chưa có tài khoản? Đăng ký</a>
            <%
                String error = (String) request.getAttribute("ERROR");
                if (error != null) {
            %>
            <p style="color: red; margin-top: 10px;"><%= error%></p>
            <% }%>

        </div>
    </body>
</html>

