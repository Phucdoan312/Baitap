<%-- 
    Document   : editBooking
    Created on : Jul 13, 2025, 6:17:07 PM
    Author     : ad
--%>

<%-- 
    Document   : editBooking
    Created on : Jul 9, 2025, 9:42:33 PM
    Author     : ad
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.BookingDTO, model.ServiceDTO, java.util.List" %>
<%
    BookingDTO booking = (BookingDTO) request.getAttribute("BOOKING");
    List<ServiceDTO> services = (List<ServiceDTO>) request.getAttribute("SERVICE_LIST");
    model.UserDTO user = (model.UserDTO) session.getAttribute("USER");
    if (user == null || booking == null || services == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    String role = user.getRole();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chỉnh sửa lịch đặt</title>
    <link rel="stylesheet" href="assets/css/editBooking.css">
</head>
<body>

<header>
    <div class="logo">Barber<span>Booking</span></div>
    <form action="MainController" method="post">
        <input type="hidden" name="action" value="listBooking">
        <button class="menu-btn">Trở lại danh sách</button>
    </form>
</header>

<div class="container">
    <h2>✏ Cập nhật lịch đặt</h2>

    <form action="MainController" method="post">
        <input type="hidden" name="action" value="updateBooking">
        <input type="hidden" name="id" value="<%= booking.getBookingId() %>">

        <label>Dịch vụ:</label>
        <select name="service_id" required>
            <% for (ServiceDTO s : services) { %>
                <option value="<%= s.getServiceId() %>"
                    <%= s.getServiceId() == booking.getServiceId() ? "selected" : "" %>>
                    <%= s.getName() %>
                </option>
            <% } %>
        </select>

        <label>Thời gian:</label>
        <input type="datetime-local" name="booking_time" required
               value="<%= booking.getBookingTime().toLocalDateTime().toString().replace(" ", "T") %>">

        <label>Số điện thoại:</label>
        <input type="text" name="phone" required value="<%= booking.getPhone() %>">

        <label>Phương thức thanh toán:</label>
        <select name="payment_method" required>
            <option value="Tiền mặt" <%= booking.getPaymentMethod().equals("Tiền mặt") ? "selected" : "" %>>Tiền mặt</option>
            <option value="Momo" <%= booking.getPaymentMethod().equals("Momo") ? "selected" : "" %>>Momo</option>
            <option value="Chuyển khoản" <%= booking.getPaymentMethod().equals("Chuyển khoản") ? "selected" : "" %>>Chuyển khoản</option>
        </select>

        <% if ("ROLE_ADMIN".equals(role)) { %>
            <label>Trạng thái:</label>
            <select name="status">
                <option value="Pending" <%= booking.getStatus().equals("Pending") ? "selected" : "" %>>Pending</option>
                <option value="Confirmed" <%= booking.getStatus().equals("Confirmed") ? "selected" : "" %>>Confirmed</option>
<option value="Cancelled" <%= booking.getStatus().equals("Cancelled") ? "selected" : "" %>>Cancelled</option>
            </select>
        <% } else { %>
            <input type="hidden" name="status" value="<%= booking.getStatus() %>">
        <% } %>

        <button type="submit">💾 Lưu thay đổi</button>
    </form>

    <% if (request.getAttribute("ERROR") != null) { %>
        <div class="alert error"><%= request.getAttribute("ERROR") %></div>
    <% } %>
</div>

<footer>
    © 2025 BarberBooking | All rights reserved
</footer>

</body>
</html>


