package org.contourgara.presentation;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class GlobalExceptionHandler {
    @Around("execution(* org.contourgara.presentation.SpringS3ApplicationRunner.run(..))")
    public void handleException(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            proceedingJoinPoint.proceed();
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
        }
    }
}
