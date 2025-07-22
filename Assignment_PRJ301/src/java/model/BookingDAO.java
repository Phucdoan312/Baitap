/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import utils.DbUtil;

/**
 *
 * @author ad
 */
public class BookingDAO {

    private Connection conn;

    public BookingDAO() throws Exception {
        conn = DbUtil.getConnection();
    }

    public boolean createBooking(BookingDTO booking) throws Exception {
        String sql = "INSERT INTO Bookings(user_id, service_id, booking_time, phone, payment_method, status) "
                + "VALUES (?, ?, ?, ?, ?, 'Pending')";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, booking.getUserId());
        ps.setInt(2, booking.getServiceId());
        ps.setTimestamp(3, new Timestamp(booking.getBookingTime().getTime()));
        ps.setString(4, booking.getPhone());
        ps.setString(5, booking.getPaymentMethod());
        return ps.executeUpdate() > 0;
    }

    public List<BookingDTO> getBookingsByUser(int userId) throws Exception {
        List<BookingDTO> list = new ArrayList<>();
        String sql = "SELECT b.*, s.name AS service_name "
                + "FROM Bookings b JOIN Services s ON b.service_id = s.service_id "
                + "WHERE b.user_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            BookingDTO b = new BookingDTO(
                    rs.getInt("booking_id"),
                    rs.getInt("user_id"),
                    rs.getInt("service_id"),
                    rs.getTimestamp("booking_time"),
                    rs.getString("phone"),
                    rs.getString("payment_method"),
                    rs.getString("status")
            );
            list.add(b);
        }
        return list;
    }

    public boolean deleteBooking(int bookingId) throws Exception {
        String sql = "DELETE FROM Bookings WHERE booking_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, bookingId);
        return ps.executeUpdate() > 0;
    }

    public boolean updateBooking(BookingDTO booking) throws Exception {
        String sql = "UPDATE Bookings SET service_id = ?, booking_time = ?, phone = ?, payment_method = ?, status = ? "
                + "WHERE booking_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, booking.getServiceId());
        ps.setTimestamp(2, new Timestamp(booking.getBookingTime().getTime()));
        ps.setString(3, booking.getPhone());
        ps.setString(4, booking.getPaymentMethod());
        ps.setString(5, booking.getStatus());
        ps.setInt(6, booking.getBookingId());
        return ps.executeUpdate() > 0;
    }

    public BookingDTO getBookingById(int id) throws Exception {
        String sql = "SELECT * FROM Bookings WHERE booking_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return new BookingDTO(
                    rs.getInt("booking_id"),
                    rs.getInt("user_id"),
                    rs.getInt("service_id"),
                    rs.getTimestamp("booking_time"),
                    rs.getString("phone"),
                    rs.getString("payment_method"),
                    rs.getString("status")
            );
        }
        return null;
    }

    public List<BookingDTO> getAllBookings() throws Exception {
        List<BookingDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM Bookings";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            BookingDTO b = new BookingDTO(
                    rs.getInt("booking_id"),
                    rs.getInt("user_id"),
                    rs.getInt("service_id"),
                    rs.getTimestamp("booking_time"),
                    rs.getString("phone"),
                    rs.getString("payment_method"),
                    rs.getString("status")
            );
            list.add(b);
        }
        return list;
    }

    public boolean updateBookingStatus(int bookingId, String newStatus) throws Exception {
        String sql = "UPDATE Bookings SET status = ? WHERE booking_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, newStatus);
        ps.setInt(2, bookingId);
        return ps.executeUpdate() > 0;
    }


}
