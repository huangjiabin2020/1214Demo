package com.binge.annotation;

import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 佛祖保佑  永无BUG
 *
 * @author: JiaBin Huang
 * @date: 2020年 12月17日
 * @description:
 **/
@Inherited
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Age.List.class)
public @interface Age {
    String message() default "默认的AGE错误信息2020";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    /**
     * 可重入
     */
    @Target({ FIELD})
    @Retention(RUNTIME)
    @Documented
    @Inherited
    public @interface List {
        Age[] value();
    }
}
