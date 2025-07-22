<%-- 
    Document   : chatbot
    Created on : Jul 11, 2025, 9:21:03 PM
    Author     : admin
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.ChatbotDTO" %>
<%@ page import="model.UserDTO" %>
<%
    UserDTO user = (UserDTO) session.getAttribute("USER");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    List<ChatbotDTO> messages = (List<ChatbotDTO>) request.getAttribute("CHAT_MESSAGES");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Trợ lý ảo Chatbot</title>
        <link rel="stylesheet" href="assets/css/chat.css">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <div class="chat-container">
            <h2>Xin chào, <%= user.getName()%> 👋</h2>

            <div class="chat-box">
                <% if (messages != null) {
                    for (ChatbotDTO msg : messages) {%>
                <div class="message user">
                    <b>Bạn:</b> <%= msg.getMessage()%><br>
                    <small><%= msg.getTimeSent()%></small>
                </div>
                <div class="message bot">
                    <b>Bot:</b> <%= msg.getReply()%>
                </div>
                <% }
            } else { %>
                <p>Chưa có tin nhắn nào.</p>
                <% }%>
            </div>


            <form action="MainController" method="post" class="chat-form">
                <input type="hidden" name="action" value="sendMessage">
                <input type="text" name="message" placeholder="Nhập tin nhắn..." required>
                <button type="submit">Gửi</button>
            </form>


            <div class="chat-hints">
                <form action="MainController" method="post">
                    <input type="hidden" name="action" value="sendMessage">
                    <input type="hidden" name="message" value="Tiệm mở cửa lúc mấy giờ?">
                    <button type="submit">🕒 Giờ mở cửa</button>
                </form>

                <form action="MainController" method="post">
                    <input type="hidden" name="action" value="sendMessage">
                    <input type="hidden" name="message" value="Dịch vụ giá bao nhiêu?">
                    <button type="submit">💈 Bảng giá dịch vụ</button>
                </form>

                <form action="MainController" method="post">
                    <input type="hidden" name="action" value="sendMessage">
                    <input type="hidden" name="message" value="Tôi muốn đặt lịch.">
                    <button type="submit">📝 Đặt lịch</button>
                </form>

                <form action="MainController" method="post">
                    <input type="hidden" name="action" value="sendMessage">
                    <input type="hidden" name="message" value="Tôi cần hỗ trợ.">
                    <button type="submit">📞 Hỗ trợ</button>
                </form>
            </div>

            <form action="MainController" method="post">
                <input type="hidden" name="action" value="backToHome">
                <button type="submit">Trang chủ</button>
            </form>


            <button class="delete-chat-btn" onclick="showDeleteModal()">Xoá đoạn chat</button>
        </div>


        <div id="confirmModal" class="modal-overlay" style="display: none;">
            <div class="modal-content">
                <p>Bạn có chắc chắn muốn xoá toàn bộ đoạn chat?</p>
                <form action="MainController" method="post">
                    <input type="hidden" name="action" value="deleteChat">
                    <button type="submit" class="delete-chat-btn">Xoá</button>
                    <button type="button" onclick="closeDeleteModal()">Huỷ</button>
                </form>
            </div>
        </div>


        <script>
            function showDeleteModal() {
                document.getElementById("confirmModal").style.display = "flex";
            }

            function closeDeleteModal() {
                document.getElementById("confirmModal").style.display = "none";
            }
        </script>
    </body>
</html>






