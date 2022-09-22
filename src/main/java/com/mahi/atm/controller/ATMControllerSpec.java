package com.mahi.atm.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mahi.atm.model.ATMTransaction;
import com.mahi.atm.model.Cash;
import com.mahi.atm.model.Denomination;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Validated
public interface ATMControllerSpec {

    @PutMapping(
            value = "deposit",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<ATMTransaction> addCash(@RequestParam String currency, @RequestParam Denomination denomination, @RequestParam Integer amount);

    @PutMapping(
            value = "withdraw",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<ATMTransaction> withdrawCash(@RequestBody Cash cash);

}
