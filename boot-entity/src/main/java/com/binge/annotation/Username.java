package com.binge.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
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
@Constraint( validatedBy = {})
@Pattern(regexp = "A.*",message = "必须以A开头")
public @interface Username {
    String message() default "默认的错误信息2020";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
