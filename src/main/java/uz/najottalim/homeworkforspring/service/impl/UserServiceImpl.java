package uz.najottalim.homeworkforspring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.najottalim.homeworkforspring.dto.ArticlesDto;
import uz.najottalim.homeworkforspring.dto.MostActiveUsersDto;
import uz.najottalim.homeworkforspring.dto.UserDto;
import uz.najottalim.homeworkforspring.excepton.NoResourceFoundException;
import uz.najottalim.homeworkforspring.models.Article;
import uz.najottalim.homeworkforspring.models.User;
import uz.najottalim.homeworkforspring.repository.ArticleRepository;
import uz.najottalim.homeworkforspring.repository.UserRepository;
import uz.najottalim.homeworkforspring.service.UserService;
import uz.najottalim.homeworkforspring.service.mapper.ArticleMapper;
import uz.najottalim.homeworkforspring.service.mapper.UserMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;


    public ResponseEntity<UserDto> addUser(UserDto userDto) {
        return ResponseEntity.ok(userMapper.toDto(userRepository.save(userMapper.toEntity(userDto))));
    }

    public ResponseEntity<UserDto> getUser(Long id) {
        Optional<User> byId = userRepository.findById(id);
        if(byId.isEmpty()) throw new NoResourceFoundException();
        return ResponseEntity.ok(userMapper.toDto(byId.get()));
    }

    public ResponseEntity<UserDto> updateUser(Long id,UserDto userDto) {
        ResponseEntity<UserDto> user = getUser(id);
        userDto.setId(id);
        User update = userRepository.save(userMapper.toEntity(userDto));
        return ResponseEntity.ok(userMapper.toDto(update));
    }

    @Override
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll()
                .stream()
                .map(user -> userMapper.toDto(user))
                .collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<UserDto> deleteUser(Long id) {
        ResponseEntity<UserDto> delete = getUser(id);
        userRepository.deleteById(id);
        return ResponseEntity.ok(delete.getBody());
    }

    @Override
    public ResponseEntity<List<ArticlesDto>> getArticlesOfUser(Long id, Optional<String> sortBy, Optional<Integer> pageNum, Optional<Integer> pageSize) {
        List<Article> articles = articleRepository.getArticlesOfUser(id,sortBy,pageNum,pageSize);
        return ResponseEntity.ok(articles.stream().map(articleMapper::toDto).collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<List<MostActiveUsersDto>> getMostActive(Integer n) {
        return ResponseEntity.ok(userRepository.getMostActive(n));
        }
}
