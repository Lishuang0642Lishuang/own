package com.example.own.common.annotation;

import com.example.own.common.utils.CheckEnumUtil;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = CheckEnumUtil.class)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnumValid {

    String message();

    Class<?> target();

    String field();

    boolean ignoreEmpty() default true;

    Class<? extends Payload>[] payload() default {};

    Class<?>[] groups() default {};
}