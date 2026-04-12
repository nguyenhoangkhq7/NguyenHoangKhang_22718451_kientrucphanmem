package nhk.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderItemResponse {
    private Long id;
    private Long foodId;
    private Integer quantity;
    private BigDecimal price;
}
