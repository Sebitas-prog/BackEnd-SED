package com.sed.backend.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class PerformanceAspect {

    @Around("within(com.sed.backend..*) && !within(org.springframework.web.filter.GenericFilterBean+) && !target(org.springframework.web.filter.GenericFilterBean+)")
    public Object measure(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            return pjp.proceed();
        } finally {
            long duration = System.currentTimeMillis() - start;
            log.debug("{} ejecutado en {} ms", pjp.getSignature(), duration);
        }
    }
}