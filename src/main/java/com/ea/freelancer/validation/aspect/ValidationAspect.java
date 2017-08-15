package com.ea.freelancer.validation.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Aspect
	@Component
	public class ValidationAspect {
		
		@Pointcut("execution(* com.ea.freelancer.service..*(..))")
			public void lookForService(){}
		
		@Pointcut("args(..)")
		public void argsLookForService(){}
		
		@Pointcut("within(com.ea.freelancer.service..*)")
		public void withinLookForService(){}
		
		
		@Before("argsLookForService() && withinLookForService()")
		public void logForServices(JoinPoint joinPoint){
		
			    System.out.println( "   **********     TARGET CLASS : " + 
				joinPoint.getSignature().getDeclaringTypeName() + "." +
				joinPoint.getSignature().getName() + 
					    			"    **********");
		}
	

}
