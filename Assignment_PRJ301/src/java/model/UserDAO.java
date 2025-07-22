/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import utils.DbUtil;

/**
 *
 * @author ad
 */
public class UserDAO {

    public UserDTO checkLogin(String email, String hashedPassword) throws Exception {
        Connection conn = DbUtil.getConnection();
        String sql = "SELECT * FROM Users WHERE email = ? AND password_hash = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, email);
        ps.setString(2, hashedPassword);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return new UserDTO(
                    rs.getInt("user_id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("password_hash"),
                    rs.getString("role")
            );
        }
        return null;
    }

    public boolean register(UserDTO user) throws Exception {
        Connection conn = DbUtil.getConnection();
        String sql = "INSERT INTO Users (name, email, password_hash, role) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, user.getName());
        ps.setString(2, user.getEmail());
        ps.setString(3, user.getPasswordHash());
        ps.setString(4, user.getRole());
        return ps.executeUpdate() > 0;
    }

    public boolean emailExists(String email) throws Exception {
        Connection conn = DbUtil.getConnection();
        String sql = "SELECT * FROM Users WHERE email = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    public List<UserDTO> getAllUsers() throws Exception {
        List<UserDTO> list = new ArrayList<>();
        Connection conn = DbUtil.getConnection();
        String sql = "SELECT * FROM Users";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            list.add(new UserDTO(
                    rs.getInt("user_id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("password_hash"),
                    rs.getString("role")
            ));
        }
        return list;
    }

    public boolean hasBookings(int userId) throws Exception {
        Connection conn = DbUtil.getConnection();
        String sql = "SELECT 1 FROM Bookings WHERE user_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    public boolean deleteUserById(int userId) throws Exception {
        Connection conn = DbUtil.getConnection();
        String sql = "DELETE FROM Users WHERE user_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, userId);
        return ps.executeUpdate() > 0;
    }

    public UserDTO getUserById(int userId) throws Exception {
        Connection conn = DbUtil.getConnection();
        String sql = "SELECT * FROM Users WHERE user_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return new UserDTO(
                    rs.getInt("user_id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    null,
                    rs.getString("role")
            );
        }
        return null;
    }

    public boolean updateUser(UserDTO user) throws Exception {
        Connection conn = DbUtil.getConnection();
        String sql = "UPDATE Users SET name = ?, email = ?, role = ? WHERE user_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, user.getName());
        ps.setString(2, user.getEmail());
        ps.setString(3, user.getRole());
        ps.setInt(4, user.getUserId());
        return ps.executeUpdate() > 0;
    }

    public Map<Integer, String> getUserMap() throws SQLException, ClassNotFoundException {
        Map<Integer, String> userMap = new HashMap<>();
        String sql = "SELECT user_id, name FROM Users";
        try ( Connection conn = DbUtil.getConnection();  PreparedStatement ps = conn.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int userId = rs.getInt("user_id");
                String name = rs.getString("name");
                userMap.put(userId, name);
            }
        }
        return userMap;
    }

}
