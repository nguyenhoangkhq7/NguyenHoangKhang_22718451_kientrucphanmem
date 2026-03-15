package fit.iuh.bai3.strategy;

public class PayPalPayment implements PaymentStrategy {
    @Override
    public double pay(double amount) {
        System.out.println("Đang kết nối qua cổng PayPal...");
        return amount;
    }

    @Override
    public String getMethodName() {
        return "PayPal";
    }
}
