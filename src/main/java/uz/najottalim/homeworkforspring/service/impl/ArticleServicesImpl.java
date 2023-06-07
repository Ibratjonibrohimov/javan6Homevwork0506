package uz.najottalim.homeworkforspring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.najottalim.homeworkforspring.dto.ArticlesDto;
import uz.najottalim.homeworkforspring.dto.CommentsDto;
import uz.najottalim.homeworkforspring.dto.MostCommentArticles;
import uz.najottalim.homeworkforspring.models.Article;
import uz.najottalim.homeworkforspring.models.Comments;
import uz.najottalim.homeworkforspring.repository.ArticleRepository;
import uz.najottalim.homeworkforspring.repository.CommentsRepository;
import uz.najottalim.homeworkforspring.service.ArticleServices;
import uz.najottalim.homeworkforspring.service.mapper.ArticleMapper;
import uz.najottalim.homeworkforspring.service.mapper.CommentMapper;

import javax.xml.stream.events.Comment;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleServicesImpl implements ArticleServices {

    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;
    private final CommentsRepository commentsRepository;
    private final CommentMapper commentMapper;


    @Override
    public ResponseEntity<ArticlesDto> addArticle(ArticlesDto articlesDto) {
        Article article = articleMapper.toEntity(articlesDto);
        try {
            return ResponseEntity
                    .ok()
                    .body(
                            articleMapper.toDto(
                                    articleRepository.save(article)));
        } catch (Exception e) {
            throw new RuntimeException("Error while saving article to database: " + e.getMessage());
        }

    }



    @Override
    public ResponseEntity<Void> deleteArticleById(Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
        Optional<Article> articleOptional = articleRepository.findById(id);

        if (articleOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        articleRepository.deleteById(id);

        return ResponseEntity.status(200).build();
    }

    @Override
    public ResponseEntity<?> getArticleById(Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
        Optional<Article> articleOptional = articleRepository.findById(id);

        if (articleOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(200).body(articleMapper.toDto(articleOptional.get()));
    }

    @Override
    public ResponseEntity<?> getAllArticles() {
        List<Article> articleOptional = articleRepository.findAll();
        return ResponseEntity.status(200).body(articleOptional.stream().map(articleMapper::toDto).toList());
    }

    @Override
    public ResponseEntity<ArticlesDto> editArticle(ArticlesDto articlesDto) {
        if (articlesDto.getId() == null) {
            return ResponseEntity.ofNullable(articlesDto);
        }

        Optional<Article> articleOptional = articleRepository.findById(articlesDto.getId());

        if (articleOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Article article = articleOptional.get();

        if (articlesDto.getTitle() != null) {
            article.setTitle(articlesDto.getTitle());
        }
        if (articlesDto.getBody() != null) {
            article.setBody(articlesDto.getBody());
        }
        if (articlesDto.getUpdatedAt() != null) {
            article.setUpdatedAt(LocalDateTime.now());
        }

        articleRepository.save(article);
        return ResponseEntity.accepted().body(articleMapper.toDto(article));
    }

    @Override
    public ResponseEntity<List<MostCommentArticles>> getMostCommented(Optional<String> checkDate) {

        if (checkDate.isPresent()){

            try {
                Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(checkDate.get());
                return ResponseEntity.ok(articleRepository.findMostCommentAfterCheckDate(date1));


            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

        }
        return ResponseEntity.ok(articleRepository.findMostComment());
    }

    @Override
    public ResponseEntity<List<ArticlesDto>> getBYSortandPage(Optional<String> sortBy, Optional<Integer> pageNum, Optional<Integer> pageSize) {
        List<Article> result;
        if(pageNum.isPresent() && pageSize.isPresent()){
            PageRequest pageRequest = PageRequest.of(pageNum.get(),pageSize.get());
            if(sortBy.isPresent()){
                Sort sort = Sort.by(sortBy.get());
                pageRequest=pageRequest.withSort(sort);
            }
             result = articleRepository.findAll(pageRequest).getContent();
        }else if(sortBy.isPresent()){
            Sort sort = Sort.by(sortBy.get());
            result=articleRepository.findAll(sort);
        }else {
            result=articleRepository.findAll();
        }
        return ResponseEntity.ok(result.stream().map(articleMapper::toDto).collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<List<ArticlesDto>> getByFilter(Optional<String> title, Optional<List<String>> body, Optional<Long> userId, Optional<LocalDate> minDate, Optional<LocalDate> maxDate) {
        List<Article> result=articleRepository.getByFilter(title,body,userId,minDate,maxDate);
        return ResponseEntity.ok(result.stream().map(articleMapper::toDto).collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<List<CommentsDto>> getCommentsOfArticle(Long id, Optional<String> sortBy, Optional<Integer> pageNum, Optional<Integer> pageSize) {
        List<Comments> result = commentsRepository.getCommentsOfArticle(id,sortBy,pageNum,pageSize);
        return ResponseEntity.ok(result.stream().map(commentMapper::toDto).collect(Collectors.toList()));
    }
}
