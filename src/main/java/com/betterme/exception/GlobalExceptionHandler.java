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

    @ExceptionHandler({NotFountBetterMeOfToday.class})
    public String notFountBetterMeOfTodayException(NotFountBetterMeOfToday exception) {
        log.warn("오늘의 Better Me가 존재하지 않습니다.");

        return "betterme/notFoundBetterMeOfTodayErrorPage";
    }
}
