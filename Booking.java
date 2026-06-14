package model.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Booking {
    private String bookingId;
    private Customer customer;
    private BanquetHall banquetHall;
    private LocalDate eventDate;
    private String shift;
    private String status; // PENDING, CONFIRMED, CANCELLED
    private final List<Dish> selectedDishes = new ArrayList<>();

    public Booking(String bookingId, Customer customer, BanquetHall banquetHall, LocalDate eventDate, String shift) {
        this.bookingId = bookingId;
        this.customer = customer;
        this.banquetHall = banquetHall;
        this.eventDate = eventDate;
        this.shift = shift;
        this.status = "PENDING";
    }

    public void addDish(Dish dish) { selectedDishes.add(dish); }

    public double calculateTotal() {
        return selectedDishes.stream().mapToDouble(Dish::getPrice).sum();
    }

    public double calculateDeposit() {
        return calculateTotal() * 0.3; // Đặt cọc 30% 
    }

    // Getters & Setters
    public String getBookingId() { return bookingId; }
    public Customer getCustomer() { return customer; }
    public BanquetHall getBanquetHall() { return banquetHall; }
    public LocalDate getEventDate() { return eventDate; }
    public String getShift() { return shift; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public List<Dish> getSelectedDishes() { return selectedDishes; }
}