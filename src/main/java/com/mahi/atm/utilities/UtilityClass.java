package com.mahi.atm.utilities;

import com.mahi.atm.model.Denomination;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Log4j2
public final class UtilityClass {

    private UtilityClass() {
        throw new UnsupportedOperationException();
    }

    public static Integer getInteger(Denomination denomination) {
        Integer nextDenomination = Integer.valueOf(denomination.toString().substring(1));
        return nextDenomination;
    }

    public static void currencyCheck(String currency) {
        if (currency == null || !currency.equals("£")) {
            log.info("Currency '{}' not supported", currency);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Currency not supported");
        }
    }

}
