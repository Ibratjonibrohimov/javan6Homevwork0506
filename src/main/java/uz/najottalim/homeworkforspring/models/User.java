package uz.najottalim.homeworkforspring.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(generator = "user_id",strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false,unique = true)
    private String email;
    @OneToMany(mappedBy = "user")
    List<Article> articles;

    public User(Long id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
