package com.osf.sp.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Slf4j
public class LogAdvice {
	@Before("execution(* com.osf.sp.controller.*.*(..))")
	public void beforeExecute(JoinPoint jp) {
		log.info("{}메서드 실행 전",jp);
	}
	
	@After("execution(* com.osf.sp.controller.*.*(..))") 
	public void afterExecute(JoinPoint jp) {
		log.info("{}메서드 실행 후!",jp);
	}
	

}
