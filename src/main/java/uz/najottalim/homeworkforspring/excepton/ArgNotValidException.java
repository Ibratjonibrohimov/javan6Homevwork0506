package uz.najottalim.homeworkforspring.excepton;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArgNotValidException extends RuntimeException{
    private  String message = "Such argument not valid ";
}
