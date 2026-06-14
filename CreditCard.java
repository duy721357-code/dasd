package model.Strategy;

public class CreditCard implements IPayment {
    @Override
    public boolean processPayment(double amount) {
        System.out.printf("[Thanh toán Thẻ] Thao tác trừ tiền cổng ngân hàng thành công: %,.0f VNĐ.%n", amount);
        return true;
    }
}