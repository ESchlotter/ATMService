package com.mahi.atm;
import com.mahi.atm.model.Denomination;
import com.mahi.atm.repository.ATMRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.Scanner;

@SpringBootApplication
@RequiredArgsConstructor
public class Main {

    private final ATMRepository atmRepository;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}