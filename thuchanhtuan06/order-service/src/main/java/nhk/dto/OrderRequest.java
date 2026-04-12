package nhk.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotEmpty(message = "Food IDs cannot be empty")
    private List<Long> foodIds;
}
