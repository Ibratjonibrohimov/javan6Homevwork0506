package uz.najottalim.homeworkforspring.service;
import org.springframework.http.ResponseEntity;
import uz.najottalim.homeworkforspring.dto.ArticlesDto;
import uz.najottalim.homeworkforspring.dto.MostActiveUsersDto;
import uz.najottalim.homeworkforspring.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {

    ResponseEntity<UserDto> addUser(UserDto userDto);

    ResponseEntity<UserDto> getUser(Long id);

    ResponseEntity<UserDto> updateUser(Long id,UserDto userDto);

    ResponseEntity<List<UserDto>> getAllUsers();

    ResponseEntity<UserDto> deleteUser(Long id);

    ResponseEntity<List<ArticlesDto>> getArticlesOfUser(Long id, Optional<String> sortBy, Optional<Integer> pageNum, Optional<Integer> pageSize);

    ResponseEntity<List<MostActiveUsersDto>> getMostActive(Integer n);
}

