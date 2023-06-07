package uz.najottalim.homeworkforspring.service;

import org.springframework.http.ResponseEntity;
import uz.najottalim.homeworkforspring.dto.CommentsDto;

import java.util.List;

public interface CommentsServices {

    ResponseEntity<?> addComment(CommentsDto commentsDto);
    ResponseEntity<?> getAllComments();

    ResponseEntity<?> updateComment(Long id, CommentsDto commentsDto);

    ResponseEntity<?> deleteCommnet(Long id);
}
