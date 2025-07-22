/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import utils.DbUtil;

/**
 *
 * @author ad
 */
public class ServiceDAO {

    public List<ServiceDTO> getAllServices() throws Exception {
        List<ServiceDTO> list = new ArrayList<>();
        String sql = "SELECT service_id, name, price, description FROM Services";

        try ( Connection conn = DbUtil.getConnection();  PreparedStatement ps = conn.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ServiceDTO service = new ServiceDTO(
                        rs.getInt("service_id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getString("description")
                );
                list.add(service);
            }
        }

        return list;
    }

    public Map<Integer, String> getServiceMap() throws Exception {
        Map<Integer, String> map = new HashMap<>();
        String sql = "SELECT service_id, name FROM Services";
        try ( Connection conn = DbUtil.getConnection();  PreparedStatement ps = conn.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                map.put(rs.getInt("service_id"), rs.getString("name"));
            }
        }
        return map;
    }

    public ServiceDTO getServiceById(int id) throws Exception {
        String sql = "SELECT service_id, name, price, description FROM Services WHERE service_id = ?";
        try ( Connection conn = DbUtil.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new ServiceDTO(
                            rs.getInt("service_id"),
                            rs.getString("name"),
                            rs.getDouble("price"),
                            rs.getString("description")
                    );
                }
            }
        }
        return null;
    }

    public String getServiceNameById(int id) throws Exception {
        String sql = "SELECT name FROM Services WHERE service_id = ?";
        try ( Connection conn = DbUtil.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("name");
                }
            }
        }
        return "Không rõ dịch vụ";
    }
}
