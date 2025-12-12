package com.sed.backend.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class AuditAspect {

    @AfterReturning("execution(* com.sed.backend.presentation.controllers..*(..))")
    public void audit(JoinPoint joinPoint) {
        log.info("Audit evento en {}", joinPoint.getSignature());
    }
}