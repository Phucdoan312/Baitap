/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;
import utils.DbUtil;

public class StatisticDAO {

    public int countUsers() throws Exception {
        String sql = "SELECT COUNT(*) FROM Users";
        try ( Connection con = DbUtil.getConnection();  PreparedStatement ps = con.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public int countServices() throws Exception {
        String sql = "SELECT COUNT(*) FROM Services";
        try ( Connection con = DbUtil.getConnection();  PreparedStatement ps = con.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public int countBookings() throws Exception {
        String sql = "SELECT COUNT(*) FROM Bookings";
        try ( Connection con = DbUtil.getConnection();  PreparedStatement ps = con.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public Map<String, Integer> countBookingsByStatus() throws Exception {
        String sql = "SELECT status, COUNT(*) FROM Bookings GROUP BY status";
        Map<String, Integer> map = new LinkedHashMap<>();
        try ( Connection con = DbUtil.getConnection();  PreparedStatement ps = con.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                map.put(rs.getString(1), rs.getInt(2));
            }
        }
        return map;
    }

    public Map<String, Integer> topServices(int limit) throws Exception {
        String sql = "SELECT s.name, COUNT(*) as total "
                + "FROM Bookings b JOIN Services s ON b.service_id = s.service_id "
                + "GROUP BY s.name ORDER BY total DESC OFFSET 0 ROWS FETCH NEXT ? ROWS ONLY";
        Map<String, Integer> map = new LinkedHashMap<>();
        try ( Connection con = DbUtil.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                map.put(rs.getString(1), rs.getInt(2));
            }
        }
        return map;
    }
}
