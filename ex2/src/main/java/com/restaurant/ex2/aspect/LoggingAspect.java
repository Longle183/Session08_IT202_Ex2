package com.restaurant.ex2.aspect;

import com.restaurant.ex2.entity.ErrorLog;
import com.restaurant.ex2.repository.ErrorLogRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {

    private final ErrorLogRepository errorLogRepository;

    @AfterThrowing(
            pointcut = "execution(* com.restaurant.ex2.service.*.*(..))",
            throwing = "ex"
    )
    public void logError(Exception ex) {

        ErrorLog log = new ErrorLog();

        log.setTimestamp(LocalDateTime.now());
        log.setMethodName("Service Error");
        log.setExceptionMessage(ex.getMessage());

        errorLogRepository.save(log);

        System.out.println("ERROR: " + ex.getMessage());
    }
}