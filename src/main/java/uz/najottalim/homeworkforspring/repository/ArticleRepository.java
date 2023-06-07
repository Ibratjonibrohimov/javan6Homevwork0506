package uz.najottalim.homeworkforspring.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.najottalim.homeworkforspring.dto.MostCommentArticles;
import uz.najottalim.homeworkforspring.models.Article;
import uz.najottalim.homeworkforspring.repository.extension.ArticleRepositoryExtension;

import java.time.LocalDate;
import java.util.*;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> , ArticleRepositoryExtension {

    @Query(
                    value = "select a.id,a.title,a.body,a.user_id as userId ,a.publish_date as publishDate,a.updated_at as updatedAt from " +
                            "articles a " +
                            "join comments c " +
                            "on a.id=c.article_id " +
                            "where a.publish_date>=#{#check_date}" +
                            "group by a.id " +
                            "order by count(a.id) desc " +
                            "limit 10",nativeQuery = true
    )
    List<MostCommentArticles> findMostCommentAfterCheckDate(@Param("check_date")Date checkDate);
    @Query(
            value = "select a.* from " +
                    "articles a " +
                    "join comments c " +
                    "on a.id=c.article_id " +
                    "group by a.id " +
                    "order by count(a.id) desc " +
                    "limit 10",nativeQuery = true
    )
    List<MostCommentArticles> findMostComment();

    @Override
    List<Article> findAll();
    @Override
    List<Article> findAll(Sort sort);
    Page<Article> findAll(Pageable pageable);
}
