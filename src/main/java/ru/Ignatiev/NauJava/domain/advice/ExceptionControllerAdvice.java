package ru.Ignatiev.NauJava.domain.advice;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class ExceptionControllerAdvice
{

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CustomException exception(ResourceNotFoundException e)
    {
        return CustomException.create("Resource not found! Error message=" + e.getMessage());
    }
}
