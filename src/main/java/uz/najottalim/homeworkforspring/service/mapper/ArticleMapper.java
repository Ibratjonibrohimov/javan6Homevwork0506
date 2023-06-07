package uz.najottalim.homeworkforspring.service.mapper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import uz.najottalim.homeworkforspring.dto.ArticlesDto;
import uz.najottalim.homeworkforspring.excepton.NoResourceFoundException;
import uz.najottalim.homeworkforspring.models.Article;
import uz.najottalim.homeworkforspring.models.User;
import uz.najottalim.homeworkforspring.repository.ArticleRepository;
import uz.najottalim.homeworkforspring.repository.UserRepository;

import java.util.Optional;


@Component
@RequiredArgsConstructor
public class ArticleMapper {
    private final UserRepository userRepository;
    public Article toEntity(ArticlesDto articlesDto) {
        Optional<User> user = userRepository.findById(articlesDto.getUser_id());
        if(user.isEmpty()) throw new NoResourceFoundException();
        return new Article(articlesDto.getId(),articlesDto.getTitle(),articlesDto.getBody(),user.get(),articlesDto.getPublishDate(),articlesDto.getUpdatedAt());
    }

    public ArticlesDto toDto(Article article) {
        return new ArticlesDto(article.getId(), article.getTitle(), article.getBody(), article.getUser().getId(), article.getPublishDate(), article.getUpdatedAt());
    }

}
