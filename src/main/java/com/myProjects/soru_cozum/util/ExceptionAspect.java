package com.myProjects.soru_cozum.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Component
@EnableAspectJAutoProxy
@Aspect
public class ExceptionAspect {

	private Logger LOGGER = LoggerFactory.getLogger(ExceptionAspect.class);

	@AfterThrowing(pointcut = "execution(* com.myProjects.soru_cozum.controller.FileController.*(..))", throwing = "ex")
	public void stringToNumberFormatException(NumberFormatException ex) {
		LOGGER.error("Can't convert to string to a number");
		LOGGER.error(ex.getMessage());
	}

	@AfterThrowing(pointcut = "execution(* com.myProjects.soru_cozum.service..*.*(..))",  throwing = "ex" )
	public void nullPointerException(JoinPoint method, NullPointerException ex) {
		LOGGER.info("Method's signature name: " + method.getSignature());
		LOGGER.error("Null Pointer exception was thrown");
		LOGGER.error(ex.getLocalizedMessage());
	}
}
