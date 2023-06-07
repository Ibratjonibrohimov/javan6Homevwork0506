package uz.najottalim.homeworkforspring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.najottalim.homeworkforspring.aop.CommentsController;
import uz.najottalim.homeworkforspring.dto.CommentsDto;
import uz.najottalim.homeworkforspring.service.CommentsServices;

@RestController
@RequestMapping("comments")
@RequiredArgsConstructor
@CommentsController
public class CommentsResources {
    private final CommentsServices commentsServices;
    @GetMapping()
    public ResponseEntity<?> getAllComments(){
        return commentsServices.getAllComments();
    }

    @PostMapping
    @Valid
    public ResponseEntity<?> addComment(@RequestBody CommentsDto commentsDto){
        return commentsServices.addComment(commentsDto);
    }

    @PutMapping("/{id}")
    @Valid
    public ResponseEntity<?> updateComment(@PathVariable Long id,@RequestBody CommentsDto commentsDto){
        return commentsServices.updateComment(id,commentsDto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id){
        return commentsServices.deleteCommnet(id);
    }

}
