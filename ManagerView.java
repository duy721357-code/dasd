package view;

import controller.ManagerController;
import model.dto.InvoiceDTO;
import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

public class ManagerView extends JFrame {
    private JTextArea txtReportArea;
    private JButton btnRevenueReport;
    private JButton btnBookingStats;
    private DecimalFormat moneyFormatter = new DecimalFormat("#,###");
    private ManagerController controller; // Khai báo Controller

    public ManagerView() {
        this.controller = new ManagerController(); // Khởi tạo Controller
        
        setTitle("PHÂN HỆ QUẢN LÝ - Hệ Thống Nhà Hàng Tiệc Cưới");
        setSize(650, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel toolBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        btnRevenueReport = new JButton("📊 Doanh Thu (Stream API)");
        btnBookingStats = new JButton("📈 Thống Kê Đơn Đặt Sảnh");
        toolBar.add(btnRevenueReport);
        toolBar.add(btnBookingStats);

        txtReportArea = new JTextArea();
        txtReportArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        txtReportArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtReportArea);

        setLayout(new BorderLayout());
        add(toolBar, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Nghiệp vụ 1: Gọi Controller xử lý dữ liệu hóa đơn và hiển thị kết quả
        btnRevenueReport.addActionListener(e -> {
            try {
                txtReportArea.setText("=== BÁO CÁO CHI TIẾT DOANH THU (STREAM API) ===\n");
                txtReportArea.append(String.format("%-15s | %-15s | %s\n", "Mã Hóa Đơn", "Mã Đơn Tiệc", "Số Tiền Thu (VNĐ)"));
                txtReportArea.append("------------------------------------------------------------\n");

                // Gọi Controller lấy danh sách đã lọc bằng Stream API
                List<InvoiceDTO> paidInvoices = controller.getPaidInvoices();
                
                // Hiển thị chi tiết từng dòng
                for (InvoiceDTO inv : paidInvoices) {
                    txtReportArea.append(String.format("%-15s | %-15s | %s VNĐ\n", 
                        inv.getInvoiceId(), 
                        inv.getBookingId(), 
                        moneyFormatter.format(inv.getTotalAmount())));
                }

                txtReportArea.append("------------------------------------------------------------\n");
                txtReportArea.append("-> Thuật toán Stream API lọc & quét danh sách từ Controller thành công!\n");

                // Gọi Controller tính tổng doanh thu
                double totalRevenue = controller.calculateTotalRevenue(paidInvoices);
                txtReportArea.append(String.format("👉 TỔNG DOANH THU THỰC TẾ ĐÃ THU: %s VNĐ\n", moneyFormatter.format(totalRevenue)));

            } catch (Exception ex) {
                ex.printStackTrace();
                txtReportArea.setText("❌ Lỗi hệ thống: Không thể tải dữ liệu báo cáo doanh thu!");
            }
        });

        // Nghiệp vụ 2: Gọi Controller lấy Map thống kê trạng thái và in ra màn hình
        btnBookingStats.addActionListener(e -> {
            try {
                txtReportArea.setText("=== THỐNG KÊ TRẠNG THÁI ĐƠN HÀNG THẬT ===\n\n");
                
                Map<String, Integer> stats = controller.getBookingStatistics();
                
                if (stats.isEmpty()) {
                    txtReportArea.append("📭 Chưa có đơn đặt tiệc nào trong hệ thống.");
                } else {
                    for (Map.Entry<String, Integer> entry : stats.entrySet()) {
                        txtReportArea.append(String.format("▪️ Trạng thái [%-10s] : %d đơn\n", entry.getKey(), entry.getValue()));
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                txtReportArea.setText("❌ Lỗi hệ thống: Không thể tải thống kê trạng thái đơn hàng!");
            }
        });
    }
}