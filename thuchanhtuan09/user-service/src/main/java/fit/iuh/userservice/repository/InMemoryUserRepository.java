package fit.iuh.userservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import fit.iuh.userservice.model.User;

@Repository
public class InMemoryUserRepository implements UserRepository {

    private final List<User> users = List.of(new User(1L, "Alice", "ACTIVE", "alice", "123456"), new User(2L, "Bob", "ACTIVE", "bob", "123456"));

    @Override
    public Optional<User> findById(Long id) {
        return users.stream().filter(user -> user.id().equals(id)).findFirst();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return users.stream().filter(user -> user.username().equals(username)).findFirst();
    }
}

