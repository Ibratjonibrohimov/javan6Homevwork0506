package uz.najottalim.homeworkforspring.service;

import org.springframework.http.ResponseEntity;
import uz.najottalim.homeworkforspring.dto.ArticlesDto;
import uz.najottalim.homeworkforspring.dto.CommentsDto;
import uz.najottalim.homeworkforspring.dto.MostCommentArticles;

import java.time.LocalDate;
import java.util.*;


public interface ArticleServices {

    ResponseEntity<ArticlesDto> addArticle(ArticlesDto articlesDto);
    ResponseEntity<?> deleteArticleById(Long id);
    ResponseEntity<?> getArticleById(Long id);
    ResponseEntity<?> getAllArticles();
    ResponseEntity<ArticlesDto> editArticle(ArticlesDto articlesDto);

    ResponseEntity<List<MostCommentArticles>> getMostCommented(Optional<String> checkDate);

    ResponseEntity<List<ArticlesDto>> getBYSortandPage(Optional<String> sortBy, Optional<Integer> pageNum, Optional<Integer> pageSize);

    ResponseEntity<List<ArticlesDto>> getByFilter(Optional<String> title, Optional<List<String>> body, Optional<Long> userId, Optional<LocalDate> minDate, Optional<LocalDate> maxDate);

    ResponseEntity<List<CommentsDto>> getCommentsOfArticle(Long id, Optional<String> sortBy, Optional<Integer> pageNum, Optional<Integer> pageSize);
}
