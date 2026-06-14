package controller;

import model.dao.CustomerDAO;
import java.util.List;

public class BookingController {
    private CustomerDAO customerDAO;

    public BookingController() {
        this.customerDAO = new CustomerDAO();
    }

    public List<Object[]> handleLoadAllHalls() throws Exception {
        return customerDAO.getAllHalls();
    }

    public List<Object[]> handleLoadAllDishes() throws Exception {
        return customerDAO.getAllDishes();
    }

    public List<Object[]> handleSearchAvailableHalls(String date, String shift) throws Exception {
        if (date == null || date.trim().isEmpty()) {
            throw new IllegalArgumentException("DATE_EMPTY");
        }
        return customerDAO.getAvailableHalls(date, shift);
    }

    // Nghiệp vụ tính toán 30% tiền cọc dựa trên chuỗi giá thô (đã dọn sạch dấu chấm/phẩy)
    public double calculateDeposit(String priceStr) {
        String cleanPrice = priceStr.replace(".", "").replace(",", "").trim();
        double basePrice = Double.parseDouble(cleanPrice);
        return basePrice * 0.3;
    }
    
    public double parseBasePrice(String priceStr) {
        String cleanPrice = priceStr.replace(".", "").replace(",", "").trim();
        return Double.parseDouble(cleanPrice);
    }
}