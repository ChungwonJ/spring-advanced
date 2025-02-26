package org.example.expert.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class AdminLoggingAspect {

    @Around("execution(* org.example.expert.domain.comment.controller.CommentAdminController.deleteComment(..)) || " +
            "execution(* org.example.expert.domain.user.controller.UserAdminController.changeUserRole(..))")
    public Object logAdminAccess(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().getName();
        String requestBody = Arrays.toString(joinPoint.getArgs());

        Object result = joinPoint.proceed();

        long endTime = System.currentTimeMillis();
        log.info("Admin API Access: Method: {}, Request Body: {}, Response: {}, Duration: {} ms",
                methodName, requestBody, result, (endTime - startTime));

        return result;
    }
}
