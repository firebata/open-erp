package com.skysport.core.annotation;

import java.lang.annotation.*;

/**
 * 说明:拦截contro日志
 * Created by zhangjh on 2016/1/11.
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemControllerLog {

    String description() default "";
}