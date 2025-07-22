<%-- 
    Document   : contact
    Created on : Jul 9, 2025, 2:26:51 PM
    Author     : ad
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.UserDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.UserDTO" %>
<%
    UserDTO user = (UserDTO) session.getAttribute("USER");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Liên hệ</title>
        <link rel="stylesheet" href="assets/css/contact.css">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>

        <header>
            <div class="logo">Barber<span>Booking</span></div>
            <nav>
                <ul>
                    <li><a href="home.jsp">Trang chủ</a></li>
                    <li><form action="MainController" method="post" style="display:inline;">
                            <input type="hidden" name="action" value="viewServices">
                            <button type="submit" class="nav-btn">Dịch vụ</button>
                        </form></li>
                    <li><form action="MainController" method="post" style="display:inline;">
                            <input type="hidden" name="action" value="logout"/>
                            <button class="nav-btn" type="submit">Đăng xuất</button>
                        </form></li>
                </ul>
            </nav>
        </header>

        <section class="contact-container">
            <h2>📞 Liên hệ với chúng tôi</h2>
            <p>Hãy gửi phản hồi, câu hỏi hoặc góp ý để chúng tôi phục vụ bạn tốt hơn.</p>

            <%
                String success = (String) request.getAttribute("SUCCESS");
                String error = (String) request.getAttribute("ERROR");
            %>
            <% if (success != null) {%>
            <div class="message success"><%= success%></div>
            <% } else if (error != null) {%>
            <div class="message error"><%= error%></div>
            <% }%>

            <form action="MainController" method="post" class="contact-form">
                <input type="hidden" name="action" value="sendContact">
                <input type="text" name="name" placeholder="Họ tên của bạn" required>
                <input type="email" name="email" placeholder="Email" required>
                <textarea name="message" placeholder="Nội dung liên hệ..." rows="6" required></textarea>
                <button type="submit">Gửi Đóng Góp</button>
            </form>
        </section>

        <footer>
            © 2025 BarberBooking | All rights reserved
        </footer>
    </body>
</html>
