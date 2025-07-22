/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.BookingDAO;
import model.BookingDTO;
import model.ServiceDAO;
import model.ServiceDTO;

/**
 *
 * @author ad
 */
@WebServlet(name = "QRCodeController", urlPatterns = {"/QRCodeController"})
public class QRCodeController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int bookingId = Integer.parseInt(request.getParameter("id"));
            BookingDTO booking = new BookingDAO().getBookingById(bookingId);
            ServiceDTO service = new ServiceDAO().getServiceById(booking.getServiceId());
            String qrImageUrl = "assets/img/";
            if ("Chuyển khoản".equalsIgnoreCase(booking.getPaymentMethod())) {
                qrImageUrl += "qr.jpg";
            } else if ("Momo".equalsIgnoreCase(booking.getPaymentMethod())) {
                qrImageUrl += "qr.jpg";
            } else {
                qrImageUrl += "default.png";
            }
            String content = "Thanh toán cho dịch vụ: " + service.getName()
                    + "\nSố tiền: " + service.getPrice() + " VNĐ"
                    + "\nNội dung chuyển khoản: BarberBooking_" + booking.getPhone() + "_" + booking.getBookingId();

            request.setAttribute("QR_IMAGE", qrImageUrl);
            request.setAttribute("QR_CONTENT", content);
            request.setAttribute("SERVICE", service);
            request.setAttribute("BOOKING", booking);
            request.getRequestDispatcher("qr.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("ERROR", "Không thể tạo mã QR");
            request.getRequestDispatcher("listBooking.jsp").forward(request, response);
        }
    }
}
