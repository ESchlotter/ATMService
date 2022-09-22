package com.mahi.atm.controller;

import com.mahi.atm.model.ATMTransaction;
import com.mahi.atm.model.Cash;
import com.mahi.atm.model.Denomination;
import com.mahi.atm.model.Type;
import com.mahi.atm.service.ATMService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

import static com.mahi.atm.utilities.UtilityClass.currencyCheck;

@Log4j2
@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("cash")
public class ATMController implements ATMControllerSpec {

    private final ATMService atmService;

    @Override
    public ResponseEntity<ATMTransaction> addCash(String currency, Denomination denomination, Integer amount) {
        log.info("Request to add {} of {} to the ATM of the following amount", denomination , amount);
        currencyCheck(currency);
        Boolean status = atmService.depositCash(denomination, amount);

        ATMTransaction atmTransaction = ATMTransaction.builder()
                .isSuccessful(status)
                .type(Type.DEPOSIT)
                .denomination(denomination)
                .currency(currency)
                .amount(amount)
                .message("Transaction Successful")
                .build();
        return ResponseEntity.ok(atmTransaction);
    }

    @Override
    public ResponseEntity<ATMTransaction> withdrawCash(Cash cash) {
        log.info("Request to withdraw cash of the following amount: {}", cash);
        currencyCheck(cash.getCurrency());
        Boolean status = atmService.withdrawCash(cash);
        ATMTransaction atmTransaction = ATMTransaction.builder()
                .isSuccessful(status)
                .type(Type.WITHDRAW)
                .currency(cash.getCurrency())
                .amount(cash.getAmount())
                .message("Transaction Successful")
                .build();
        return ResponseEntity.ok(atmTransaction);
    }

}
