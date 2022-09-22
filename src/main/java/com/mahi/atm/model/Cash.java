package com.mahi.atm.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Cash {

    private String currency;

    private Integer amount;
}
