package com.saloon.saloon_mobile_api.utility;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger log =
            LoggerFactory.getLogger(LoggingAspect.class);

    @Around(
        "execution(* com.saloon.saloon_mobile_api.controller..*(..)) || " +
        "execution(* com.saloon.saloon_mobile_api.service..*(..))"
    )
    public Object logExecution(
            ProceedingJoinPoint joinPoint
    ) throws Throwable {

        String className =
                joinPoint.getSignature()
                        .getDeclaringType()
                        .getSimpleName();

        String methodName =
                joinPoint.getSignature()
                        .getName();

        long startTime = System.currentTimeMillis();

        log.info(
                "ENTER -> {}.{}()",
                className,
                methodName
        );

        try {

            Object result = joinPoint.proceed();

            long executionTime =
                    System.currentTimeMillis() - startTime;

            log.info(
                    "EXIT  -> {}.{}() [{} ms]",
                    className,
                    methodName,
                    executionTime
            );

            return result;

        } catch (Exception ex) {

            long executionTime =
                    System.currentTimeMillis() - startTime;

            log.error(
                    "ERROR -> {}.{}() [{} ms] : {}",
                    className,
                    methodName,
                    executionTime,
                    ex.getMessage(),
                    ex
            );

            throw ex;
        }
    }
}