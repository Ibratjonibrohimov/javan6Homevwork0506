package uz.najottalim.homeworkforspring.service.mapper;

import org.springframework.stereotype.Component;
import uz.najottalim.homeworkforspring.dto.UserDto;
import uz.najottalim.homeworkforspring.models.User;

@Component
public class UserMapper {
    public User toEntity(UserDto dto) {
        return new User(dto.getId(), dto.getUsername(), dto.getPassword(), dto.getEmail());
    }

    public UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getPassword(), user.getEmail());
    }

    public UserDto toDtoForComments(User user){
        return new UserDto(user.getId(), user.getUsername());
    }
}
