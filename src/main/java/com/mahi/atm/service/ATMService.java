package com.mahi.atm.service;

import com.mahi.atm.model.Cash;
import com.mahi.atm.model.Denomination;
import com.mahi.atm.repository.ATMRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static com.mahi.atm.utilities.UtilityClass.getInteger;


@Service
@Log4j2
public class ATMService {
    private final ATMRepository atmRepository;

    @Autowired
    public ATMService(ATMRepository atmRepository) {
        this.atmRepository = atmRepository;
        if (!atmRepository.isInitialised()) {
        Scanner reader = new Scanner(System.in);
            Arrays.asList(Denomination.values())
                    .forEach(denomination -> {
                        System.out.println("Please enter how many " + denomination + " notes you would like to be in the system");
                        Integer amount = Integer.valueOf(reader.nextInt());
                        atmRepository.setNotes(denomination, amount);
                    });
            reader.close();
        }
    }

    public Boolean depositCash(Denomination denomination, Integer amount) {
        atmRepository.insertNotes(denomination, amount);
        return true;
    }

    public Boolean withdrawCash(Cash cash) {
        return calculateDenomination(cash);
    }

    protected Boolean calculateDenomination(Cash cash) {
        Denomination[] denomination = Denomination.values();

        Map<Denomination, Integer> denominationUsedMap = new HashMap<>();

        for (int i = 0; i < denomination.length; i++) {
            Denomination currentDenomination = denomination[i];
            Integer denominationValue = getInteger(currentDenomination);
            Integer count = atmRepository.getCount(currentDenomination);

            // Calculating maximum amount of notes that can be used of current denomination
            Integer amount = cash.getAmount();
            Integer notesUsed = amount / denominationValue;

            // Check if enough notes are available
            if (notesUsed > count) {
                notesUsed = count;
            }

            // If current denomination wasn't enough and left over is incompatible with next denomination
            // then remove one note used
            amount = amount - (notesUsed * denominationValue);
            if (i < denomination.length - 1) {
                Integer nextDenomination = getInteger(denomination[i + 1]);
                if (amount % nextDenomination != 0) {
                    amount += denominationValue;
                }
            }

            // Keep track of notes used
            denominationUsedMap.put(currentDenomination, notesUsed);
            if (amount == 0) {
                log.info("Sufficient notes available");
                withdrawCashFromRepository(denominationUsedMap);
                return true;
            }
        }

        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Not enough notes available");
    }

    private void withdrawCashFromRepository(Map<Denomination, Integer> denominationUsedMap) {
        for (Denomination denomination : denominationUsedMap.keySet()) {
            atmRepository.withdrawNotes(denomination, denominationUsedMap.get(denomination));
        }
    }
}
