package com.expencia.accounts.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountStatsDTO {

    BigDecimal balance;
    BigDecimal credit;
    BigDecimal debit;
    Date dateop;
    int transactions;
}
