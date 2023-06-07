package uz.najottalim.homeworkforspring.repository.extension.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uz.najottalim.homeworkforspring.excepton.NoResourceFoundException;
import uz.najottalim.homeworkforspring.models.Article;
import uz.najottalim.homeworkforspring.models.User;
import uz.najottalim.homeworkforspring.repository.UserRepository;
import uz.najottalim.homeworkforspring.repository.extension.ArticleRepositoryExtension;
import uz.najottalim.homeworkforspring.service.UserService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
public class ArticleRepositoryExtensionImpl implements ArticleRepositoryExtension {
    private final EntityManager entityManager;
    private final UserRepository userRepository;
    @Override
    public List<Article> getByFilter(Optional<String> title, Optional<List<String>> body, Optional<Long> userId, Optional<LocalDate> minDate, Optional<LocalDate> maxDate) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Article> query = criteriaBuilder.createQuery(Article.class);
        Root<Article> root = query.from(Article.class);

        List<Predicate> predicates = new ArrayList<>();
        List<Predicate> predicatesForBody = new ArrayList<>();
        title.ifPresent(value->predicates.add(criteriaBuilder.like(root.get("title"), "%"+value+"%")));
        body.ifPresent(values->{
            values.forEach(value->{predicatesForBody.add(criteriaBuilder.like(root.get("body"),"%"+value+"%"));});
            predicates.add(criteriaBuilder.or(predicatesForBody.toArray(Predicate[]::new)));
        });
        userId.ifPresent(value->predicates.add(criteriaBuilder.equal(root.get("user").get("id"),value)));

        minDate.ifPresent(value->predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("publishDate"), value)));
        maxDate.ifPresent(value->predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("publishDate"), value)));

        TypedQuery<Article> result = entityManager.createQuery(query.where(predicates.toArray(Predicate[]::new)));

        return result.getResultList();
    }

    @Override
    public List<Article> getArticlesOfUser(Long id, Optional<String> sortBy, Optional<Integer> pageNum, Optional<Integer> pageSize) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Article> query = criteriaBuilder.createQuery(Article.class);
        Root<Article> articleRoot = query.from(Article.class);

        List<Predicate> predicates = new ArrayList<>();

        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) throw new NoResourceFoundException();

        predicates.add(criteriaBuilder.equal(articleRoot.get("user").get("id"),id));

        CriteriaQuery<Article> query1 = query.where(predicates.toArray(Predicate[]::new));
        sortBy.ifPresent(value->query1.orderBy(criteriaBuilder.asc(articleRoot.get(value))));
        TypedQuery<Article> query2 = entityManager.createQuery(query1);
        if(pageNum.isPresent() && pageSize.isPresent()){
            query2.setFirstResult(pageNum.get()*pageSize.get()).setMaxResults(pageSize.get());
        }

        return query2.getResultList();
    }
}
