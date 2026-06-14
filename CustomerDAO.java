package model.dao;

import model.database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    // Lấy toàn bộ sảnh tiệc
    public List<Object[]> getAllHalls() throws Exception {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT * FROM BanquetHall";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            list.add(new Object[]{
                rs.getString("HallId"),
                rs.getString("Name"),
                rs.getInt("Capacity"),
                rs.getDouble("BasePrice"),
                rs.getString("Status")
            });
        }
        return list;
    }

    // Lấy toàn bộ món ăn
    public List<Object[]> getAllDishes() throws Exception {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT * FROM Dish";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            list.add(new Object[]{
                rs.getString("DishId"),
                rs.getString("Name"),
                rs.getString("Category"),
                rs.getDouble("Price")
            });
        }
        return list;
    }

    // Lọc danh sách sảnh tiệc còn trống theo ngày và ca
    public List<Object[]> getAvailableHalls(String date, String shift) throws Exception {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT h.HallId, h.Name, h.Capacity, h.BasePrice, h.Status "
                   + "FROM BanquetHall h "
                   + "WHERE h.Status = 'Available' AND h.HallId NOT IN ("
                   + "    SELECT s.HallId FROM HallBookedSlot s WHERE s.EventDate = ? AND s.Shift = ?"
                   + ")";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setDate(1, java.sql.Date.valueOf(date.trim()));
        ps.setNString(2, shift.trim());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            list.add(new Object[]{
                rs.getString("HallId"),
                rs.getString("Name"),
                rs.getInt("Capacity"),
                rs.getDouble("BasePrice"),
                rs.getString("Status")
            });
        }
        return list;
    }
}