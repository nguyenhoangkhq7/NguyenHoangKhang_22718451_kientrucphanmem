package fit.iuh.bai2.strategy;

public class LuxuryTaxStrategy implements TaxStrategy {
    @Override
    public double calculateTax(double price) {
        return price * 0.20; // 20% thuế xa xỉ
    }
}
