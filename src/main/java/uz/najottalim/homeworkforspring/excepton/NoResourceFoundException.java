package uz.najottalim.homeworkforspring.excepton;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoResourceFoundException extends RuntimeException {
    private String message = "No such data found";
}
