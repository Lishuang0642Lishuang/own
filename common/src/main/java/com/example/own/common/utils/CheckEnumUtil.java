package com.example.own.common.utils;

import com.example.own.common.annotation.EnumValid;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ObjectUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckEnumUtil implements ConstraintValidator<EnumValid, Object> {

    private EnumValid annotation;

    @Override
    public void initialize(EnumValid constraintAnnotation) {
        annotation = constraintAnnotation;
    }

    @SneakyThrows
    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {

        boolean result = false;

        if (!annotation.ignoreEmpty() && ObjectUtils.isEmpty(o)) {
            return false;
        }

        Object[] objects = annotation.target().getEnumConstants();
        for (Object object : objects) {
            Object value = object.getClass().getDeclaredMethod(buildGetMethod(annotation.field())).invoke(object);

            if (value.toString().equals(String.valueOf(o))) {
                result = true;
                break;
            }
        }

        return result;
    }


    private String buildGetMethod(String field) {

        return "get" + field.substring(0, 1).toUpperCase() + field.substring(1);
    }

}
