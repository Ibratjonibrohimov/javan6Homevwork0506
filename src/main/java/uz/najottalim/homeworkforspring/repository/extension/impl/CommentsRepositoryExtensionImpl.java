package uz.najottalim.homeworkforspring.repository.extension.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import uz.najottalim.homeworkforspring.excepton.NoResourceFoundException;
import uz.najottalim.homeworkforspring.models.Article;
import uz.najottalim.homeworkforspring.models.Comments;
import uz.najottalim.homeworkforspring.repository.ArticleRepository;
import uz.najottalim.homeworkforspring.repository.extension.CommentsRepositoryExtension;

import javax.xml.stream.events.Comment;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@RequiredArgsConstructor
public class CommentsRepositoryExtensionImpl implements CommentsRepositoryExtension {
    private final EntityManager entityManager;
    private final ArticleRepository articleRepository;

    @Override
    public List<Comments> getCommentsOfArticle(Long id, Optional<String> sortBy, Optional<Integer> pageNum, Optional<Integer> pageSize) {
        Optional<Article> article = articleRepository.findById(id);
        if (article.isEmpty()) throw new NoResourceFoundException();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Comments> query = criteriaBuilder.createQuery(Comments.class);
        Root<Comments> root = query.from(Comments.class);

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(criteriaBuilder.equal(root.get("article").get("id"),id));

        query.where(predicates.toArray(Predicate[]::new));
        sortBy.ifPresent(value->query.orderBy(criteriaBuilder.asc(root.get(value))));
        TypedQuery<Comments> query1 = entityManager.createQuery(query);
        if(pageNum.isPresent() && pageSize.isPresent()) {
            query1.setFirstResult(pageNum.get()*pageSize.get()).setMaxResults(pageSize.get());
        }
        return query1.getResultList();
    }
}

