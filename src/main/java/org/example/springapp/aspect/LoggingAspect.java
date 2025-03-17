package org.example.springapp.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Slf4j
@Component
public class LoggingAspect {
    @Pointcut("within(org.example.springapp.controller..*)")
    public void apiMethods() {
    }
    @Pointcut("@annotation(AspectAnnotation)")
    public void withAnnotation(){
    }
    @Around(value = "withAnnotation()")
    public Object loggingMethods(final ProceedingJoinPoint point)
            throws Throwable {
        log.info("Enter method {}.{} with argument(s): {}",
                point.getSignature().getDeclaringTypeName(),
                point.getSignature().getName(),
                Arrays.toString(point.getArgs()));
        try {
            Object result = point.proceed();
            log.info("After execution method {}.{} with argument(s): {}",
                    point.getSignature().getDeclaringTypeName(),
                    point.getSignature().getName(),
                    Arrays.toString(point.getArgs()));
            return result;
        } catch (Throwable e) {
            log.error("Illegal argument(s) {} in method {}.{}()",
                    Arrays.toString(point.getArgs()),
                    point.getSignature().getDeclaringTypeName(),
                    point.getSignature().getName());
            throw e;
        }
    }
    @AfterThrowing(pointcut = "apiMethods()", throwing = "exception")
    public void logsExceptionsFromAnyLocation(final JoinPoint joinPoint,
                                              final Throwable exception) {
        log.error("Exception in {}.{}() - {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), exception.getMessage());
    }
}