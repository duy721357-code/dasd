package controller;

import model.entity.Invoice;
import java.util.List;

public class ReportController {
    public double getAggregateRevenue(List<Invoice> invoiceList) {
        return invoiceList.stream()
                .filter(Invoice::isPaid)
                .mapToDouble(Invoice::getTotalAmount) 
                .sum(); 
    }
}