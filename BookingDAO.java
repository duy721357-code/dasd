package model.dao;

import model.database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BookingDAO {
    
    // Hàm kiểm tra trạng thái đơn hàng
    public String checkStatus(String bookingId) throws Exception {
        String sql = "SELECT Status FROM Booking WHERE BookingId = ?";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, bookingId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getString("Status");
        }
        return null;
    }

    // Hàm cập nhật trạng thái đơn hàng sang CONFIRMED
    public boolean confirmBooking(String bookingId) throws Exception {
        String sql = "UPDATE Booking SET Status = 'CONFIRMED' WHERE BookingId = ?";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, bookingId);
        return ps.executeUpdate() > 0;
    }
}