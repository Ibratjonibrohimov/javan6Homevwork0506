package uz.najottalim.homeworkforspring.exceptionhandler;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uz.najottalim.homeworkforspring.dto.ErrorDto;
import uz.najottalim.homeworkforspring.excepton.NoResourceFoundException;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//@RestControllerAdvice
public class ExceptionsHandler {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto exceptionHandler(Exception ex){
        Map<String,List<String>> error = new HashMap<>();
        error.put("exception",List.of(ex.getMessage()));
        return ErrorDto.builder().errors(error).build();
    }
    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto dattaAccessExceptionHandler(DataAccessException ex){
        Map<String,List<String>> error = new HashMap<>();
        error.put("data access exception",List.of(ex.getMessage()));
        return ErrorDto.builder().errors(error).build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto validationHandler(MethodArgumentNotValidException ex){
        Map<String, List<String>> errors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(DefaultMessageSourceResolvable::getDefaultMessage, Collectors.toList())
                ));
        return ErrorDto.builder().errors(errors).build();
    }

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto noResourceFoundExceptionHandler(NoResourceFoundException ex){
        Map<String,List<String>> error = new HashMap<>();
        error.put("exception",List.of(ex.getMessage()));
        return ErrorDto.builder().errors(error).build();
    }
}
