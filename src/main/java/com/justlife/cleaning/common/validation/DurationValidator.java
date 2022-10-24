package com.justlife.cleaning.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class DurationValidator implements ConstraintValidator<DurationValid, Integer> {

    private DurationValid constraint;

    @Override
    public void initialize(final DurationValid constraintAnnotation) {
        this.constraint = constraintAnnotation;
    }

    @Override
    public boolean isValid(final Integer duration, final ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.nonNull(duration) && (duration == 2 || duration == 4)) {
            return true;
        }
        return false;
    }

}