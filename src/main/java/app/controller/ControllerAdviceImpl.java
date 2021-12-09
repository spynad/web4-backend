package app.controller;

import app.exception.PointNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.naming.AuthenticationException;

@ControllerAdvice
public class ControllerAdviceImpl {

    @ResponseBody
    @ExceptionHandler(PointNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String pointNotFoundHandler(PointNotFoundException e) {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String unreadableMessageHandler(HttpMessageNotReadableException e) {
        return "Bad request\n";
    }

    @ResponseBody
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String badCredentialsHandler(BadCredentialsException e) {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String generalHandler(RuntimeException e) {
        return "Internal server error\n";
    }


}
