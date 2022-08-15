package com.example.hw_5.validation.constraint;

import com.example.hw_5.validation.MyEmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MyEmailValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailValidation {
    String message() default "Incorrect email address.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
