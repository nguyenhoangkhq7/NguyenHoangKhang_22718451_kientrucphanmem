package nhk.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateOrderStatusResponse {
    private String message;
    private Long orderId;
    private String oldStatus;
    private String newStatus;
}
