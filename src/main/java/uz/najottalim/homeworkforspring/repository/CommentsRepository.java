package uz.najottalim.homeworkforspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.najottalim.homeworkforspring.models.Comments;
import uz.najottalim.homeworkforspring.repository.extension.CommentsRepositoryExtension;

import java.util.List;


@Repository
public interface CommentsRepository extends JpaRepository<Comments, Long>, CommentsRepositoryExtension {

}
