package controller;

import model.entity.Booking;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class KitchenController {
    public List<Booking> getDailyServingList(List<Booking> totalBookings, LocalDate today) {
        return totalBookings.stream()
                .filter(b -> b.getEventDate().equals(today) && "CONFIRMED".equals(b.getStatus()))
                .collect(Collectors.toList());
    }
}