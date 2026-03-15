package fit.iuh.bai1.strategy;

public class StandardShipping implements ShippingStrategy {
    @Override
    public void ship() {
        System.out.println("Vận chuyển tiêu chuẩn: 3-5 ngày.");
    }
}
