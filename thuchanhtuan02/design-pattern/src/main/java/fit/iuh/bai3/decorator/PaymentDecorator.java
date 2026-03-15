package fit.iuh.bai3.decorator;

import fit.iuh.bai3.strategy.PaymentStrategy;

public abstract class PaymentDecorator implements PaymentStrategy {
    protected PaymentStrategy wrappedPayment;

    public PaymentDecorator(PaymentStrategy wrappedPayment) {
        this.wrappedPayment = wrappedPayment;
    }
}
