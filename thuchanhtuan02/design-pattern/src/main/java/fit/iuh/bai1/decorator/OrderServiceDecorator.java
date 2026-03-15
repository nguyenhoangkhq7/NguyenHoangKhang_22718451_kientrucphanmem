package fit.iuh.bai1.decorator;

public abstract class OrderServiceDecorator implements OrderService {
    protected OrderService decoratedOrder;

    public OrderServiceDecorator(OrderService decoratedOrder) {
        this.decoratedOrder = decoratedOrder;
    }

    @Override
    public String getDescription() {
        return decoratedOrder.getDescription();
    }

    @Override
    public double getCost() {
        return decoratedOrder.getCost();
    }
}
