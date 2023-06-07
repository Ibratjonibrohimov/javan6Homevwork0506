package uz.najottalim.homeworkforspring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.najottalim.homeworkforspring.dto.CommentsDto;
import uz.najottalim.homeworkforspring.excepton.NoResourceFoundException;
import uz.najottalim.homeworkforspring.models.Comments;
import uz.najottalim.homeworkforspring.repository.CommentsRepository;
import uz.najottalim.homeworkforspring.service.CommentsServices;
import uz.najottalim.homeworkforspring.service.mapper.CommentMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentsServiceImpl implements CommentsServices {
    private final CommentsRepository commentsRepository;
    private final CommentMapper commentMapper;

    @Override
    public ResponseEntity<?> addComment(CommentsDto commentsDto) {
        return ResponseEntity.ok(commentMapper.toDtoWithAuthorAndArticle(commentsRepository.save(commentMapper.toEntity(commentsDto))));
    }
    @Override
    public ResponseEntity<?> getAllComments() {
        List<Comments> results = commentsRepository.findAll();
        return ResponseEntity.ok(results.stream().map(commentMapper::toDtoWithAuthorAndArticle).collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<?> updateComment(Long id, CommentsDto commentsDto) {
        Optional<Comments> comment = commentsRepository.findById(id);
        if(comment.isEmpty())throw new NoResourceFoundException();
        commentsDto.setId(id);
        return ResponseEntity.ok(commentMapper.toDtoWithAuthorAndArticle(commentsRepository.save(commentMapper.toEntity(commentsDto))));
    }

    @Override
    public ResponseEntity<?> deleteCommnet(Long id) {
        Optional<Comments> comment = commentsRepository.findById(id);
        if(comment.isEmpty())throw new NoResourceFoundException();
        commentsRepository.deleteById(id);
        return ResponseEntity.ok(commentMapper.toDtoWithAuthorAndArticle(comment.get()));
    }
}
