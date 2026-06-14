package controller;

import model.entity.Booking;
import java.util.ArrayList;
import java.util.List;

public class BookingManager {
    private final List<Booking> allBookings = new ArrayList<>();

    public void addBooking(Booking b) { allBookings.add(b); }
    public List<Booking> getAllBookings() { return allBookings; }

    public boolean isDishSafeToDelete(String dishId) {
        // Kiểm tra xem món ăn có đang nằm trong bất kỳ đơn Active nào không [cite: 2417, 2536]
        return allBookings.stream()
                .filter(b -> "PENDING".equals(b.getStatus()) || "CONFIRMED".equals(b.getStatus())) 
                .flatMap(b -> b.getSelectedDishes().stream())
                .noneMatch(d -> d.getDishId().equalsIgnoreCase(dishId)); 
    }
}