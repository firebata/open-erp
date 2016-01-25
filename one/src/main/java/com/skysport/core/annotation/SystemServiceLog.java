package com.skysport.core.annotation;

import java.lang.annotation.*;

/**
 * 说明:自定义注解 拦截service
 * Created by zhangjh on 2016/1/11.
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemServiceLog {
    String description() default "";
}