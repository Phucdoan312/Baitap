<%-- 
    Document   : user
    Created on : Jul 11, 2025, 8:17:54 PM
    Author     : admin
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.UserDTO" %>

<%
    List<UserDTO> userList = (List<UserDTO>) request.getAttribute("USER_LIST");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>👥 Quản Lý Người Dùng</title>
        <link rel="stylesheet" href="assets/css/user.css">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <div class="container">
            <h1>👥 Danh sách người dùng</h1>

            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Họ tên</th>
                        <th>Email</th>
                        <th>Vai trò</th>
                        <th>Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <% if (userList != null && !userList.isEmpty()) {
                        for (UserDTO user : userList) {%>
                    <tr>
                        <td><%= user.getUserId()%></td>
                        <td><%= user.getName()%></td>
                        <td><%= user.getEmail()%></td>
                        <td><%= user.getRole()%></td>
                        <td>
                            <form action="UserController" method="post" style="display:inline;">
                                <input type="hidden" name="action" value="editUser"/>
                                <input type="hidden" name="userId" value="<%= user.getUserId()%>"/>
                                <button type="submit">Sửa</button>
                            </form>
                            <form action="UserController" method="post" style="display:inline;">
                                <input type="hidden" name="action" value="deleteUser"/>
                                <input type="hidden" name="userId" value="<%= user.getUserId()%>"/>
                                <button type="button" class="btn-delete" data-userid="<%= user.getUserId() %>">Xoá</button>
                            </form>
                        </td>
                    </tr>
                    <% }
                } else { %>
                    <tr>
                        <td colspan="5">Không có người dùng nào.</td>
                    </tr>
                    <% }%>
                </tbody>
            </table>

            <form action="MainController" method="post" style="margin-top: 30px; text-align: center;">
                <input type="hidden" name="action" value="backToHome">
                <button>Trang chủ</button>
            </form>
        </div>
        <div id="confirmModal" class="modal-overlay" style="display: none;">
            <div class="modal-content">
                <p>Bạn chắc chắn muốn xoá?</p>
                <form action="UserController" method="post">
                    <input type="hidden" name="action" value="deleteUser">
                    <input type="hidden" name="userId" id="modalUserId">
                    <button type="submit" class="delete-chat-btn">Xoá</button>
                    <button type="button" onclick="closeDeleteModal()">Huỷ</button>
                </form>
            </div>
        </div>

        <script>
            function showDeleteModal(userId) {
                document.getElementById("modalUserId").value = userId;
                document.getElementById("confirmModal").style.display = "flex";
            }

            function closeDeleteModal() {
                document.getElementById("confirmModal").style.display = "none";
            }

            document.querySelectorAll(".btn-delete").forEach(btn => {
                btn.addEventListener("click", function () {
                    const userId = this.getAttribute("data-userid");
                    showDeleteModal(userId);
                });
            });
        </script>
    </body>
</html>

