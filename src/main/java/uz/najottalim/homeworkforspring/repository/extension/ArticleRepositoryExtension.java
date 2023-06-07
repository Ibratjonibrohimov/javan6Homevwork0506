package uz.najottalim.homeworkforspring.repository.extension;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import uz.najottalim.homeworkforspring.models.Article;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ArticleRepositoryExtension {

    List<Article> getByFilter(Optional<String> title, Optional<List<String>> body, Optional<Long> userId, Optional<LocalDate> minDate, Optional<LocalDate> maxDate);

    List<Article> getArticlesOfUser(Long id, Optional<String> sortBy, Optional<Integer> pageNum, Optional<Integer> pageSize);
}
