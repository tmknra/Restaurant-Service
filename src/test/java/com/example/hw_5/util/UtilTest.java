package com.example.hw_5.util;

import com.google.i18n.phonenumbers.NumberParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UtilTest {
    // TODO: add Date validation test
    @Test
    void reformatRuTelephoneRemoveWhiteSpaces() throws NumberParseException {
        String reformatted = Util.reformatRuTelephone("+7(999)-11-11-111");
        assertEquals("+79991111111", reformatted);
        reformatted = Util.reformatRuTelephone("+7-999-99-11-111");
        assertEquals("+79999911111", reformatted);
        reformatted = Util.reformatRuTelephone("+7 923 456 789 1");
        assertEquals("+79234567891", reformatted);
        reformatted = Util.reformatRuTelephone("+79331275123");
        assertEquals("+79331275123", reformatted);
    }

    @Test
    void reformatRuTelephoneRemoveNotRu() {
        Assertions.assertThrows(Exception.class, () -> Util.reformatRuTelephone("+9(999)222-33-11"));
        Assertions.assertThrows(Exception.class, () -> Util.reformatRuTelephone("+7(899)222-33-11"));
    }

    @Test
    void reformatRuTelephoneRemoveNotTelephone() {
        Assertions.assertThrows(Exception.class, () -> Util.reformatRuTelephone("+cdvbgfcdxdsaxd"));
        Assertions.assertThrows(Exception.class, () -> Util.reformatRuTelephone("+7(ddddd899)222-33-11"));
        Assertions.assertThrows(Exception.class, () -> Util.reformatRuTelephone("+7000000899)2-0-----0999922-33-11"));
        Assertions.assertThrows(Exception.class, () -> Util.reformatRuTelephone("+70000008900000000000000000000"));
    }

    @Test
    void validateEmailAddress() {
        Assertions.assertThrows(Exception.class, () -> Util.validateEmailAddress("asdasd@asdasd"));
        Assertions.assertThrows(Exception.class, () -> Util.validateEmailAddress("asdasdasd"));
        Assertions.assertThrows(Exception.class, () -> Util.validateEmailAddress("..123adsf@asdasd.ca"));
        Assertions.assertThrows(Exception.class, () -> Util.validateEmailAddress("asdasd..@asdqw..23"));
        Assertions.assertThrows(Exception.class, () -> Util.validateEmailAddress(""));
    }
}
