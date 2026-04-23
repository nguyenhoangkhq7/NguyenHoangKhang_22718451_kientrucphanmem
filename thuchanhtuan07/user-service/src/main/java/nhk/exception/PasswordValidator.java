package nhk.exception;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public void initialize(ValidPassword annotation) {
        // No initialization needed
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return false;
        }

        // Password requirements:
        // - At least 6 characters
        // - At least one uppercase letter
        // - At least one number
        // - At least one special character
        boolean isValidLength = password.length() >= 6;
        boolean hasUppercase = password.matches(".*[A-Z].*");
        boolean hasNumber = password.matches(".*[0-9].*");
        boolean hasSpecialChar = password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*");

        return isValidLength && hasUppercase && hasNumber && hasSpecialChar;
    }
}
