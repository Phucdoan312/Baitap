/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import utils.DbUtil;

/**
 *
 * @author ad
 */
public class ContactDAO {
    public boolean addContact(ContactDTO contact) throws Exception {
        String sql = "INSERT INTO Contact (name, email, message) VALUES (?, ?, ?)";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, contact.getName());
            ps.setString(2, contact.getEmail());
            ps.setString(3, contact.getMessage());
            return ps.executeUpdate() > 0;
        }
    }
}
