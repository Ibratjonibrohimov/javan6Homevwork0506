package uz.najottalim.homeworkforspring.dto;

import java.time.LocalDateTime;

public interface MostCommentArticles {
    Long getId();

    String getTitle();

    String getBody();
    Long getUserId();
    LocalDateTime getPublishDate();
    LocalDateTime getUpdatedAt();
}
