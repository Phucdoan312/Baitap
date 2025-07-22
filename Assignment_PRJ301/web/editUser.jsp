<%-- 
    Document   : editUser
    Created on : Jul 12, 2025, 7:30:02 PM
    Author     : admin
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.UserDTO" %>

<%
    UserDTO userToEdit = (UserDTO) request.getAttribute("USER_TO_EDIT");
    String error = (String) request.getAttribute("ERROR");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cập nhật người dùng</title>
    <link rel="stylesheet" href="assets/css/editUser.css">
</head>
<body>
    <div class="container">
        <h1>👥 Cập nhật người dùng</h1>

        <% if (error != null) { %>
            <div class="error"><%= error %></div>
        <% } %>

        <form action="UserController" method="post">
            <input type="hidden" name="action" value="updateUser"/>
            <input type="hidden" name="userId" value="<%= userToEdit.getUserId() %>"/>

            <label for="name">Họ tên:</label>
            <input type="text" id="name" name="name" value="<%= userToEdit.getName() %>" required>

            <label for="email">Email:</label>
            <input type="email" id="email" name="email" value="<%= userToEdit.getEmail() %>" required>

            <label for="role">Vai trò:</label>
            <select id="role" name="role" required>
                <option value="ROLE_USER" <%= "ROLE_USER".equals(userToEdit.getRole()) ? "selected" : "" %>>Người dùng</option>
                <option value="ROLE_ADMIN" <%= "ROLE_ADMIN".equals(userToEdit.getRole()) ? "selected" : "" %>>Quản trị viên</option>
            </select>

            <button type="submit">Cập nhật</button>
        </form>

        <form action="UserController" method="post" style="margin-top: 20px;">
            <input type="hidden" name="action" value="listUsers">
            <button>Quay lại danh sách</button>
        </form>
    </div>
</body>
</html>
