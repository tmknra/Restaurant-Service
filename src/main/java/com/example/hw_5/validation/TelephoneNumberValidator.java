package com.example.hw_5.validation;

import com.example.hw_5.util.Util;
import com.example.hw_5.validation.constraint.PhoneNumberValidation;
import lombok.SneakyThrows;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TelephoneNumberValidator implements ConstraintValidator<PhoneNumberValidation, String> {


    @Override
    public void initialize(PhoneNumberValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @SneakyThrows
    @Override
    public boolean isValid(String telephone, ConstraintValidatorContext context) {
        if (telephone != null) {
            String reformatRuTelephone = Util.reformatRuTelephone(telephone);
            Pattern pattern = Pattern.compile("^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$");
            Matcher matcher = pattern.matcher(reformatRuTelephone);
            return matcher.matches();
        }
        return true;
    }
}
