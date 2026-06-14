package model.entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class BanquetHall {
    private String hallId;
    private String name;
    private int capacity;
    private double basePrice;
    private String status; // Available, Maintenance
    private final Set<String> bookedSlots = new HashSet<>();

    public BanquetHall(String hallId, String name, int capacity, double basePrice) {
        this.hallId = hallId;
        this.name = name;
        this.capacity = capacity;
        this.basePrice = basePrice;
        this.status = "Available";
    }

    public boolean checkAvailability(LocalDate date, String shift) {
        if ("Maintenance".equalsIgnoreCase(this.status)) return false; 
        String key = date.toString() + "_" + shift.toUpperCase();
        return !bookedSlots.contains(key);
    }

    public void bookSlot(LocalDate date, String shift) {
        bookedSlots.add(date.toString() + "_" + shift.toUpperCase());
    }

    public void releaseSlot(LocalDate date, String shift) {
        bookedSlots.remove(date.toString() + "_" + shift.toUpperCase());
    }

    public String getName() { return name; }
    public void setStatus(String status) { this.status = status; }
}