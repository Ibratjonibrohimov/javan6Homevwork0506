package uz.najottalim.homeworkforspring.repository.extension;

import uz.najottalim.homeworkforspring.dto.MostActiveUsersDto;
import uz.najottalim.homeworkforspring.models.User;

import java.util.List;

public interface UserRepositoryExtension {
    List<MostActiveUsersDto> getMostActive(Integer n);
}
