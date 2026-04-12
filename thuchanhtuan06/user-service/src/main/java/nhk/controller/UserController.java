package nhk.controller;

import nhk.dto.ApiResponse;
import nhk.dto.UserResponse;
import nhk.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        try {
            log.info("Fetching all users");
            List<UserResponse> users = userService.getAllUsers();
            log.info("Successfully fetched {} users", users.size());
            return ResponseEntity.ok(ApiResponse.success("Users retrieved successfully", users));
        } catch (Exception e) {
            log.error("Error fetching users", e);
            return ResponseEntity.status(500)
                    .body(ApiResponse.error("Failed to retrieve users: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long id) {
        try {
            log.info("Fetching user with id: {}", id);
            UserResponse user = userService.getUserById(id);
            log.info("Successfully fetched user: {}", user.getUsername());
            return ResponseEntity.ok(ApiResponse.success("User retrieved successfully", user));
        } catch (Exception e) {
            log.error("Error fetching user with id {}: {}", id, e.getMessage());
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.status(404)
                        .body(ApiResponse.error("User not found"));
            }
            return ResponseEntity.status(500)
                    .body(ApiResponse.error("Failed to retrieve user: " + e.getMessage()));
        }
    }
}
