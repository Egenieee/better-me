package com.betterme.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({UsersNotUniqueException.class})
    public String BadDataIntegrityException(UsersNotUniqueException exception) {
        log.warn("Wrong Data");

        return "users/usersJoinErrorPage";
    }
}
