package view;

import controller.PaymentController;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.DecimalFormat;

public class PaymentView extends JFrame {
    private JTextField txtInvoiceId;
    private JButton btnSearch, btnConfirmPayment, btnCancel;
    private JLabel lblInvoiceIdVal, lblBookingIdVal, lblTotalAmount, lblStatus;
    private PaymentController controller;
    private DecimalFormat moneyFormatter = new DecimalFormat("#,###");

    public PaymentView() {
        this.controller = new PaymentController();

        setTitle("QUẦY QUYẾT TOÁN & THANH TOÁN HÓA ĐƠN");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(15, 15));
        ((JPanel)getContentPane()).setBorder(new EmptyBorder(15, 15, 15, 15));

        // ========================================================
        // 1. THANH TÌM KIẾM HÓA ĐƠN (Phía trên)
        // ========================================================
        JPanel pnlTop = new JPanel(new BorderLayout(10, 0));
        pnlTop.setBorder(BorderFactory.createTitledBorder("Tra cứu thông tin thanh toán"));
        
        txtInvoiceId = new JTextField("INV_2026_004");
        txtInvoiceId.setFont(new Font("Tahoma", Font.PLAIN, 13));
        btnSearch = new JButton("🔍 Tìm Hóa Đơn");
        btnSearch.setBackground(new Color(41, 128, 185));
        btnSearch.setForeground(Color.WHITE);
        
        pnlTop.add(new JLabel(" Nhập Mã Hóa Đơn / Mã Đơn:  "), BorderLayout.WEST);
        pnlTop.add(txtInvoiceId, BorderLayout.CENTER);
        pnlTop.add(btnSearch, BorderLayout.EAST);

        // ========================================================
        // 2. KHU VỰC HIỂN THỊ CHI TIẾT HÓA ĐƠN (Trung tâm)
        // ========================================================
        JPanel pnlInfo = new JPanel(new GridLayout(4, 2, 10, 15));
        pnlInfo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(" Thông tin chi tiết hóa đơn quyết toán "),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        pnlInfo.add(new JLabel("🧾 Mã số hóa đơn:"));
        lblInvoiceIdVal = new JLabel("---");
        lblInvoiceIdVal.setFont(new Font("Tahoma", Font.BOLD, 13));
        pnlInfo.add(lblInvoiceIdVal);

        pnlInfo.add(new JLabel("📅 Thuộc mã đơn đặt tiệc:"));
        lblBookingIdVal = new JLabel("---");
        lblBookingIdVal.setFont(new Font("Tahoma", Font.PLAIN, 13));
        pnlInfo.add(lblBookingIdVal);

        pnlInfo.add(new JLabel("💰 Tổng số tiền cần thanh toán:"));
        lblTotalAmount = new JLabel("0");
        lblTotalAmount.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblTotalAmount.setForeground(new Color(192, 57, 43));
        pnlInfo.add(lblTotalAmount);

        pnlInfo.add(new JLabel("📌 Trạng thái hiện tại:"));
        lblStatus = new JLabel("CHƯA TRA CỨU");
        lblStatus.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblStatus.setForeground(Color.GRAY);
        pnlInfo.add(lblStatus);

        // ========================================================
        // 3. HỆ THỐNG NÚT CHỨC NĂNG (Phía dưới)
        // ========================================================
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        
        btnCancel = new JButton("Đóng Cửa Sổ");
        btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 13));
        btnCancel.setPreferredSize(new Dimension(120, 38));
        
        btnConfirmPayment = new JButton("💳 Xác Nhận Thanh Toán & In Biên Lai");
        btnConfirmPayment.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnConfirmPayment.setBackground(new Color(46, 204, 113));
        btnConfirmPayment.setForeground(Color.WHITE);
        btnConfirmPayment.setPreferredSize(new Dimension(280, 38));
        btnConfirmPayment.setEnabled(false); // Chỉ bật lên khi đã tìm thấy hóa đơn chưa thanh toán

        pnlButtons.add(btnCancel);
        pnlButtons.add(btnConfirmPayment);

        add(pnlTop, BorderLayout.NORTH);
        add(pnlInfo, BorderLayout.CENTER);
        add(pnlButtons, BorderLayout.SOUTH);

        // ========================================================
        // 🛠️ XỬ LÝ SỰ KIỆN (LISTENERS) THEO CHUẨN MVC
        // ========================================================
        
        // Sự kiện đóng cửa sổ
        btnCancel.addActionListener(e -> dispose());

        // Sự kiện tìm kiếm hóa đơn
        btnSearch.addActionListener(e -> {
            String inputId = txtInvoiceId.getText().trim();
            if (inputId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập mã hóa đơn cần tìm!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Giả định Controller trả về mảng Object chứa thông tin hóa đơn từ DB
            Object[] invoiceData = controller.handleFindInvoice(inputId);
            
            if (invoiceData == null) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy dữ liệu hóa đơn phù hợp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                btnConfirmPayment.setEnabled(false);
            } else {
                lblInvoiceIdVal.setText(invoiceData[0].toString());
                lblBookingIdVal.setText(invoiceData[1].toString());
                
                double amount = (Double) invoiceData[2];
                lblTotalAmount.setText(moneyFormatter.format(amount));
                
                int isPaid = (Integer) invoiceData[3];
                if (isPaid == 1) {
                    lblStatus.setText("ĐÃ THANH TOÁN XONG");
                    lblStatus.setForeground(new Color(39, 174, 96));
                    btnConfirmPayment.setEnabled(false); // Đã trả rồi thì khóa nút lại
                } else {
                    lblStatus.setText("CHƯA THANH TOÁN (Đang nợ)");
                    lblStatus.setForeground(new Color(192, 57, 43));
                    btnConfirmPayment.setEnabled(true); // Bật nút lên để test quyết toán
                }
            }
        });

        // TÍCH HỢP ĐÚNG YÊU CẦU CỦA BẠN: Bấm thanh toán hiện Dialog dùng chung
        btnConfirmPayment.addActionListener(e -> {
            String invoiceId = lblInvoiceIdVal.getText();
            String totalAmountStr = lblTotalAmount.getText().replace(".", "").replace(",", "").trim();
            double totalAmount = Double.parseDouble(totalAmountStr);

            // 1. Hiện thông báo in biên lai cũ thành công
            JOptionPane.showMessageDialog(this, 
                "🎉 Thanh toán thành công cho hóa đơn: " + invoiceId + "\n" +
                "Hệ thống đã chuẩn bị in biên lai quyết toán tiệc cưới!", 
                "Thành công", JOptionPane.INFORMATION_MESSAGE);
            
            // 2. MỞ TIẾP GIAO DIỆN CHỌN PHƯƠNG THỨC THANH TOÁN DÙNG CHUNG NGAY LẬP TỨC
            // - Chế độ: "FINAL" (Quyết toán toàn bộ tiền tiệc cuối đơn)
            GeneralPaymentDialog methodDialog = new GeneralPaymentDialog(this, "FINAL", invoiceId, "", totalAmount);
            methodDialog.setVisible(true);
            
            // Làm mới giao diện (Cập nhật nhãn trạng thái thành ĐÃ THANH TOÁN)
            lblStatus.setText("ĐÃ THANH TOÁN XONG");
            lblStatus.setForeground(new Color(39, 174, 96));
            btnConfirmPayment.setEnabled(false);
        });
    }
}