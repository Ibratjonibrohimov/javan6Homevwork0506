package uz.najottalim.homeworkforspring.service.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uz.najottalim.homeworkforspring.dto.CommentsDto;
import uz.najottalim.homeworkforspring.models.Comments;

import javax.xml.stream.events.Comment;

@Component
@RequiredArgsConstructor
public class CommentMapper {
    protected final UserMapper userMapper;
    protected final ArticleMapper articleMapper;

    public Comments toEntity(CommentsDto commentsDto) {
        return new Comments(commentsDto.getId(),
                commentsDto.getBody(),
                userMapper.toEntity(commentsDto.getAuthor()),
                articleMapper.toEntity(commentsDto.getArticle()),
                commentsDto.getPublishDate(),
                commentsDto.getUpdatedAt()
        );
    }

    public CommentsDto toDto(Comments comment) {
        return new CommentsDto(comment.getId(),comment.getBody(),userMapper.toDtoForComments(comment.getUser()));
    }

    public CommentsDto toDtoWithAuthorAndArticle(Comments comments){
        return new CommentsDto(comments.getId(),comments.getBody(),userMapper.toDto(comments.getUser()),articleMapper.toDto(comments.getArticle()),comments.getPublishDate(),comments.getUpdatedAt());
    }
}
