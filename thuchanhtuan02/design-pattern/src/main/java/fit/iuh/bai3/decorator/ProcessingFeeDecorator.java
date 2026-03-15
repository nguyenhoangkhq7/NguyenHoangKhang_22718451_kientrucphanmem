package fit.iuh.bai3.decorator;

import fit.iuh.bai3.strategy.PaymentStrategy;

public class ProcessingFeeDecorator extends PaymentDecorator {
    public ProcessingFeeDecorator(PaymentStrategy wrappedPayment) {
        super(wrappedPayment);
    }

    @Override
    public double pay(double amount) {
        double currentAmount = wrappedPayment.pay(amount);
        double fee = currentAmount * 0.02; // Phí xử lý 2%
        System.out.println("+ Áp dụng Phí xử lý (2%): $" + fee);
        return currentAmount + fee;
    }

    @Override
    public String getMethodName() {
        return wrappedPayment.getMethodName() + " (+Phí xử lý)";
    }
}
