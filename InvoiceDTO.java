package model.dto;

public class InvoiceDTO {
    private String invoiceId;
    private String bookingId;
    private double totalAmount;
    private int isPaid;

    public InvoiceDTO(String invoiceId, String bookingId, double totalAmount, int isPaid) {
        this.invoiceId = invoiceId;
        this.bookingId = bookingId;
        this.totalAmount = totalAmount;
        this.isPaid = isPaid;
    }

    // Getters
    public String getInvoiceId() { return invoiceId; }
    public String getBookingId() { return bookingId; }
    public double getTotalAmount() { return totalAmount; }
    public int getIsPaid() { return isPaid; }
}