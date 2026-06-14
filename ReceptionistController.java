package controller;

import model.dao.BookingDAO;

public class ReceptionistController {
    private BookingDAO bookingDAO;

    public ReceptionistController() {
        this.bookingDAO = new BookingDAO();
    }

    // Xử lý nghiệp vụ duyệt đơn cọc
    public String processConfirmation(String bookingId) {
        if (bookingId == null || bookingId.trim().isEmpty()) {
            return "EMPTY_ID";
        }
        try {
            String currentStatus = bookingDAO.checkStatus(bookingId);
            if (currentStatus == null) {
                return "NOT_FOUND";
            }
            if (currentStatus.equalsIgnoreCase("CONFIRMED")) {
                return "ALREADY_CONFIRMED";
            }
            if (currentStatus.equalsIgnoreCase("CANCELLED")) {
                return "IS_CANCELLED";
            }

            // Tiến hành cập nhật
            boolean isSuccess = bookingDAO.confirmBooking(bookingId);
            return isSuccess ? "SUCCESS" : "FAILED";
            
        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR";
        }
    }
}