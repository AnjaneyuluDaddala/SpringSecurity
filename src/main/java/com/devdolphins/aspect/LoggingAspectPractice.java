package com.devdolphins.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.devdolphins.models.Employees;
import com.devdolphins.models.Leaves;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Slf4j
@Component
public class LoggingAspectPractice {

    // @Pointcut("execution(* com.devdolphins.controllers.*.*.(..))")
    //@Pointcut("within(com.devdolphins.controllers.*)") // within the package 
 

    // @Pointcut("within(com.devdolphins..*)") // with in subPackage

   // @Pointcut("this(com.devdolphins.controllers.AdminController)") // with in this class only.

    @Pointcut("@annotation(com.devdolphins.controllers.CustomAnnotation)") // custom annotation
    //custom annotation is using mainly datavalidation ,logging,Transcational management,for security
    public void pointCutLoggoing(){
    }
/* 
    @Before("pointCutLoggoing()")
    public void before(JoinPoint joinPoint){
           log.info("Before method invoke :: "+joinPoint.getSignature());
    }



    @After("pointCutLoggoing()")
    public void after(JoinPoint joinPoint){
        log.info("After method invoke :: "+joinPoint.getSignature());
    }

    */

    /* 
    @AfterReturning(value = "execution(* com.devdolphins.controllers.*.*.(..))",returning = "employee")
    public void after(JoinPoint joinPoint,Employees employee){
        log.info("After method invoke :: "+joinPoint.getSignature());
    }


    //find by id (id is not present ) of that method (.orElseThrow())

    @AfterThrowing(value = "execution(* com.devdolphins.controllers.*.*.(..))",throwing= "ex")
    public void after(JoinPoint joinPoint,Exception ex){
        log.info("After method invoke :: "+ex.getMessage());
    }
*/

@Around("pointCutLoggoing()")
public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
    log.info("Before method invoke :: "+joinPoint.getSignature());
    Object obj=joinPoint.proceed();
    if (obj instanceof Employees) {
        log.info("After the method invoke :: "+joinPoint.getSignature());
        log.info("After the method invoke :: "+joinPoint.getArgs()[0]);
    }else if(obj instanceof Leaves){
        log.info("After the method invoke :: "+joinPoint.getSignature());
    }

    return obj;
}




}
