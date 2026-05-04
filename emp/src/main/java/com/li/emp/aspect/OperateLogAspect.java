package com.li.emp.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 操作日志切面类
 * 用于拦截 EmpController 的方法并记录操作日志
 */
@Slf4j
@Aspect
@Component
public class OperateLogAspect {

    /**
     * 环绕通知：拦截 EmpController 类下的所有 public 方法
     *
     * @param joinPoint 连接点对象
     * @return 目标方法的返回值
     * @throws Throwable 异常
     */
    @Around("execution(public * com.li.emp.controller.EmpController.*(..))")
    public Object recordOperateLog(ProceedingJoinPoint joinPoint) throws Throwable {
        // 1. 记录开始时间
        long startTime = System.currentTimeMillis();

        // 2. 获取操作人：遍历方法参数，找到实体类参数并获取其 name 属性
        String operator = "未知";
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg != null) {
                // 判断是否为实体类（EmpEntity、EmpDTO 等）
                String className = arg.getClass().getName();
                if (className.contains("com.li.emp.pojo") || className.contains("com.li.emp.dto")) {
                    try {
                        // 尝试调用 getName() 方法
                        java.lang.reflect.Method getNameMethod = arg.getClass().getMethod("getName");
                        Object nameValue = getNameMethod.invoke(arg);
                        if (nameValue != null) {
                            operator = nameValue.toString();
                            break;
                        }
                    } catch (Exception e) {
                        // 如果没有 getName() 方法，继续查找下一个参数
                        log.debug("参数 {} 没有 getName() 方法", className);
                    }
                }
            }
        }

        // 3. 执行目标方法，并获取返回值
        Object result = joinPoint.proceed();

        // 4. 记录结束时间并计算耗时
        long endTime = System.currentTimeMillis();
        long costTime = endTime - startTime;

        // 5. 获取当前时间
        Date operateTime = new Date();

        // 6. 获取方法全类名
        String methodName = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();

        // 7. 打印完整日志
        log.info("========== 操作日志 ==========");
        log.info("操作人: {}", operator);
        log.info("操作时间: {}", operateTime);
        log.info("操作方法: {}", methodName);
        log.info("返回值: {}", result);
        log.info("执行耗时: {} ms", costTime);
        log.info("=============================");

        // 返回目标方法的执行结果
        return result;
    }
}
