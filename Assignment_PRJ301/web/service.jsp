<%-- 
    Document   : service
    Created on : Jul 8, 2025, 11:17:37 PM
    Author     : ad
--%>

<%@page import="model.UserDTO"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.ServiceDTO, java.util.List" %>
<%
    List<ServiceDTO> list = (List<ServiceDTO>) request.getAttribute("SERVICE_LIST");
    if (list == null) {
        list = new java.util.ArrayList<>();
    }

    UserDTO user = (UserDTO) session.getAttribute("USER");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Dịch vụ - BarberBooking</title>
        <link rel="stylesheet" href="assets/css/service.css">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>

        <% if ("ROLE_USER".equals(user.getRole())) { %>
        <header>
            <div class="logo">Barber<span>Booking</span></div>
            <nav>
                <ul>
                    <li><a href="home.jsp">Trang chủ</a></li>
                    <li>
                        <form action="contact.jsp" method="get" style="display:inline;">
                            <button class="menu-btn">Đóng góp</button>
                        </form>
                    </li>
                    <li>
                        <form action="MainController" method="post" style="display:inline;">
                            <input type="hidden" name="action" value="logout"/>
                            <button class="menu-btn" type="submit">Đăng xuất</button>
                        </form>
                    </li>
                </ul>
            </nav>
        </header>
        <% } %>

        <section class="service-section">
            <h2>💈 Dịch Vụ Của Chúng Tôi 💈</h2>
            <div class="service-container">
                <%
                    Locale vn = new Locale("vi", "VN");
                    NumberFormat vndFormat = NumberFormat.getCurrencyInstance(vn);
                %>
                <% for (ServiceDTO s : list) {
                        String imgName = "default.jpg";
                        String lower = s.getName().toLowerCase();
                        if (lower.contains("cắt")) {
                            imgName = "cut.jpg";
                        } else if (lower.contains("gội")) {
                            imgName = "headwashing.jpg";
                        } else if (lower.contains("nhuộm")) {
                            imgName = "nhuom.jpg";
                        } else if (lower.contains("tẩy") || lower.contains("tay")) {
                            imgName = "tay.jpg";
                        }
                        String img = "assets/img/" + imgName;
                %>
                <div class="service-card">
                    <img src="<%= img%>" alt="Ảnh dịch vụ">
                    <div class="info">
                        <h3><%= s.getName()%></h3>
                        <p><strong>Giá từ:</strong> <%= vndFormat.format(s.getPrice())%></p>
                        <p><%= s.getDescription()%></p>
                    </div>
                </div>
                <% } %>
            </div>

            <% if ("ROLE_ADMIN".equals(user.getRole())) { %>
            <form action="dashboard.jsp" method="get" style="margin-top: 30px; text-align: center;">
                <button class="menu-btn" type="submit">Quay lại</button>
            </form>
            <% }%>
        </section>

        <div class="booking-button">
            <form action="MainController" method="post">
                <input type="hidden" name="action" value="form"/>
                <button type="submit">✂️ Đặt lịch ngay</button>
            </form>
        </div>
        <footer>
            © 2025 BarberBooking. All rights reserved.
        </footer>
    </body>
</html>
