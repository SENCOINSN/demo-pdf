package com.gl.si.exceptions;

import jakarta.validation.ConstraintViolation;
import lombok.Getter;


import java.util.Set;

@Getter
public class ValidationException extends RuntimeException{
    private Set<ConstraintViolation<Object>> violations;

    public ValidationException(String message, Set<ConstraintViolation<Object>> violations) {
        this.violations = violations;
    }
}
