package uz.najottalim.homeworkforspring.repository.extension.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import uz.najottalim.homeworkforspring.dto.MostActiveUsersDto;
import uz.najottalim.homeworkforspring.models.User;
import uz.najottalim.homeworkforspring.repository.extension.UserRepositoryExtension;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class UserRepositoryExtensionImpl implements UserRepositoryExtension {
    private final EntityManager entityManager;
    @Override
    public List<MostActiveUsersDto> getMostActive(Integer n) {
        TypedQuery<Tuple> nativeQuery = entityManager.createQuery(
                "select u.id as id ,u.username as username,u.password as password,u.email as email,count(u.id) as count\n" +
                "from users u\n" +
                "    join Comments c\n" +
                "        on u.id = c.user.id\n" +
                "group by u.id,u.username,u.password,u.email\n" +
                "order by count(u.id) desc", Tuple.class);

        List<Tuple> resultList = nativeQuery.setMaxResults(n).getResultList();
        return resultList.stream().map(tuple ->
             new MostActiveUsersDto(
                    tuple.get("id",Long.class),
                    tuple.get("username",String.class),
                    tuple.get("password",String.class),
                    tuple.get("email",String.class),
                    tuple.get("count",Long.class)
                    )
        ).collect(Collectors.toList());
    }
}
