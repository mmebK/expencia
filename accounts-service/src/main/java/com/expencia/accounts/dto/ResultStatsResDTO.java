package com.expencia.accounts.dto;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;


public interface ResultStatsResDTO {

    BigDecimal getBalance();

    BigDecimal getIncome();

    BigDecimal getExpense();

    String getTransactionDate();

    int getTransactions();


}
