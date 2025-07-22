<%-- 
    Document   : home
    Created on : Jul 8, 2025, 8:59:30 PM
    Author     : ad
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.UserDTO" %>
<%
    UserDTO user = (UserDTO) session.getAttribute("USER");
    if (user == null || !"ROLE_USER".equals(user.getRole())) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Trang Người Dùng</title>
        <link rel="stylesheet" href="assets/css/home.css">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>

        <header>
            <div class="logo">Barber<span>Booking</span></div>
            <nav>
                <ul>
                    <li>
                        <form action="home.jsp" method="post">
                            <button class="link-button">Trang chủ</button>
                        </form>
                    </li>
                    <li>
                        <form action="MainController" method="post">
                            <input type="hidden" name="action" value="viewServices"/>
                            <button class="link-button">Dịch vụ</button>
                        </form>
                    </li>
                    <li>
                        <form action="contact.jsp" method="get">
                            <button class="link-button">Đóng góp</button>
                        </form>
                    </li>
                </ul>
            </nav>
        </header>


        <section class="hero">
            <div class="overlay">
                <h1>Chào mừng, <%= user.getName()%>!</h1>
                <p>Trải nghiệm dịch vụ cắt tóc chuyên nghiệp, hiện đại và đẳng cấp.</p>
            </div>
        </section>


        <section class="features">
            <form action="MainController" method="post">
                <input type="hidden" name="action" value="form"/>
                <button>📝 Đặt lịch mới</button>
            </form>

            <form action="MainController" method="post">
                <input type="hidden" name="action" value="listBooking"/>
                <button>📋 Xem lịch đã đặt</button>
            </form>

            <form action="MainController" method="post">
                <input type="hidden" name="action" value="logout"/>
                <button>🚪 Đăng xuất</button>
            </form>
        </section>
        <a href="MainController?action=chat" id="chatbot-button">
            <img src="assets/img/chat-dots-fill.svg" alt="Chat với bot">
        </a>
        <footer>
            © 2025 BarberBooking | All rights reserved
        </footer>
    </body>
</html>
