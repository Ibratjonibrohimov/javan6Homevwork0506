package uz.najottalim.homeworkforspring.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.najottalim.homeworkforspring.dto.ArticlesDto;
import uz.najottalim.homeworkforspring.dto.MostActiveUsersDto;
import uz.najottalim.homeworkforspring.dto.UserDto;
import uz.najottalim.homeworkforspring.service.UserService;

import java.time.OffsetTime;
import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping()
    private ResponseEntity<UserDto> addUser(@Valid @RequestBody UserDto userDto){
        return userService.addUser(userDto);
    }

    @GetMapping({"/{id}"}) ResponseEntity<UserDto> getUser(@PathVariable Long id){
        return userService.getUser(id);
    }
    @PutMapping("/{id}") ResponseEntity<UserDto> updateUser(@PathVariable Long id,@RequestBody UserDto userDto){
        return userService.updateUser(id,userDto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable Long id){
        return userService.deleteUser(id);
    }

    @GetMapping("/{id}/articles")
    public ResponseEntity<List<ArticlesDto>> getArticlesOfUser(@PathVariable Long id,
                                                               @RequestParam Optional<String> sortBy,
                                                               @RequestParam Optional<Integer> pageNum,
                                                               @RequestParam Optional<Integer> pageSize)
    {
        return userService.getArticlesOfUser(id,sortBy,pageNum,pageSize);
    }

    @GetMapping("most-active/{n}")
    public ResponseEntity<List<MostActiveUsersDto>> getMostActive(@PathVariable Integer n){
        return userService.getMostActive(n);
    }
}
