package com.mahi.atm.utilities;

import com.mahi.atm.model.Denomination;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import static com.mahi.atm.utilities.UtilityClass.currencyCheck;
import static com.mahi.atm.utilities.UtilityClass.getInteger;
import static org.junit.jupiter.api.Assertions.*;

class UtilityClassTest {

    @Test
    void getIntegerFifty() {
        Integer integer = getInteger(Denomination.£50);
        assertEquals(50, integer);
    }

    @Test
    void getIntegerTwenty() {
        Integer integer = getInteger(Denomination.£20);
        assertEquals(20, integer);
    }

    @Test
    void currencyCheckValid() {
        currencyCheck("£");
        //No error thrown
    }

    @Test
    void currencyCheckInvalid() {
        try {
            currencyCheck("$");
            fail( "Method didn't throw exception when I expected it to" );
        } catch (ResponseStatusException expectedException) {
            assertEquals("400 BAD_REQUEST \"Currency not supported\"", expectedException.getMessage());
        }
    }

    @Test
    void currencyCheckNull() {
        try {
            currencyCheck(null);
            fail("Method didn't throw exception when I expected it to");
        } catch (ResponseStatusException expectedException) {
            assertEquals("400 BAD_REQUEST \"Currency not supported\"", expectedException.getMessage());
        }
    }
}