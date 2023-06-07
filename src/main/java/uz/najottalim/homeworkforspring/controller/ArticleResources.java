package uz.najottalim.homeworkforspring.controller;

import org.hibernate.sql.model.PreparableMutationOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.najottalim.homeworkforspring.dto.ArticlesDto;
import uz.najottalim.homeworkforspring.dto.CommentsDto;
import uz.najottalim.homeworkforspring.dto.MostCommentArticles;
import uz.najottalim.homeworkforspring.service.ArticleServices;
import uz.najottalim.homeworkforspring.service.CommentsServices;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
    @RequestMapping("articles")
public class ArticleResources {

    @Autowired
    private ArticleServices articleServices;

    @Autowired
    private CommentsServices commentsServices;

    @PostMapping
    public ResponseEntity<ArticlesDto> addArticle(@RequestBody ArticlesDto articlesDto) {
        return articleServices.addArticle(articlesDto);
    }

    @PatchMapping
    public ResponseEntity<ArticlesDto> editArticle(@RequestBody ArticlesDto articlesDto){
        return articleServices.editArticle(articlesDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteArticleById(@PathVariable Long id) {
        return articleServices.deleteArticleById(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getArticleById(@PathVariable Long id) {
        return articleServices.getArticleById(id);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllArticles() {
        return articleServices.getAllArticles();
    }

    @PostMapping("/{article_id}/comment")
    public ResponseEntity<?> addComment(@PathVariable Integer article_id, @RequestBody CommentsDto commentsDto){
        return commentsServices.addComment(commentsDto);
    }

    @GetMapping("/most-commented")
    public ResponseEntity<List<MostCommentArticles>> getMostCommented(@RequestParam("check_date")
                                                                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                          Optional<String> checkDate){
        return articleServices.getMostCommented(checkDate);
    }

    @GetMapping()
    public ResponseEntity<List<ArticlesDto>> getBySortAndPage(@RequestParam("sortBy") Optional<String> sortBy,
                                                              @RequestParam("pageSize") Optional<Integer> pageSize,
                                                              @RequestParam("pageNum") Optional<Integer> pageNum)
    {
        return articleServices.getBYSortandPage(sortBy,pageNum,pageSize);
    }
    @GetMapping("/search")
    public ResponseEntity<List<ArticlesDto>> getFilter(@RequestParam("title") Optional<String> title,
                                                       @RequestParam("body") Optional<List<String>> body,
                                                       @RequestParam("user_id") Optional<Long> userId,
                                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                       @RequestParam("minPublishDate")Optional<LocalDate> minDate,
                                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                       @RequestParam("maxPublishDate")Optional<LocalDate> maxDate)
    {
        return articleServices.getByFilter(title,body,userId,minDate,maxDate);
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentsDto>> getCommentsOfArticle(@PathVariable Long id,
                                                  @RequestParam Optional<String> sortBy,
                                                  @RequestParam Optional<Integer> pageNum,
                                                  @RequestParam Optional<Integer> pageSize)
    {
        return articleServices.getCommentsOfArticle(id,sortBy,pageNum,pageSize);
    }

}
