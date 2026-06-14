package model.dao;

import model.database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PaymentDAO {

    // Tìm hóa đơn bằng mã Invoice hoặc mã Booking (Thông minh, chống rỗng dữ liệu)
    public Object[] findInvoiceByIdOrBookingId(String inputId) throws Exception {
        String sql = "SELECT InvoiceId, BookingId, TotalAmount, IsPaid "
                   + "FROM Invoice "
                   + "WHERE InvoiceId = ? OR BookingId = ?";
        
        Connection conn = DatabaseConnection.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, inputId.trim());
        ps.setString(2, inputId.trim());
        
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return new Object[] {
                rs.getString("InvoiceId"),
                rs.getString("BookingId"),
                rs.getDouble("TotalAmount"),
                rs.getInt("IsPaid")
            };
        }
        return null; // Không tìm thấy dòng nào phù hợp
    }

    // Cập nhật trạng thái IsPaid = 1 khi bấm Xác nhận thanh toán xong
    public boolean updateStatus(String invoiceId, int status) throws Exception {
        String sql = "UPDATE Invoice SET IsPaid = ? WHERE InvoiceId = ?";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, status);
        ps.setString(2, invoiceId.trim());
        
        return ps.executeUpdate() > 0; // Trả về true nếu update thành công ít nhất 1 dòng
    }
}