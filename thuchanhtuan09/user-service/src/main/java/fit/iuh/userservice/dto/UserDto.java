package fit.iuh.userservice.dto;

import fit.iuh.userservice.model.User;

public record UserDto(Long id, String name, String status) {

    public static UserDto from(User user) {
        return new UserDto(user.id(), user.name(), user.status());
    }
}

