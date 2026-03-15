package fit.iuh.bai2.strategy;

public class ConsumptionTaxStrategy implements TaxStrategy {
    @Override
    public double calculateTax(double price) {
        return price * 0.05; // 5% thuế tiêu thụ
    }
}
