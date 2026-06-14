package view;

import java.awt.*;
import javax.swing.*;

public class MainJFrame extends JFrame {
    private JButton btnCustomerView;
    private JButton btnReceptionistView;
    private JButton btnKitchenView;
    private JButton btnManagerView;

    public MainJFrame() {
        setTitle("HỆ THỐNG QUẢN LÝ NHÀ HÀNG TIỆC CƯỚI - MAIN");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        JPanel panelTitle = new JPanel();
        JLabel lblTitle = new JLabel("HỆ THỐNG VẬN HÀNH NHÀ HÀNG TIỆC CƯỚI");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setForeground(new Color(0, 51, 102));
        panelTitle.add(lblTitle);

        JPanel panelButtons = new JPanel(new GridLayout(4, 1, 10, 15));
        panelButtons.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        btnCustomerView = new JButton("Giao Diện Khách Hàng (Đặt Tiệc)");
        btnReceptionistView = new JButton("Giao Diện Bộ Phận Lễ Tân");
        btnKitchenView = new JButton("Giao Diện Bộ Phận Nhà Bếp");
        btnManagerView = new JButton("Giao Diện Ban Quản Lý (Manager)");

        // Định dạng style nút bấm chung
        Font btnFont = new Font("Arial", Font.PLAIN, 14);
        for (JButton btn : new JButton[]{btnCustomerView, btnReceptionistView, btnKitchenView, btnManagerView}) {
            btn.setFont(btnFont);
            panelButtons.add(btn);
        }

        setLayout(new BorderLayout());
        add(panelTitle, BorderLayout.NORTH);
        add(panelButtons, BorderLayout.CENTER);

        // Bắt sự kiện chuyển View trực tiếp
        btnCustomerView.addActionListener(e -> new CustomerView().setVisible(true));
        btnReceptionistView.addActionListener(e -> new ReceptionistView().setVisible(true));
        btnKitchenView.addActionListener(e -> new KitchenView().setVisible(true));
        btnManagerView.addActionListener(e -> new ManagerView().setVisible(true));
    }

    public static void main(String[] args) {
        // Cài đặt Look and Feel hệ thống để giao diện đẹp hơn
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        
        SwingUtilities.invokeLater(() -> new MainJFrame().setVisible(true));
    }
}