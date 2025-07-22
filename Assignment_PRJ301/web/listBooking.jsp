<%-- 
    Document   : listBooking
    Created on : Jul 9, 2025, 9:10:10 PM
    Author     : ad
--%>

<%@page import="model.UserDTO"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, java.util.Map, model.BookingDTO" %>
<%@ page import="java.util.Map" %>
<%
    Map<Integer, String> userMap = (Map<Integer, String>) request.getAttribute("USER_MAP");
%>
<%
    UserDTO user = (UserDTO) session.getAttribute("USER");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    List<BookingDTO> bookings = (List<BookingDTO>) request.getAttribute("BOOKING_LIST");
    if (bookings == null) {
        bookings = new java.util.ArrayList<>();
    }

    Map<Integer, String> serviceMap = (Map<Integer, String>) request.getAttribute("SERVICE_MAP");
    if (serviceMap == null)
        serviceMap = new java.util.HashMap<>();
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Danh Sách Lịch Đặt</title>
        <link rel="stylesheet" href="assets/css/listBooking.css">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <script>
            function confirmDelete(id) {
                document.getElementById("deleteBookingId").value = id;
                document.getElementById("confirmModal").style.display = "flex";
            }
            function closeModal() {
                document.getElementById("confirmModal").style.display = "none";
            }
        </script>
    </head>
    <body>

        <header>
            <div class="logo">Barber<span>Booking</span></div>
            <form action="MainController" method="post">
                <input type="hidden" name="action" value="backToHome">
                <button class="menu-btn">Quay lại</button>
            </form>
        </header>

        <div class="container">
            <h2>📋 Lịch Đặt</h2>

            <% if (bookings.isEmpty()) { %>
            <p>Chưa có lịch đặt nào.</p>
            <% } else { %>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Khách hàng</th>
                        <th>Dịch vụ</th>
                        <th>Thời gian</th>
                        <th>SĐT</th>
                        <th>Thanh toán</th>
                        <th>Trạng thái</th>
                        <th>Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <% for (BookingDTO b : bookings) {%>
                    <tr>
                        <td><%= b.getBookingId()%></td>
                        <td><%= userMap.getOrDefault(b.getUserId(), "Unknown") %></td>
                        <td><%= serviceMap.getOrDefault(b.getServiceId(), "Không rõ")%></td>
                        <td><%= b.getBookingTime()%></td>
                        <td><%= b.getPhone()%></td>
                        <td><%= b.getPaymentMethod()%></td>
                        <td><%= b.getStatus()%></td>
                        <td>
                            <form action="MainController" method="post" style="display:inline;">
                                <input type="hidden" name="action" value="editBooking">
                                <input type="hidden" name="id" value="<%= b.getBookingId()%>">
                                <button type="submit" class="action-btn edit">Sửa</button>
                            </form>

                            <button type="button" class="action-btn delete" onclick="confirmDelete(<%= b.getBookingId()%>)">Xoá</button>

                            <% if ("ROLE_ADMIN".equals(user.getRole())) {%>
                            <form action="MainController" method="post" style="display:inline;">
                                <input type="hidden" name="action" value="updateStatus">
                                <input type="hidden" name="id" value="<%= b.getBookingId()%>">
                                <select name="status" class="select-status" onchange="this.form.submit()">
                                    <option value="Pending" <%= "Pending".equalsIgnoreCase(b.getStatus()) ? "selected" : ""%>>Pending</option>
                                    <option value="Confirmed" <%= "Confirmed".equalsIgnoreCase(b.getStatus()) ? "selected" : ""%>>Confirmed</option>
                                    <option value="Cancelled" <%= "Cancelled".equalsIgnoreCase(b.getStatus()) ? "selected" : ""%>>Cancelled</option>
                                    <option value="Done" <%= "Done".equalsIgnoreCase(b.getStatus()) ? "selected" : ""%>>Done</option>
                                </select>
                            </form>
                            <% } %>

                            <%
                                boolean isUser = "ROLE_USER".equals(user.getRole());
                                String method = b.getPaymentMethod().toLowerCase();
                                boolean isChuyenKhoan = method.contains("chuyển khoản") || method.contains("momo");
                                boolean isConfirmed = "Confirmed".equalsIgnoreCase(b.getStatus());

                                if (isUser && isChuyenKhoan && isConfirmed) {
                            %>
                            <form action="QRCodeController" method="post" style="display:inline;">
                                <input type="hidden" name="id" value="<%= b.getBookingId()%>">
                                <button type="submit" class="action-btn pay">Thanh toán đơn hàng</button>
                            </form>
                            <% } %>
                        </td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
            <% } %>

            <% if (request.getAttribute("SUCCESS") != null) {%>
            <div class="alert success"><%= request.getAttribute("SUCCESS")%></div>
            <% } else if (request.getAttribute("ERROR") != null) {%>
            <div class="alert error"><%= request.getAttribute("ERROR")%></div>
            <% }%>
        </div>

        <footer>© 2025 BarberBooking | All rights reserved</footer>

        <div id="confirmModal" style="display:none;" class="modal-overlay">
            <div class="modal-content">
                <p>Bạn chắc chắn muốn xoá?</p>
                <form method="post" action="MainController">
                    <input type="hidden" name="action" value="deleteBooking">
                    <input type="hidden" name="id" id="deleteBookingId">
                    <button type="submit">Xoá</button>
                    <button type="button" onclick="closeModal()">Huỷ</button>
                </form>
            </div>
        </div>

    </body>
</html>
