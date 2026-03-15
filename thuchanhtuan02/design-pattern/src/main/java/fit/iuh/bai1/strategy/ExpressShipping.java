package fit.iuh.bai1.strategy;

public class ExpressShipping implements ShippingStrategy {
    @Override
    public void ship() {
        System.out.println("Vận chuyển hỏa tốc: 1 ngày.");
    }
}
