package com.anushka.bankcore.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
 
@Aspect
@Component
public class LoggingAspect {
 
	private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
 
	@Before("execution(* com.anushka.bankcore.service.*.*(..))")
	public void logBefore(JoinPoint joinPoint) {
		logger.info("Entering method: {} with arguments: {}", joinPoint.getSignature().getName(), joinPoint.getArgs());
	}
 
	@AfterReturning(pointcut = "execution(* com.anushka.bankcore.service.*.*(..))", returning = "result")
	public void logAfterReturning(JoinPoint joinPoint, Object result) {
		logger.info("Exiting method: {} with result: {}", joinPoint.getSignature().getName(), result);
	}
 
	@AfterThrowing(pointcut = "execution(* com.anushka.bankcore.service.*.*(..))", throwing = "error")
	public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
		logger.error("Exception in method: {} with cause: {}", joinPoint.getSignature().getName(), error.getMessage());
	}
}