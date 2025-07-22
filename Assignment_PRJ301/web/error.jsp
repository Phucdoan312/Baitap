<%-- 
    Document   : error
    Created on : Jul 8, 2025, 11:27:45 PM
    Author     : ad
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String errorMessage = (String) request.getAttribute("ERROR");
    if (errorMessage == null) {
        errorMessage = "Lỗi không xác định!";
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lỗi hệ thống</title>
    <link rel="stylesheet" href="assets/css/error.css">
</head>
<body>
    <div class="error-container">
        <h1>❌ Có lỗi xảy ra!</h1>
        <p><%= errorMessage %></p>
        <a href="MainController?action=backToHome">⬅ Quay lại trang chủ</a>
    </div>
</body>
</html>


