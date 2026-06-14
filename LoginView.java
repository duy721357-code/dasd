package view;

import controller.AuthController;
import model.entity.Customer;
import model.entity.Staff;
import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class LoginView extends JFrame {
    private JTextField txtAccount; // Có thể nhập Email khách hoặc Mã nhân viên
    private JPasswordField txtPassword;
    private JRadioButton rbCustomer;
    private JRadioButton rbStaff;
    private JButton btnLogin;
    private AuthController authController;

    public LoginView() {
        authController = new AuthController();

        // Cấu hình JFrame cơ bản
        setTitle("ĐẠI HỌC NÔNG LÂM - QUẢN LÝ TIỆC CƯỚI");
        setSize(480, 280);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Khởi tạo Panel chính sử dụng GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Tiêu đề giao diện
        JLabel lblTitle = new JLabel("ĐĂNG NHẬP HỆ THỐNG", JLabel.CENTER);
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblTitle.setForeground(new Color(44, 62, 80));
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(lblTitle, gbc);

        // Ô nhập tài khoản
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Tài khoản (Email/Mã NV):"), gbc);
        txtAccount = new JTextField(20);
        gbc.gridx = 1;
        panel.add(txtAccount, gbc);

        // Ô nhập mật khẩu
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Mật khẩu:"), gbc);
        txtPassword = new JPasswordField(20);
        gbc.gridx = 1;
        panel.add(txtPassword, gbc);

        // Chọn vai trò bằng Radio Button để xử lý phân quyền trực quan
        rbCustomer = new JRadioButton("Khách hàng", true);
        rbStaff = new JRadioButton("Nhân viên nhà hàng");
        ButtonGroup group = new ButtonGroup();
        group.add(rbCustomer);
        group.add(rbStaff);

        JPanel rolePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        rolePanel.add(rbCustomer);
        rolePanel.add(rbStaff);
        
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Vai trò:"), gbc);
        gbc.gridx = 1;
        panel.add(rolePanel, gbc);

        // Nút Đăng nhập
        btnLogin = new JButton("Đăng nhập");
        btnLogin.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnLogin.setBackground(new Color(39, 174, 96));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(btnLogin, gbc);

        add(panel);

        // Bắt sự kiện nút Login
        btnLogin.addActionListener(e -> handleLoginSubmit());
    }

    private void handleLoginSubmit() {
        String account = txtAccount.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (account.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // ĐĂNG NHẬP KHÁCH HÀNG
        if (rbCustomer.isSelected()) {
            Optional<Customer> customerOpt = authController.loginCustomer(account, password);
            if (customerOpt.isPresent()) {
                Customer customer = customerOpt.get();
                JOptionPane.showMessageDialog(this, "Chào mừng Khách hàng: " + customer.getFullName());
                
                this.dispose(); // BƯỚC 1: ĐÓNG MÀN HÌNH LOGIN
                new CustomerView().setVisible(true); // BƯỚC 2: MỞ GIAO DIỆN KHÁCH HÀNG TỰ ĐỘNG
            } else {
                JOptionPane.showMessageDialog(this, "Sai tài khoản Khách hàng hoặc Mật khẩu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } 
        // ĐĂNG NHẬP NHÂN VIÊN PHÂN QUYỀN
        else {
            Optional<Staff> staffOpt = authController.loginStaff(account, password);
            if (staffOpt.isPresent()) {
                Staff staff = staffOpt.get();
                String role = staff.getRole(); // Lấy ra chuỗi "Manager", "Receptionist", hoặc "Kitchen"
                
                JOptionPane.showMessageDialog(this, "Xác thực thành công! Nhân sự: " + staff.getFullName());
                
                this.dispose(); // BƯỚC 1: ĐÓNG MÀN HÌNH LOGIN ĐỂ GIẢI PHÓNG BỘ NHỚ

                // BƯỚC 2: PHÂN RÃ CHUYỂN MẠCH SANG GIAO DIỆN PHÙ HỢP
                if (role.equalsIgnoreCase("Manager")) {
                    new ManagerView().setVisible(true); 
                } else if (role.equalsIgnoreCase("Receptionist")) {
                    new ReceptionistView().setVisible(true); 
                } else if (role.equalsIgnoreCase("Kitchen")) {
                    new KitchenView().setVisible(true); 
                }
            } else {
                JOptionPane.showMessageDialog(this, "Mã nhân viên không tồn tại hoặc mật khẩu sai!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}