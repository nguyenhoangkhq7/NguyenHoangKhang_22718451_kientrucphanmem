package fit.iuh.bai1.decorator;

public class GiftWrapDecorator extends OrderServiceDecorator {
    public GiftWrapDecorator(OrderService decoratedOrder) {
        super(decoratedOrder);
    }

    @Override
    public String getDescription() {
        return decoratedOrder.getDescription() + " + Gói quà đặc biệt (-$15.0)";
    }

    @Override
    public double getCost() {
        return decoratedOrder.getCost() + 15.0;
    }
}
