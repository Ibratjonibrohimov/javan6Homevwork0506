package uz.najottalim.homeworkforspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.najottalim.homeworkforspring.models.User;
import uz.najottalim.homeworkforspring.repository.extension.UserRepositoryExtension;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User,Long>, UserRepositoryExtension {
    Optional<User> findFirstById(Long id);
    Optional<User> findFirstByUsername(String username);

}
