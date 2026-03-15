package fit.iuh.bai3.strategy;

public interface PaymentStrategy {
    double pay(double amount);
    String getMethodName();
}
