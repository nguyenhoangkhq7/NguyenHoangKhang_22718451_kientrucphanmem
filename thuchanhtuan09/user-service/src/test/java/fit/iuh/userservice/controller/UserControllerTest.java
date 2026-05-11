package fit.iuh.userservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import fit.iuh.userservice.dto.UserDto;
import fit.iuh.userservice.exception.RestExceptionHandler;
import fit.iuh.userservice.exception.UserNotFoundException;
import fit.iuh.userservice.model.User;
import fit.iuh.userservice.repository.UserRepository;
import fit.iuh.userservice.service.UserService;
import org.junit.jupiter.api.Test;

class UserControllerTest {

    @Test
    void getUserReturnsDto() {
        var controller = new UserController(new UserService(new UserRepository() {
            @Override
            public Optional<User> findById(Long id) {
                return Optional.of(new User(id, "Alice", "ACTIVE", "alice", "123456"));
            }

            @Override
            public Optional<User> findByUsername(String username) {
                return Optional.empty();
            }
        }));

        var user = controller.getUser(1L);

        assertEquals(1L, user.id());
        assertEquals("Alice", user.name());
        assertEquals("ACTIVE", user.status());
    }

    @Test
    void getUserPropagatesNotFound() {
        var controller = new UserController(new UserService(new UserRepository() {
            @Override
            public Optional<User> findById(Long id) {
                return Optional.empty();
            }

            @Override
            public Optional<User> findByUsername(String username) {
                return Optional.empty();
            }
        }));

        assertThrows(UserNotFoundException.class, () -> controller.getUser(99L));
    }

    @Test
    void handlerReturns404() {
        assertEquals(404, new RestExceptionHandler().handleUserNotFound().getStatusCode().value());
    }
}




