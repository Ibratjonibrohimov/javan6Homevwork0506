package uz.najottalim.homeworkforspring.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ARTICLES")
public class Article {
    @Id
    @GeneratedValue(generator = "article_id_seq")
    @SequenceGenerator(name = "article_id_seq", sequenceName = "article_id_seq", allocationSize = 1)
    private Long id;
    private String title;
    private String body;
    @ManyToOne
    private User user;
    private LocalDateTime publishDate;
    private LocalDateTime updatedAt;
}
