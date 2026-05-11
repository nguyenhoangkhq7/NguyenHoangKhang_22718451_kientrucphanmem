package fit.iuh.userservice.repository;

import java.util.Optional;

import fit.iuh.userservice.model.User;

public interface UserRepository {

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);
}

