package com.sed.backend.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Before("within(com.sed.backend..*) && !within(org.springframework.web.filter.GenericFilterBean+) && !target(org.springframework.web.filter.GenericFilterBean+)")
    public void logBefore(JoinPoint joinPoint) {
        log.info("Invocando {}", joinPoint.getSignature());
    }

    @AfterReturning(pointcut = "within(com.sed.backend..*)&& !within(org.springframework.web.filter.GenericFilterBean+) && !target(org.springframework.web.filter.GenericFilterBean+)", returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
        log.info("Completado {} -> {}", joinPoint.getSignature(), result);
    }
}