package controller;

import model.database.DatabaseConnection;
import model.entity.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

public class AuthController {

    // ĐĂNG NHẬP KHÁCH HÀNG (Giữ nguyên - Đã chuẩn)
    public Optional<Customer> loginCustomer(String email, String password) {
        String sql = "SELECT * FROM Customer WHERE Email = ? AND Password = ?";
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return Optional.of(new Customer(
                    rs.getString("Email"),
                    rs.getString("Password"),
                    rs.getString("FullName"),
                    rs.getString("Phone")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    // FIX: ĐĂNG NHẬP NHÂN VIÊN (Đối chiếu mật khẩu thật từ cột Password trong DB)
    public Optional<Staff> loginStaff(String staffId, String password) {
        // Câu lệnh SQL kiểm tra đồng thời cả ID và Mật khẩu của nhân viên
        String sql = "SELECT * FROM Staff WHERE StaffId = ? AND Password = ?";
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, staffId);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String id = rs.getString("StaffId");
                String name = rs.getString("FullName");
                String role = rs.getString("Role"); // 'Manager', 'Receptionist', 'Kitchen'
                String pass = rs.getString("Password"); // Lấy mật khẩu thật từ DB lên

                // Đổ dữ liệu vào đúng Constructor có 4 tham số của các lớp con kế thừa
                if (role.equalsIgnoreCase("Manager")) {
                    return Optional.of(new Manager(id, name, role, pass));
                } else if (role.equalsIgnoreCase("Receptionist")) {
                    return Optional.of(new Receptionist(id, name, role, pass));
                } else if (role.equalsIgnoreCase("Kitchen")) {
                    return Optional.of(new Kitchen(id, name, role, pass));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}