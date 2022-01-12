package com.expencia.accounts.service;

import com.expencia.accounts.dto.AccountResDTO;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    AccountResDTO saveAccount(Long id);

    AccountResDTO debitAccount(BigDecimal amount);

    AccountResDTO creditAccount(BigDecimal amount);

    List<AccountResDTO> getAll();

    AccountResDTO getAccount();

    // TODO: 9/28/2021 when we create a transaction we load => user then => account and by reflection the transaction gonna be saved to both


}
