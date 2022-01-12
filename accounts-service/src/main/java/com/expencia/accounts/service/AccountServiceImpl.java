package com.expencia.accounts.service;

import com.expencia.accounts.dto.AccountResDTO;
import com.expencia.accounts.entity.Account;
import com.expencia.accounts.entity.AppUser;
import com.expencia.accounts.exception.AppUserNotFoudException;
import com.expencia.accounts.mapper.AccountOperationMapper;
import com.expencia.accounts.openfeign.UserRestClient;
import com.expencia.accounts.repo.AccountRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private AccountRepo accountRepo;
    private AccountOperationMapper mapper;
    private UserRestClient userRestClient;


    private AppUser getAppUser() {
        AppUser appUser;
        try {
            appUser = userRestClient.getUser();
            System.out.println(appUser);
        } catch (Exception e) {
            throw new AppUserNotFoudException("this client not found");
        }
        return appUser;
    }

    @Override
    public AccountResDTO saveAccount(Long id) {
        Account account = new Account();
        account.setId(id);
        /*account.setBalance(UUID.randomUUID().getMostSignificantBits());
        account.setExpenses(UUID.randomUUID().getMostSignificantBits());
        account.setIncome(UUID.randomUUID().getMostSignificantBits());
        account.setTransactions(5);*/
        accountRepo.save(account);

        return mapper.fromAccount(account);
    }

    @Override
    public AccountResDTO debitAccount(BigDecimal amount) {
        return null;
    }

    @Override
    public AccountResDTO creditAccount(BigDecimal amount) {
        return null;
    }

    @Override
    public List<AccountResDTO> getAll() {
        List<Account> accounts = accountRepo.findAll();
        return accounts
                .stream().map(account -> mapper.fromAccount(account))
                .collect(Collectors.toList());
    }

    @Override
    public AccountResDTO getAccount() {
        AppUser appUser = getAppUser();
        Account account = accountRepo.findById(appUser.getAccountId()).orElse(null);
        return mapper.fromAccount(account);
    }



}
