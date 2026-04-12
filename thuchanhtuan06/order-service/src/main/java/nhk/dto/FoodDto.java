package nhk.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FoodDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private String description;
}
