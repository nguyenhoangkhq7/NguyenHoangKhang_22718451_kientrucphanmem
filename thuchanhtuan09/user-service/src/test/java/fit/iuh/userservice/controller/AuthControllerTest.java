package fit.iuh.userservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import fit.iuh.userservice.dto.LoginRequest;
import fit.iuh.userservice.exception.RestExceptionHandler;
import fit.iuh.userservice.exception.InvalidCredentialsException;
import fit.iuh.userservice.repository.InMemoryUserRepository;
import fit.iuh.userservice.service.AuthService;
import org.junit.jupiter.api.Test;

class AuthControllerTest {

    @Test
    void loginReturnsResponse() {
        var controller = new AuthController(new AuthService(new InMemoryUserRepository()));

        var response = controller.login(new LoginRequest("alice", "123456"));

        assertEquals(1L, response.id());
        assertEquals("Alice", response.name());
        assertEquals("ACTIVE", response.status());
        assertEquals("alice", response.username());
    }

    @Test
    void loginThrowsWhenCredentialsInvalid() {
        var controller = new AuthController(new AuthService(new InMemoryUserRepository()));

        assertThrows(InvalidCredentialsException.class, () -> controller.login(new LoginRequest("alice", "wrong")));
    }

    @Test
    void handlerReturns401() {
        assertEquals(401, new RestExceptionHandler().handleInvalidCredentials().getStatusCode().value());
    }
}



