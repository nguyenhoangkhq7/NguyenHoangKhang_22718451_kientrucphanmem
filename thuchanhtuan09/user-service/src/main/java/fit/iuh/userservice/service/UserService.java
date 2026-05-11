package fit.iuh.userservice.service;

import org.springframework.stereotype.Service;

import fit.iuh.userservice.dto.UserDto;
import fit.iuh.userservice.exception.UserNotFoundException;
import fit.iuh.userservice.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto getUserById(Long id) {
        return userRepository.findById(id).map(UserDto::from).orElseThrow(UserNotFoundException::new);
    }
}

