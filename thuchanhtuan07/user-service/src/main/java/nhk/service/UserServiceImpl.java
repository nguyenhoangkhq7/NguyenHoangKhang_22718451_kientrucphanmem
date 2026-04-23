package nhk.service;

import nhk.dto.RegisterRequest;
import nhk.dto.UserResponse;
import nhk.entity.Role;
import nhk.entity.User;
import nhk.exception.UserAlreadyExistsException;
import nhk.exception.UserNotFoundException;
import nhk.messaging.UserEventPublisherPort;
import nhk.messaging.UserRegisteredEvent;
import nhk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserEventPublisherPort userEventPublisher;

    @Override
    public void registerUser(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException("Error: Username '" + request.getUsername() + "' is already taken!");
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .build();

          User savedUser = userRepository.save(user);
          userEventPublisher.publishUserRegistered(new UserRegisteredEvent(
              "USER_REGISTERED",
              savedUser.getId(),
              savedUser.getUsername(),
              savedUser.getRole(),
              LocalDateTime.now()
          ));
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserResponse(user.getId(), user.getUsername(), user.getRole()))
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
        return new UserResponse(user.getId(), user.getUsername(), user.getRole());
    }
}
