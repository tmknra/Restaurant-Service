package com.example.restaurant_service.util;

import com.example.restaurant_service.exception.entity.FoundationDateIsExpiredException;
import com.example.restaurant_service.exception.entity.PhoneNumberNotRuException;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class Util {

    private final static String emailRegExp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    private final static PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

    public static String reformatRuTelephone(String phone) throws NumberParseException, PhoneNumberNotRuException {
        Phonenumber.PhoneNumber phoneNumber = phoneNumberUtil.parse(phone, Phonenumber.PhoneNumber.CountryCodeSource.UNSPECIFIED.name());
        boolean validNumber = phoneNumberUtil.isValidNumberForRegion(phoneNumber, "RU");
        if (!validNumber) {
            throw new PhoneNumberNotRuException("Number is not russian.");
        }
        return phoneNumberUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.E164);
    }

    public static boolean validateEmailAddress(String email) {
        boolean matches = Pattern.compile(emailRegExp).matcher(email).matches();
        if (!matches)
            throw new IllegalArgumentException("incorrect email format");
        return true;
    }

    public static void validateFoundationDate(String restName, LocalDate date) throws FoundationDateIsExpiredException {
        if (date != null && LocalDate.now().isBefore(date))
            throw new FoundationDateIsExpiredException(restName, date);
    }
}
