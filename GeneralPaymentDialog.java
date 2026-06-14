package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.DecimalFormat;

public class GeneralPaymentDialog extends JDialog {
    private JRadioButton rbCash, rbCredit;
    private JButton btnConfirm;
    private DecimalFormat moneyFormatter = new DecimalFormat("#,###");
    
    private String mode; // "DEPOSIT" (Đặt cọc) hoặc "FINAL" (Quyết toán)
    private String id;   // Có thể là BookingId hoặc InvoiceId

    // Constructor linh hoạt nhận diện mục đích sử dụng
    public GeneralPaymentDialog(JFrame parent, String mode, String id, String titleName, double amount) {
        super(parent, "XÁC NHẬN PHƯƠNG THỨC THANH TOÁN", true);
        this.mode = mode;
        this.id = id;

        setSize(520, 320);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(15, 15));
        ((JPanel)getContentPane()).setBorder(new EmptyBorder(15, 15, 15, 15));

        // Panel thông tin chi phí
        JPanel pnlInfo = new JPanel(new GridLayout(2, 1, 8, 8));
        pnlInfo.setBackground(new Color(248, 249, 250));
        pnlInfo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 224, 230), 1),
                BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));

        // Rẽ nhánh hiển thị chữ dựa trên chế độ "mode" truyền vào
        if (mode.equals("DEPOSIT")) {
            pnlInfo.add(new JLabel("🏢 Sảnh tiệc đăng ký giữ chỗ: " + titleName));
            pnlInfo.add(new JLabel("💰 Số tiền đặt cọc bắt buộc (30%): " + moneyFormatter.format(amount) + " VNĐ"));
        } else {
            pnlInfo.add(new JLabel("🧾 Mã số hóa đơn quyết toán tiệc: " + id));
            pnlInfo.add(new JLabel("💰 Tổng số tiền thu hoàn tất: " + moneyFormatter.format(amount) + " VNĐ"));
        }

        // Phần chọn phương thức
        JPanel pnlMethods = new JPanel(new GridLayout(2, 1, 10, 10));
        pnlMethods.setBorder(BorderFactory.createTitledBorder(" Chọn hình thức thanh toán "));
        rbCash = new JRadioButton("💵  Thanh toán bằng TIỀN MẶT tại quầy", true);
        rbCredit = new JRadioButton("💳  Thanh toán qua THẺ / QUÉT MÃ QR CODE (Credit)");
        ButtonGroup group = new ButtonGroup();
        group.add(rbCash); group.add(rbCredit);
        pnlMethods.add(rbCash); pnlMethods.add(rbCredit);

        btnConfirm = new JButton("Xác Nhận Thanh Toán");
        btnConfirm.setBackground(new Color(46, 204, 113));
        btnConfirm.setForeground(Color.WHITE);
        
        add(pnlInfo, BorderLayout.NORTH);
        add(pnlMethods, BorderLayout.CENTER);
        add(btnConfirm, BorderLayout.SOUTH);

        // XỬ LÝ SỰ KIỆN KHI BẤM NÚT XÁC NHẬN
        btnConfirm.addActionListener(e -> {
            String method = rbCash.isSelected() ? "TIỀN MẶT" : "CREDIT";
            
            // BIẾN THỂ XỬ LÝ DATABASE THEO TỪNG CHẾ ĐỘ:
            if (mode.equals("DEPOSIT")) {
                // Gọi sang BookingController để làm nhiệm vụ Đặt cọc
                JOptionPane.showMessageDialog(this, "🎉 Đã ghi nhận hình thức đóng cọc giữ sảnh: " + method);
            } else {
                // Gọi sang PaymentController để làm nhiệm vụ Cập nhật trạng thái Invoice lên 1
                JOptionPane.showMessageDialog(this, "🎉 Đã quyết toán thành công hóa đơn bằng: " + method);
            }
            dispose();
        });
    }
}