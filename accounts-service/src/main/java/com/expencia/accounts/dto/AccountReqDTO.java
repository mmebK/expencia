package com.expencia.accounts.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountReqDTO {

    private Long id;

    private BigDecimal balance;

    private BigDecimal income;

    private BigDecimal expenses;

    private int transactions;
}
