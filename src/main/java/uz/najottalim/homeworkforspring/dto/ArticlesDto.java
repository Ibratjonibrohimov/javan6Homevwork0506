package uz.najottalim.homeworkforspring.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ArticlesDto {
    private Long id;
    private String title;
    private String body;
    private Long user_id;
    private LocalDateTime publishDate;
    private LocalDateTime updatedAt;
}
