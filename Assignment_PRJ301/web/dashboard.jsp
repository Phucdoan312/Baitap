<%-- 
    Document   : dashboard
    Created on : Jul 8, 2025, 9:00:40 PM
    Author     : ad
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.UserDTO" %>
<%
    UserDTO user = (UserDTO) session.getAttribute("USER");
    if (user == null || !"ROLE_ADMIN".equals(user.getRole())) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Dashboard Admin</title>
        <link rel="stylesheet" href="assets/css/dashboard.css">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <div class="container">
            <h1>Xin chào, <%= user.getName()%>!</h1>
            <p>Quản trị hệ thống đặt lịch cắt tóc</p>

            <form action="MainController" method="post">
                <input type="hidden" name="action" value="listBooking"/>
                <button type="submit">📅 Quản lý lịch đặt</button>
            </form>

            <form action="MainController" method="post">
                <input type="hidden" name="action" value="viewServices"/>
                <button type="submit">💈 Xem Dịch vụ</button>
            </form>

            <form action="MainController" method="post">
                <input type="hidden" name="action" value="listUsers"/>
                <button type="submit">👥 Quản lý người dùng</button>
            </form>

            <form action="MainController" method="post">
                <input type="hidden" name="action" value="viewStats"/>
                <button type="submit">📊 Xem thống kê</button>
            </form>

            <form action="MainController" method="post">
                <input type="hidden" name="action" value="logout"/>
                <button type="submit">🚪 Đăng xuất</button>
            </form>
        </div>
    </body>
</html>
