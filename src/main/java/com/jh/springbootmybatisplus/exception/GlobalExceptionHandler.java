package com.jh.springbootmybatisplus.exception;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Set;


/*统一捕获异常处理*/
@ControllerAdvice
@Component
public class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handle(ValidationException exception) {
        String validTips = "";
        if(exception instanceof ConstraintViolationException){
            ConstraintViolationException exs = (ConstraintViolationException) exception;
            Set<ConstraintViolation<?>> violations = exs.getConstraintViolations();
            for (ConstraintViolation<?> item : violations) {
              validTips+=item.getMessage();   //打印验证不通过的信息
            }
        }
        return validTips;
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handle1(MethodArgumentNotValidException exception){
        String validTips = "";
        if(exception.getBindingResult().getAllErrors().size()>0){
            for(ObjectError error : exception.getBindingResult().getAllErrors()){
                validTips += error.getDefaultMessage()+";";
            }
        }
        return validTips;

    }
}
