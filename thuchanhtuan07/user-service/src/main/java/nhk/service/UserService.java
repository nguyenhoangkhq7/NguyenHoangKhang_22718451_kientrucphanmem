package nhk.service;

import nhk.dto.RegisterRequest;
import nhk.dto.UserResponse;

import java.util.List;

public interface UserService {
    void registerUser(RegisterRequest request);
    List<UserResponse> getAllUsers();
    UserResponse getUserById(Long id);
}
