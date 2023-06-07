package uz.najottalim.homeworkforspring.aop;

import jakarta.persistence.JoinColumn;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import uz.najottalim.homeworkforspring.dto.CommentsDto;
import uz.najottalim.homeworkforspring.dto.ErrorDto;
import uz.najottalim.homeworkforspring.excepton.ArgNotValidException;
import uz.najottalim.homeworkforspring.excepton.NoResourceFoundException;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Aspect
public class ControllerAspect {
    @Pointcut(value = "@within(CommentsController)")
    public void commentsController(){

    }
    @Around(value = "commentsController()")
    public Object successfulCrud(ProceedingJoinPoint proceedingJoinPoint){
        Object proceed;
        try{
            Object[] args = proceedingJoinPoint.getArgs();
            for (Object arg:args) {
                if(arg instanceof CommentsDto commentsDto){
                    if(commentsDto.getBody() == null) throw new ArgNotValidException();
                    else if(commentsDto.getBody().isBlank()) throw  new ArgNotValidException();
                }
            }
            proceed = ResponseEntity.status(200).body(proceedingJoinPoint.proceed());
        }catch (NoResourceFoundException ex){
            proceed = ResponseEntity.status(404).body(ErrorDto.builder().errors(Map.of("no resource found exception",List.of(ex.getMessage()))).build());

        }catch (ArgNotValidException ex){
            proceed = ResponseEntity.status(404).body(ErrorDto.builder().errors(Map.of("field exception",List.of(ex.getMessage()))).build());
        }catch (MethodArgumentNotValidException ex){
            Map<String, List<String>> errors = ex.getBindingResult().getFieldErrors().stream()
                    .collect(Collectors.groupingBy(
                            FieldError::getField,
                            Collectors.mapping(DefaultMessageSourceResolvable::getDefaultMessage, Collectors.toList())
                    ));
            proceed = ResponseEntity.status(404).body(ErrorDto.builder().errors(errors).build());

        }catch (DataAccessException ex){
            proceed = ResponseEntity.status(404).body(ErrorDto.builder().errors(Map.of("data access exception",List.of(ex.getMessage()))).build());
        }catch (MethodArgumentTypeMismatchException ex){
            proceed = ResponseEntity.status(404).body(ErrorDto.builder().errors(Map.of("method arg mismatch",List.of(ex.getMessage()))).build());
        }catch (Throwable ex) {
            proceed = ResponseEntity.status(404).body(ErrorDto.builder().errors(Map.of("exception",List.of(ex.getMessage()))).build());
        }
        return proceed;
    }
}
