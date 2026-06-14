package view;

import controller.ReceptionistController;
import javax.swing.*;
import java.awt.*;

public class ReceptionistView extends JFrame {
    private JTextField txtBookingId;
    private JButton btnConfirmBooking, btnCheckHall, btnOpenPayment;
    private ReceptionistController controller; // Khai báo Controller để điều hướng

    public ReceptionistView() {
        this.controller = new ReceptionistController(); // Khởi tạo Controller
        
        setTitle("HỆ THỐNG LỄ TÂN - Chuẩn Kiến Trúc MVC");
        setSize(550, 320);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridLayout(3, 1, 15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        btnCheckHall = new JButton("🔍 Tra Cứu Lịch Trống Sảnh Tiệc (Mở Bộ Lọc)");
        btnCheckHall.setFont(new Font("Tahoma", Font.BOLD, 13));

        btnOpenPayment = new JButton("💳 Quyết Toán & Thanh Toán Hóa Đơn");
        btnOpenPayment.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnOpenPayment.setBackground(new Color(41, 128, 185));
        btnOpenPayment.setForeground(Color.WHITE);

        JPanel confirmPanel = new JPanel(new BorderLayout(10, 0));
        txtBookingId = new JTextField("BKG_2026_002");
        txtBookingId.setFont(new Font("Tahoma", Font.PLAIN, 13));
        
        btnConfirmBooking = new JButton("Xác Nhận Đơn & Cọc");
        btnConfirmBooking.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnConfirmBooking.setBackground(new Color(46, 204, 113));
        btnConfirmBooking.setForeground(Color.WHITE);
        
        confirmPanel.add(new JLabel("Mã Đơn Tiệc:"), BorderLayout.WEST);
        confirmPanel.add(txtBookingId, BorderLayout.CENTER);
        confirmPanel.add(btnConfirmBooking, BorderLayout.EAST);

        mainPanel.add(btnCheckHall);
        mainPanel.add(btnOpenPayment);
        mainPanel.add(confirmPanel);
        add(mainPanel, BorderLayout.CENTER);

        // ========================================================
        // 🛠️ ĐIỀU HƯỚNG SỰ KIỆN QUA CONTROLLER (Chuẩn MVC)
        // ========================================================
        btnCheckHall.addActionListener(e -> new CustomerView().setVisible(true));
        btnOpenPayment.addActionListener(e -> new PaymentView().setVisible(true));

        btnConfirmBooking.addActionListener(e -> {
            String bookingId = txtBookingId.getText().trim();
            
            // View đẩy dữ liệu cho Controller xử lý và nhận kết quả trả về bằng chuỗi ký hiệu (Mã phản hồi)
            String result = controller.processConfirmation(bookingId);
            
            // View chỉ lo việc hiển thị thông báo (Hộp thoại) dựa trên kết quả nhận được
            switch (result) {
                case "EMPTY_ID":
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập mã đơn đặt tiệc hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    break;
                case "NOT_FOUND":
                    JOptionPane.showMessageDialog(this, "Không tìm thấy mã đơn đặt tiệc này trong hệ thống!", "Lỗi Tìm Kiếm", JOptionPane.ERROR_MESSAGE);
                    break;
                case "ALREADY_CONFIRMED":
                    JOptionPane.showMessageDialog(this, "Đơn hàng này đã được xác nhận từ trước đó!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                    break;
                case "IS_CANCELLED":
                    JOptionPane.showMessageDialog(this, "Không thể duyệt đơn này vì khách hàng đã hủy lịch!", "Thông báo", JOptionPane.ERROR_MESSAGE);
                    break;
                case "SUCCESS":
                    // 1. Hiển thị thông báo thành công cũ của hệ thống lễ tân
                    JOptionPane.showMessageDialog(this, "🎉 Xác nhận thành công mã đơn: " + bookingId + "\nTrạng thái: PENDING -> CONFIRMED", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    
                    // 2. MỞ TIẾP GIAO DIỆN CHỌN TIỀN MẶT / CREDIT DÙNG CHUNG NGAY LẬP TỨC KHI ẤN OK
                    // - Chế độ: "DEPOSIT" (Thu tiền cọc sảnh)
                    // - Giả định lấy tiền cọc mặc định từ dữ liệu test (Ví dụ: 10,500,000 VNĐ hoặc bạn có thể viết hàm lấy giá thật từ DB qua controller)
                    double mockDeposit = 10500000; 
                    GeneralPaymentDialog methodDialog = new GeneralPaymentDialog(this, "DEPOSIT", bookingId, "Sảnh đã chọn duyệt", mockDeposit);
                    methodDialog.setVisible(true);
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Có lỗi xảy ra tại hệ thống cơ sở dữ liệu!", "Lỗi Hệ Thống", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        });
    }
}