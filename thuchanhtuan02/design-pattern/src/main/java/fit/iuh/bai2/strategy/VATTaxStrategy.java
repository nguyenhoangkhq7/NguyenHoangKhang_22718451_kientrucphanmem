package fit.iuh.bai2.strategy;

public class VATTaxStrategy implements TaxStrategy {
    @Override
    public double calculateTax(double price) {
        return price * 0.10; // 10% VAT
    }
}
