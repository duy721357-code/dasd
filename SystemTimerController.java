package controller;

import model.entity.Booking;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SystemTimerController {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1); 

    public void registerTimeoutMonitor(Booking booking) {
        // Cấu hình giả lập 5 giây cho demo (Trong thực tế là 24, TimeUnit.HOURS) [cite: 2408]
        scheduler.schedule(() -> {
            if ("PENDING".equalsIgnoreCase(booking.getStatus())) { 
                booking.setStatus("CANCELLED"); 
                booking.getBanquetHall().releaseSlot(booking.getEventDate(), booking.getShift()); 
                System.out.printf("%n[⚠️ SYSTEM TIMER OVERFLOW] Đơn %s quá hạn 24 giờ không nộp cọc. Đơn tự động hủy, giải phóng tài nguyên sảnh!%n", 
                        booking.getBookingId()); 
            }
        }, 5, TimeUnit.SECONDS);
    }
}