package uz.najottalim.homeworkforspring.repository.extension;

import uz.najottalim.homeworkforspring.models.Comments;

import javax.xml.stream.events.Comment;
import java.util.List;
import java.util.Optional;

public interface CommentsRepositoryExtension {
    List<Comments> getCommentsOfArticle(Long id, Optional<String> sortBy, Optional<Integer> pageNum, Optional<Integer> pageSize);
}
