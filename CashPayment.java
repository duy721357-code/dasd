package model.Strategy;

public class CashPayment implements IPayment {
    @Override
    public boolean processPayment(double amount) {
        System.out.printf("[Thanh toán Tiền mặt] Đã nhận số tiền: %,.0f VNĐ.%n", amount);
        return true;
    }
}