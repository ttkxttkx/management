package com.li.emp.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 操作日志自定义注解
 * 用于标记需要记录操作日志的方法
 */
@Target(ElementType.METHOD)  // 仅能加在方法上
@Retention(RetentionPolicy.RUNTIME)  // 运行时保留
public @interface OperateLog {
    
    /**
     * 操作描述
     * @return 操作描述信息
     */
    String value() default "";
    
    /**
     * 操作类型
     * @return 操作类型（INSERT、UPDATE、DELETE、SELECT、OTHER）
     */
    String type() default "OTHER";
}
