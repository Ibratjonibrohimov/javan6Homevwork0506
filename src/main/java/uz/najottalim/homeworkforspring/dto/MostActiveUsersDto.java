package uz.najottalim.homeworkforspring.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MostActiveUsersDto {
    private Long id;
    @NotNull(message = "username shouldn't be null")
    @NotEmpty(message = "username shouldn't be empty")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String username;
    @NotNull(message = "password shouldn't be null")
    @NotEmpty(message = "password shouldn't be empty")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @Email(message = "email is incorrectly")
    @NotNull(message = "email shouldn't be null")
    @NotEmpty(message = "email shouldn't be empty")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String email;

    private Long count;
}
