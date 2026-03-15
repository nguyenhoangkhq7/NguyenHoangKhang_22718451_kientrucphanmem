package fit.iuh.bai1.decorator;

public class BasicOrderService implements OrderService {
    @Override
    public String getDescription() {
        return "Đơn hàng cơ bản";
    }

    @Override
    public double getCost() {
        return 100.0;
    }
}
