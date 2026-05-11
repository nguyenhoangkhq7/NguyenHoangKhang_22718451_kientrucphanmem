package fit.iuh.userservice.service;

import org.springframework.stereotype.Service;

import fit.iuh.userservice.dto.LoginRequest;
import fit.iuh.userservice.dto.LoginResponse;
import fit.iuh.userservice.exception.InvalidCredentialsException;
import fit.iuh.userservice.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LoginResponse login(LoginRequest request) {
        return userRepository.findByUsername(request.username())
                .filter(user -> user.password().equals(request.password()))
                .map(user -> new LoginResponse(user.id(), user.name(), user.status(), user.username()))
                .orElseThrow(InvalidCredentialsException::new);
    }
}


