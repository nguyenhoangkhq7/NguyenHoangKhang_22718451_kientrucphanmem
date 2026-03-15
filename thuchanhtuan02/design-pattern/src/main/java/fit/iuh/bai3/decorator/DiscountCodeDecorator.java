package fit.iuh.bai3.decorator;

import fit.iuh.bai3.strategy.PaymentStrategy;

public class DiscountCodeDecorator extends PaymentDecorator {
    public DiscountCodeDecorator(PaymentStrategy wrappedPayment) {
        super(wrappedPayment);
    }

    @Override
    public double pay(double amount) {
        double currentAmount = wrappedPayment.pay(amount);
        double discount = currentAmount * 0.10; // Giảm 10%
        System.out.println("- Áp dụng Mã giảm giá (10%): -$" + discount);
        return currentAmount - discount;
    }

    @Override
    public String getMethodName() {
        return wrappedPayment.getMethodName() + " (+Mã giảm giá)";
    }
}
