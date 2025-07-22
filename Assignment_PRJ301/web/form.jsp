<%-- 
    Document   : form
    Created on : Jul 9, 2025, 8:22:06 PM
    Author     : ad
--%>

<%@page import="model.UserDTO"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, model.ServiceDTO" %>
<%
    List<ServiceDTO> services = (List<ServiceDTO>) request.getAttribute("SERVICE_LIST");
    if (services == null) {
        services = new java.util.ArrayList<>();
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
        <meta charset="UTF-8">
        <title>Đặt Lịch Cắt Tóc</title>
        <link rel="stylesheet" href="assets/css/form.css">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>


        <header>
            <div class="logo">Barber<span>Booking</span></div>
            <nav>
                <ul>
                    <li>
                        <form action="MainController" method="post">
                            <input type="hidden" name="action" value="backToHome"/>
                            <button class="menu-btn">Trang chủ</button>
                        </form>
                    </li>
                    <li>
                        <form action="MainController" method="post">
                            <input type="hidden" name="action" value="viewServices"/>
                            <button class="menu-btn">Dịch vụ</button>
                        </form>
                    </li>
                    <li>
                        <form action="MainController" method="post">
                            <input type="hidden" name="action" value="listBooking"/>
                            <button class="menu-btn">Lịch đã đặt</button>
                        </form>
                    </li>
                </ul>
            </nav>
        </header>


        <div class="form-container">
            <h2>📅 Đặt Lịch Cắt Tóc</h2>

            <form action="MainController" method="post" class="booking-form">
                <input type="hidden" name="action" value="createBooking">


                <label>1. Chọn dịch vụ:</label>
                <select name="service_id" required>
                    <option value="">-- Chọn dịch vụ --</option>
                    <% for (ServiceDTO s : services) {%>
                    <option value="<%= s.getServiceId()%>">
                        <%= s.getName()%> 
                    </option>
                    <% }%>
                </select>


                <label>2. Chọn ngày & giờ:</label>
                <input type="datetime-local" name="booking_time" required 
                       min="<%= java.time.LocalDateTime.now().toString().substring(0, 16)%>">


                <label>3. Số điện thoại:</label>
                <input type="text" name="phone" required pattern="0[0-9]{9}" maxlength="10" placeholder="VD: 0912345678">


                <label>4. Phương thức thanh toán:</label>
                <select name="payment_method" required>
                    <option value="">-- Chọn phương thức --</option>
                    <option value="Tiền mặt">Tiền mặt</option>
                    <option value="Chuyển khoản">Chuyển khoản</option>
                    <option value="Momo">Momo</option>
                </select>

                <button type="submit">✅ Đặt lịch ngay</button>
            </form>

            <% if (request.getAttribute("SUCCESS") != null) {%>
            <div class="alert success"><%= request.getAttribute("SUCCESS")%></div>



            <% } else if (request.getAttribute("ERROR") != null) {%>
            <div class="alert error"><%= request.getAttribute("ERROR")%></div>
            <% }%>

        </div>

        <footer>
            © 2025 BarberBooking | All rights reserved
        </footer>

    </body>
</html>
