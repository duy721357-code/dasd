package controller;

import model.dao.PaymentDAO; // Đảm bảo import đúng lớp DAO quản lý thanh toán của bạn

public class PaymentController {
    private PaymentDAO paymentDAO;

    public PaymentController() {
        this.paymentDAO = new PaymentDAO(); // Khởi tạo lớp kết nối database
    }

    // Hàm nhận nhiệm vụ tìm kiếm từ View truyền xuống
    public Object[] handleFindInvoice(String inputId) {
        try {
            return paymentDAO.findInvoiceByIdOrBookingId(inputId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Hàm cập nhật trạng thái IsPaid lên SQL Server
    public boolean updateInvoiceStatus(String invoiceId, int status) {
        try {
            return paymentDAO.updateStatus(invoiceId, status);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}