package uz.najottalim.javan6.exceptionshandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uz.najottalim.javan6.customexseptions.EmailAlreadyRegisteredException;
import uz.najottalim.javan6.customexseptions.NoResourceFoundException;
import uz.najottalim.javan6.customexseptions.UsernameAlreadyRegisteredException;
import uz.najottalim.javan6.dto.ErrorDto;

import java.util.*;

@RestControllerAdvice
public class ExceptionsHandler {
    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto notFoundHandler(NoResourceFoundException ex){
        return ErrorDto.builder().errors(Map.of("error ",List.of(ex.getMessage()))).build();
    }
    @ExceptionHandler(EmailAlreadyRegisteredException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorDto emailAlreadyRegisteredHandler(EmailAlreadyRegisteredException ex){
        return ErrorDto.builder().errors(Map.of("email ",List.of(ex.getMessage()))).build();
    }

    @ExceptionHandler(UsernameAlreadyRegisteredException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorDto usernameAlreadyRegisteredHandler(UsernameAlreadyRegisteredException ex){
        return ErrorDto.builder().errors(Map.of("username ",List.of(ex.getMessage()))).build();
    }
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto notFoundHandler(Throwable ex){
        return ErrorDto.builder().errors(Map.of("error ",List.of(ex.getMessage()))).build();
    }
}
