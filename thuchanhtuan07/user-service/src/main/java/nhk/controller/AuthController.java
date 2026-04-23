package nhk.controller;

import jakarta.validation.Valid;
import nhk.dto.ApiResponse;
import nhk.dto.JwtResponse;
import nhk.dto.LoginRequest;
import nhk.dto.RegisterRequest;
import nhk.security.JwtUtils;
import nhk.security.UserDetailsImpl;
import nhk.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            log.info("User login attempt: {}", loginRequest.getUsername());

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            String role = userDetails.getAuthorities().stream()
                    .findFirst()
                    .map(item -> item.getAuthority())
                    .orElse(null);

            JwtResponse jwtResponse = new JwtResponse(jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    role);

            log.info("User {} logged in successfully", loginRequest.getUsername());
            return ResponseEntity.ok(ApiResponse.success("User logged in successfully", jwtResponse));

        } catch (BadCredentialsException e) {
            log.warn("Invalid credentials for user: {}", loginRequest.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error("Invalid username or password"));
        } catch (Exception e) {
            log.error("Login error", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error("Authentication failed: " + e.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        try {
            log.info("User registration attempt: {}", registerRequest.getUsername());
            userService.registerUser(registerRequest);
            log.info("User {} registered successfully", registerRequest.getUsername());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("User registered successfully"));
        } catch (Exception e) {
            log.error("Registration error", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Registration failed: " + e.getMessage()));
        }
    }
}
