<%-- 
    Document   : statistic
    Created on : Jul 11, 2025, 7:56:39 PM
    Author     : admin
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Map" %>
<%
    Integer totalUsers = (Integer) request.getAttribute("totalUsers");
    Integer totalServices = (Integer) request.getAttribute("totalServices");
    Integer totalBookings = (Integer) request.getAttribute("totalBookings");
    Map<String, Integer> bookingsByStatus = (Map<String, Integer>) request.getAttribute("bookingsByStatus");
    Map<String, Integer> topServices = (Map<String, Integer>) request.getAttribute("topServices");
%>
<!DOCTYPE html>
<html>
    <head>
        <title>📊 Thống kê</title>
        <link rel="stylesheet" href="assets/css/statistic.css">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <div class="container">
            <h1>📊 Báo Cáo Hệ Thống</h1>
            <ul>
                <li><strong>Tổng số người dùng:</strong> <%= totalUsers%></li>
                <li><strong>Tổng số dịch vụ:</strong> <%= totalServices%></li>
                <li><strong>Tổng số lịch đặt:</strong> <%= totalBookings%></li>
                <li><strong>Số lượng theo trạng thái:</strong>
                    <ul>
                        <% for (Map.Entry<String, Integer> entry : bookingsByStatus.entrySet()) {%>
                        <li><%= entry.getKey()%>: <%= entry.getValue()%></li>
                            <% } %>
                    </ul>
                </li>
                <li><strong>Top 3 dịch vụ được đặt nhiều nhất:</strong>
                    <ol>
                        <% for (Map.Entry<String, Integer> entry : topServices.entrySet()) {%>
                        <li><%= entry.getKey()%> - <%= entry.getValue()%> lượt đặt</li>
                            <% }%>
                    </ol>
                </li>
            </ul>
            <form action="MainController" method="post">
                <input type="hidden" name="action" value="backToHome"/>
                <button>Trang Chủ</button>
            </form>
        </div>
    </body>
</html>
