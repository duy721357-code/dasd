package model.entity;

import model.observer.IObserver;
import model.observer.ISubject;
import java.util.ArrayList;
import java.util.List;

public class Invoice implements ISubject {
    private String invoiceId;
    private Booking booking;
    private double totalAmount;
    private boolean isPaid;
    private final List<IObserver> observers = new ArrayList<>();

    public Invoice(String invoiceId, Booking booking) {
        this.invoiceId = invoiceId;
        this.booking = booking;
        this.totalAmount = booking.calculateTotal();
        this.isPaid = false;
        registerObserver(booking.getCustomer()); // Khách hàng đăng ký lắng nghe hóa đơn này [cite: 2446]
    }

    @Override
    public void registerObserver(IObserver o) { observers.add(o); }
    @Override
    public void removeObserver(IObserver o) { observers.remove(o); }
    @Override
    public void notifyObservers(String msg) {
        observers.forEach(o -> o.update(msg)); 
    }

    public void setPaid(boolean paid) { 
        this.isPaid = paid; 
        if(paid) {
            notifyObservers("Hóa đơn " + invoiceId + " đã hoàn tất thanh toán. Đơn hàng chính thức CONFIRMED!"); 
        }
    }

    public double getTotalAmount() { return totalAmount; }
    public String getInvoiceId() { return invoiceId; }
    public boolean isPaid() { return isPaid; }
}