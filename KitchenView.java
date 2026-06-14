package view;

import model.database.DatabaseConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class KitchenView extends JFrame {
    private JList<String> lstDishes;
    private DefaultListModel<String> listModel;
    private JButton btnRefresh;

    public KitchenView() {
        setTitle("MÀN HÌNH BỘ PHẬN BẾP - (Kitchen: Phạm Đầu Bếp Trưởng)");
        setSize(650, 450); // Tăng nhẹ kích thước để hiển thị chữ tiếng Việt không bị che khuất
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        listModel = new DefaultListModel<>();
        lstDishes = new JList<>(listModel);
        lstDishes.setFont(new Font("Tahoma", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(lstDishes);

        btnRefresh = new JButton("🔄 Cập Nhật Thực Đơn Mới Nhất");
        btnRefresh.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnRefresh.setBackground(new Color(41, 128, 185));
        btnRefresh.setForeground(Color.WHITE);

        // Tiêu đề phía trên chuẩn hóa tiếng Việt
        JLabel lblHeader = new JLabel("👨‍🍳 DANH SÁCH MÓN ĂN CẦN CHUẨN BỊ TRONG NGÀY", JLabel.CENTER);
        lblHeader.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        lblHeader.setFont(new Font("Tahoma", Font.BOLD, 14));

        setLayout(new BorderLayout());
        add(lblHeader, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(btnRefresh, BorderLayout.SOUTH);

        // Tự động load dữ liệu ngay khi vừa mở màn hình bếp lên
        loadKitchenOrders();

        // Sự kiện khi đầu bếp bấm nút làm mới/cập nhật dữ liệu
        btnRefresh.addActionListener(e -> {
            loadKitchenOrders();
            JOptionPane.showMessageDialog(this, "🎉 Đã đồng bộ danh sách món ăn trực tiếp từ SQL Server!");
        });
    }

    // Hàm kết nối SQL Server quét dữ liệu thực tế từ các bảng liên kết
    private void loadKitchenOrders() {
        listModel.clear();
        
        // Câu lệnh SQL nâng cao kết hợp INNER JOIN 3 bảng: Booking, BookingDetail, và Dish
        // Lọc ra toàn bộ các đơn tiệc cưới có trạng thái 'CONFIRMED' để nhà bếp chuẩn bị nấu
        String sql = "SELECT b.BookingId, b.Shift, d.Name AS DishName, bd.Quantity " +
                     "FROM Booking b " +
                     "INNER JOIN BookingDetail bd ON b.BookingId = bd.BookingId " +
                     "INNER JOIN Dish d ON bd.DishId = d.DishId " +
                     "WHERE b.Status = 'CONFIRMED' " +
                     "ORDER BY b.Shift ASC, b.BookingId ASC";
                     
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            String currentBookingId = "";
            boolean hasData = false;

            while (rs.next()) {
                hasData = true;
                String bookingId = rs.getString("BookingId");
                String shift = rs.getString("Shift");
                String dishName = rs.getString("DishName");
                int quantity = rs.getInt("Quantity");

                // Gom nhóm hiển thị theo từng Mã đơn đặt tiệc cho đầu bếp dễ nhìn
                if (!bookingId.equals(currentBookingId)) {
                    if (!currentBookingId.isEmpty()) {
                        listModel.addElement("---------------------------------------------------------------------------------");
                    }
                    listModel.addElement(String.format("📦 CA [%s] - Đơn Đặt Tiệc Số: %s", shift, bookingId));
                    currentBookingId = bookingId;
                }
                
                // Hiển thị chi tiết từng món ăn cần nấu kèm theo số lượng mâm tiệc
                listModel.addElement(String.format("    ➕ %s  👉  (Số lượng: %d bàn)", dishName, quantity));
            }

            if (!hasData) {
                listModel.addElement("📭 Hiện tại không có đơn tiệc cưới nào được 'CONFIRMED' để chuẩn bị món.");
            }
            
        } catch (Exception ex) {
            listModel.addElement("❌ Lỗi hệ thống: Không thể kết nối cơ sở dữ liệu để tải món ăn!");
            ex.printStackTrace();
        }
    }
}