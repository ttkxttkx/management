package com.li.emp.AOP;


import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AOPdemo {

    @Before("execution(* com.li.emp.controller.*.*(..))")
    public void before() {
        System.out.println("before");
    }
}
