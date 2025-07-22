/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import utils.DbUtil;

public class ChatbotDAO {

    public void saveMessage(ChatbotDTO chat) throws Exception {
        Connection conn = DbUtil.getConnection();
        String sql = "INSERT INTO ChatMessages (user_id, message, reply, time_sent) VALUES (?, ?, ?, GETDATE())";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, chat.getUserId());
        ps.setString(2, chat.getMessage());
        ps.setString(3, chat.getReply());
        ps.executeUpdate();
    }

    public List<ChatbotDTO> getMessagesByUser(int userId) throws Exception {
        List<ChatbotDTO> list = new ArrayList<>();
        Connection conn = DbUtil.getConnection();
        String sql = "SELECT * FROM ChatMessages WHERE user_id = ? ORDER BY time_sent DESC";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            ChatbotDTO dto = new ChatbotDTO(
                    rs.getInt("message_id"),
                    rs.getInt("user_id"),
                    rs.getString("message"),
                    rs.getString("reply"),
                    rs.getTimestamp("time_sent")
            );
            list.add(dto);
        }
        return list;
    }

    public void deleteMessagesByUserId(int userId) throws Exception {
        Connection conn = DbUtil.getConnection();
        String sql = "DELETE FROM ChatMessages WHERE user_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, userId);
        ps.executeUpdate();
    }

}
