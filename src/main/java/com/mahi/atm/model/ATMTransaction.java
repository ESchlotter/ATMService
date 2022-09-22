package com.mahi.atm.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ATMTransaction {
    private Boolean isSuccessful;

    private Type type;

    private String currency;

    private Denomination denomination;

    private Integer amount;

    private String message;
}
