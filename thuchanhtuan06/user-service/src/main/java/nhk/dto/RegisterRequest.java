package nhk.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nhk.exception.ValidPassword;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    
    @NotBlank(message = "Username cannot be blank")
    private String username;

    @NotBlank(message = "Password cannot be blank")
    @ValidPassword(message = "Password must be at least 6 characters long and contain at least one uppercase letter, one number, and one special character")
    private String password;
}
