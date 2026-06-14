package controller;

import model.dao.ReportDAO;
import model.dto.InvoiceDTO;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ManagerController {
    private ReportDAO reportDAO;

    public ManagerController() {
        this.reportDAO = new ReportDAO();
    }

    // Lấy danh sách hóa đơn đã thanh toán bằng Stream API
    public List<InvoiceDTO> getPaidInvoices() throws Exception {
        List<InvoiceDTO> allInvoices = reportDAO.getAllInvoices();
        // DÙNG STREAM API: Lọc các hóa đơn có IsPaid == 1
        return allInvoices.stream()
                .filter(inv -> inv.getIsPaid() == 1)
                .collect(Collectors.toList());
    }

    // Tính tổng doanh thu bằng Stream API
    public double calculateTotalRevenue(List<InvoiceDTO> paidInvoices) {
        // DÙNG STREAM API: map sang kiểu double và tính tổng sum()
        return paidInvoices.stream()
                .mapToDouble(InvoiceDTO::getTotalAmount)
                .sum();
    }

    // Lấy dữ liệu thống kê trạng thái từ DAO
    public Map<String, Integer> getBookingStatistics() throws Exception {
        return reportDAO.getBookingStats();
    }
}