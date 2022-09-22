package com.mahi.atm.service;

import com.mahi.atm.model.Cash;
import com.mahi.atm.model.Denomination;
import com.mahi.atm.repository.ATMRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

class ATMServiceTest {

    ATMService atmService;
    ATMRepository atmRepository;

    Integer fiftyNotes;
    Integer twentyNotes;

    @BeforeEach
    void setUp() {
        fiftyNotes = 5;
        twentyNotes = 4;
        atmRepository = new ATMRepository();
        atmRepository.setNotes(Denomination.£50, 5);
        atmRepository.setNotes(Denomination.£20, 4);
        atmService = new ATMService(atmRepository);
    }

    @Test
    void depositCashTestFifty() {
        // Given
        Integer count = 1;
        Integer expected = fiftyNotes + count;

        // When
        Boolean response = atmService.depositCash(Denomination.£50, count);

        // Then
        assertTrue(response);
        assertEquals(expected, atmRepository.getCount(Denomination.£50));
        assertEquals(twentyNotes, atmRepository.getCount(Denomination.£20));
    }

    @Test
    void depositCashTestTwenty() {
        // Given
        Integer count = 2;
        Integer expected = twentyNotes + count;

        // When
        Boolean response = atmService.depositCash(Denomination.£20, count);

        // Then
        assertTrue(response);
        assertEquals(fiftyNotes, atmRepository.getCount(Denomination.£50));
        assertEquals(expected, atmRepository.getCount(Denomination.£20));
    }

    @Test
    void depositCashTestZero() {
        // When
        Boolean response = atmService.depositCash(Denomination.£20, 0);

        // Then
        assertTrue(response);
        assertEquals(fiftyNotes, atmRepository.getCount(Denomination.£50));
        assertEquals(twentyNotes, atmRepository.getCount(Denomination.£20));
    }

    @Test
    void calculateDenominationTestTwenties() {
        // Given
        Cash cash = Cash.builder().amount(60).currency("£").build();

        // When
        Boolean response = atmService.calculateDenomination(cash);

        // Then
        assertTrue(response);
        assertEquals(4, atmRepository.getCount(Denomination.£50));
        assertEquals(1, atmRepository.getCount(Denomination.£20));
    }

    @Test
    void calculateDenominationTestFifties() {
        // Given
        Cash cash = Cash.builder().amount(150).currency("£").build();

        // When
        Boolean response = atmService.calculateDenomination(cash);

        // Then
        assertTrue(response);
        assertEquals(2, atmRepository.getCount(Denomination.£50));
        assertEquals(4, atmRepository.getCount(Denomination.£20));
    }

    @Test
    void calculateDenominationTestNotEnough() {
        // Given
        Cash cash = Cash.builder().amount(300).currency("£").build();

        // When
        try {
            atmService.calculateDenomination(cash);
            fail( "My method didn't throw when I expected it to" );
        } catch (ResponseStatusException expectedException) {
            assertEquals("500 INTERNAL_SERVER_ERROR \"Not enough notes available\"", expectedException.getMessage());
        }

        // Then
        assertEquals(5, atmRepository.getCount(Denomination.£50));
        assertEquals(4, atmRepository.getCount(Denomination.£20));
    }

    @Test
    void calculateDenominationTestZero() {
        // Given
        Cash cash = Cash.builder().amount(0).currency("£").build();

        // When
        Boolean response = atmService.calculateDenomination(cash);

        // Then
        assertTrue(response);
        assertEquals(5, atmRepository.getCount(Denomination.£50));
        assertEquals(4, atmRepository.getCount(Denomination.£20));
    }
}