package fit.iuh.bai2.state;

import fit.iuh.bai2.context.ProductContext;

public interface ProductState {
    void handleTax(ProductContext product);
}
