package model.dao;

import model.database.DatabaseConnection;
import model.dto.InvoiceDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ReportDAO {

    // Lấy toàn bộ danh sách hóa đơn từ DB
    public List<InvoiceDTO> getAllInvoices() throws Exception {
        List<InvoiceDTO> list = new ArrayList<>();
        String sql = "SELECT InvoiceId, BookingId, TotalAmount, IsPaid FROM Invoice";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            list.add(new InvoiceDTO(
                rs.getString("InvoiceId"),
                rs.getString("BookingId"),
                rs.getDouble("TotalAmount"),
                rs.getInt("IsPaid")
            ));
        }
        return list;
    }

    // Lấy thống kê trạng thái đơn hàng (sử dụng Map để trả về cặp Status -> Count)
    public Map<String, Integer> getBookingStats() throws Exception {
        Map<String, Integer> stats = new LinkedHashMap<>();
        String sql = "SELECT Status, COUNT(*) AS Soluong FROM Booking GROUP BY Status";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            stats.put(rs.getString("Status"), rs.getInt("Soluong"));
        }
        return stats;
    }
}