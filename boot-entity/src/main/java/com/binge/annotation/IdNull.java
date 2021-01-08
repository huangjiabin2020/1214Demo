package com.binge.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Null;
import java.lang.annotation.*;

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
@Constraint(validatedBy = { })
@Null
public @interface IdNull {
    String message() default "默认的Id错误信息2020";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
