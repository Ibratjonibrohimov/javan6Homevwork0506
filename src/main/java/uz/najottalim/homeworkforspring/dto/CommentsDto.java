package uz.najottalim.homeworkforspring.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentsDto {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotBlank(message = "Body can not empty")
    @NotNull(message = "Body can not null")
    private String body;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UserDto author;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ArticlesDto article;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime publishDate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime updatedAt;

    public CommentsDto(Long id, String body, UserDto author) {
        this.id = id;
        this.body = body;
        this.author = author;
    }
}
