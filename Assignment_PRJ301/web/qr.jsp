<%-- 
    Document   : qr
    Created on : Jul 12, 2025, 10:11:14 AM
    Author     : ad
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.BookingDTO, model.ServiceDTO" %>
<%
    BookingDTO booking = (BookingDTO) request.getAttribute("BOOKING");
    ServiceDTO service = (ServiceDTO) request.getAttribute("SERVICE");
    String qrImage = (String) request.getAttribute("QR_IMAGE");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thanh toán đơn hàng</title>
    <link rel="stylesheet" href="assets/css/qr.css">
</head>
<body>
    <div class="container">
        <div class="header">Thanh toán đơn hàng</div>
        <div class="qr-wrapper">
            <div class="qr-left">
                <h2>🔒 Quét mã QR</h2>
                <img src="<%= request.getContextPath() + "/" + qrImage %>" alt="QR Code">
            </div>
            <div class="qr-right">
                <h3>Thông tin thanh toán</h3>
                <div class="info-row"><strong>Nhà cung cấp:</strong> BABER 3TL</div>
                <div class="info-row"><strong>Chủ tài khoản:</strong> TRAN PHUC DAT</div>
                <div class="info-row"><strong>Mã đơn hàng:</strong> Booking#<%= booking.getBookingId() %></div>
                <div class="info-row"><strong>Dịch vụ:</strong> <%= service.getName() %></div>
                <div class="info-row"><strong>Số tiền cần trả:</strong> <%= String.format("%,.0f", service.getPrice()) %> VNĐ</div>
                <div class="info-row"><strong>Nội dung chuyển khoản:</strong> BarberBooking_<%= booking.getPhone() %>_<%= booking.getBookingId() %></div>

                <form action="MainController" method="post">
                    <input type="hidden" name="action" value="listBooking">
                    <button class="back-btn">Quay lại</button>
                </form>
            </div>
        </div>
                <div class="note">          VUI LÒNG NHẬP ĐÚNG NỘI DUNG CHUYỂN KHOẢN     </div>
        <div class="note">
                              📌 Sau khi chuyển khoản, vui lòng đợi và theo dõi đơn dịch vụ của bạn trong hệ thống.
        </div>
    </div>
</body>
</html>