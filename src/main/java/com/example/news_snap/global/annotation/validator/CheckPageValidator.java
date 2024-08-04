package com.example.news_snap.global.annotation.validator;

import com.example.news_snap.global.annotation.CheckPage;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CheckPageValidator implements ConstraintValidator<CheckPage, Integer> {
    @Override
    public void initialize(CheckPage constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Integer page, ConstraintValidatorContext context) {
        if (page == null || page < 0) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("페이지는 0 이상 정수여야 합니다.")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
