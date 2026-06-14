package view;

import controller.BookingController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.List;

public class CustomerView extends JFrame {
    private JComboBox<String> cbShift;
    private JTextField txtEventDate;
    private JButton btnCheckHall, btnCreateBooking;
    private JTable tblHalls, tblDishes;
    private DefaultTableModel hallModel, dishModel;
    private DecimalFormat moneyFormatter = new DecimalFormat("#,###");
    
    // Khai báo Controller điều hướng chuẩn kiến trúc MVC
    private BookingController controller; 

    public CustomerView() {
        this.controller = new BookingController();
        
        setTitle("CỔNG THÔNG TIN KHÁCH HÀNG - CHUẨN KIẾN TRÚC MVC");
        setSize(900, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // ========================================================
        // 1. GIAO DIỆN PHÍA TRÊN (BỘ LỌC)
        // ========================================================
        JPanel pnlTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        pnlTop.setBorder(BorderFactory.createTitledBorder("Nhập thông tin thời gian tổ chức"));

        pnlTop.add(new JLabel("Ngày tổ chức (YYYY-MM-DD):"));
        txtEventDate = new JTextField("2026-06-20", 10);
        pnlTop.add(txtEventDate);

        pnlTop.add(new JLabel("Ca tiệc:"));
        cbShift = new JComboBox<>(new String[]{"SÁNG", "TRƯA", "CHIỀU", "TỐI"});
        pnlTop.add(cbShift);

        btnCheckHall = new JButton("🔍 Tìm Sảnh Trống");
        btnCheckHall.setBackground(new Color(41, 128, 185));
        btnCheckHall.setForeground(Color.WHITE);
        pnlTop.add(btnCheckHall);

        // ========================================================
        // 2. GIAO DIỆN TRUNG TÂM (HAI BẢNG DỮ LIỆU)
        // ========================================================
        JPanel pnlCenter = new JPanel(new GridLayout(1, 2, 10, 0));

        // BẢNG SẢNH TIỆC (Bên trái)
        String[] hallColumns = {"Mã Sảnh", "Tên Sảnh", "Sức Chứa", "Giá Gốc (VNĐ)", "Trạng Thái"};
        hallModel = new DefaultTableModel(hallColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        JPanel pnlLeftHall = new JPanel(new BorderLayout());
        pnlLeftHall.setBorder(BorderFactory.createTitledBorder("Danh sách sảnh tiệc"));
        tblHalls = new JTable(hallModel);
        
        // Cấu hình kích thước cột ngăn lỗi che chữ (...)
        tblHalls.getColumnModel().getColumn(0).setPreferredWidth(85);
        tblHalls.getColumnModel().getColumn(1).setPreferredWidth(140);
        tblHalls.getColumnModel().getColumn(2).setPreferredWidth(70);
        tblHalls.getColumnModel().getColumn(3).setPreferredWidth(110);
        tblHalls.getColumnModel().getColumn(4).setPreferredWidth(85);
        tblHalls.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        pnlLeftHall.add(new JScrollPane(tblHalls), BorderLayout.CENTER);

        // BẢNG MÓN ĂN (Bên phải)
        String[] dishColumns = {"Mã Món", "Tên Món Ăn", "Phân Loại", "Đơn Giá (VNĐ)"};
        dishModel = new DefaultTableModel(dishColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        JPanel pnlRightDish = new JPanel(new BorderLayout());
        pnlRightDish.setBorder(BorderFactory.createTitledBorder("Danh sách món ăn chọn thực đơn"));
        tblDishes = new JTable(dishModel);
        
        // Cấu hình kích thước cột ngăn lỗi che chữ (...)
        tblDishes.getColumnModel().getColumn(0).setPreferredWidth(75);
        tblDishes.getColumnModel().getColumn(1).setPreferredWidth(160);
        tblDishes.getColumnModel().getColumn(2).setPreferredWidth(90);
        tblDishes.getColumnModel().getColumn(3).setPreferredWidth(100);
        tblDishes.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        pnlRightDish.add(new JScrollPane(tblDishes), BorderLayout.CENTER);

        pnlCenter.add(pnlLeftHall);
        pnlCenter.add(pnlRightDish);

        // ========================================================
        // 3. GIAO DIỆN PHÍA DƯỚI (NÚT CHỨC NĂNG)
        // ========================================================
        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        btnCreateBooking = new JButton("📝 Tiến Hành Tạo Đơn Đặt Tiệc (Tính Cọc 30%)");
        btnCreateBooking.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnCreateBooking.setBackground(new Color(46, 204, 113));
        btnCreateBooking.setForeground(Color.WHITE);
        pnlBottom.add(btnCreateBooking);

        add(pnlTop, BorderLayout.NORTH);
        add(pnlCenter, BorderLayout.CENTER);
        add(pnlBottom, BorderLayout.SOUTH);

        // Gọi hàm nạp dữ liệu ban đầu thông qua Controller
        initData();

        // ========================================================
        // 4. ĐIỀU HƯỚNG SỰ KIỆN QUA CONTROLLER
        // ========================================================

        // Bắt sự kiện Tìm kiếm sảnh trống
        btnCheckHall.addActionListener(e -> {
            String date = txtEventDate.getText().trim();
            String selectShift = cbShift.getSelectedItem().toString();
            try {
                hallModel.setRowCount(0);
                List<Object[]> availableHalls = controller.handleSearchAvailableHalls(date, selectShift);
                
                if (availableHalls.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Không còn sảnh nào trống vào ca tiệc này!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    for (Object[] row : availableHalls) {
                        row[3] = moneyFormatter.format((Double) row[3]); // Định dạng tiền tệ trước khi hiển thị
                        hallModel.addRow(row);
                    }
                    JOptionPane.showMessageDialog(this, "🎉 Đã lọc và cập nhật danh sách các sảnh tiệc còn trống khả dụng!");
                }
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng ngày YYYY-MM-DD!", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Bắt sự kiện Tạo đơn & Tính cọc 30% 
        btnCreateBooking.addActionListener(e -> {
            int selectedHallRow = tblHalls.getSelectedRow();
            if (selectedHallRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 sảnh tiệc trống trong bảng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String hallId = hallModel.getValueAt(selectedHallRow, 0).toString();
            String hallName = hallModel.getValueAt(selectedHallRow, 1).toString();
            String priceStr = hallModel.getValueAt(selectedHallRow, 3).toString();
            
            // Xử lý tính toán thông qua Controller
            double basePrice = controller.parseBasePrice(priceStr);
            double deposit = controller.calculateDeposit(priceStr); 

            String msg = String.format("🎉 Đã khởi tạo đơn tiệc tạm thời!\n"
                    + "▪️ Sảnh chọn: %s\n"
                    + "▪️ Phí thuê sảnh: %s VNĐ\n"
                    + "👉 Tiền cọc bắt buộc (30%%): %s VNĐ\n\n"
                    + "Hệ thống bắt đầu kích hoạt luồng tự động hủy đơn sau 24 giờ nếu thiếu tiền cọc!", 
                    hallName, moneyFormatter.format(basePrice), moneyFormatter.format(deposit));
            
            // 1. Hiện thông báo khởi tạo đơn tạm thời
            JOptionPane.showMessageDialog(this, msg, "Đặt Tiệc Thành Công", JOptionPane.INFORMATION_MESSAGE);
            
            // 2. MỞ TIẾP GIAO DIỆN CHỌN TIỀN MẶT / CREDIT NGAY LẬP TỨC KHI ẤN OK
            // Chế độ "DEPOSIT", truyền mã sảnh hoặc mã đơn tùy cấu trúc sinh mã của nhóm bạn
            GeneralPaymentDialog methodDialog = new GeneralPaymentDialog(this, "DEPOSIT", hallId, hallName, deposit);
            methodDialog.setVisible(true);
        });
    }

    // Hàm gọi nạp dữ liệu ban đầu sạch sẽ qua Controller
    private void initData() {
        try {
            hallModel.setRowCount(0);
            List<Object[]> halls = controller.handleLoadAllHalls();
            for (Object[] row : halls) {
                row[3] = moneyFormatter.format((Double) row[3]);
                hallModel.addRow(row);
            }

            dishModel.setRowCount(0);
            List<Object[]> dishes = controller.handleLoadAllDishes();
            for (Object[] row : dishes) {
                row[3] = moneyFormatter.format((Double) row[3]);
                dishModel.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}