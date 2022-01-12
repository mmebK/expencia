package com.expencia.accounts.web;


import com.expencia.accounts.dto.AccountResDTO;
import com.expencia.accounts.entity.AppUser;
import com.expencia.accounts.openfeign.UserRestClient;
import com.expencia.accounts.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class AccountApi {

    AccountService accountService;
    UserRestClient userRestClient;

    @GetMapping("/getAccount")
    public AccountResDTO getAccount() {
        return accountService.getAccount();
    }

    @PostMapping("/createAccount")
    public AccountResDTO createAccount(@RequestBody Long id) {
        return accountService.saveAccount(id);
    }


    @GetMapping("/allAccounts")
    public List<AccountResDTO> getAllAccounts() {
        return accountService.getAll();
    }

    @GetMapping("/getUser")
    public AppUser getUser() {

        return userRestClient.getUser();
    }


    @GetMapping("/getTest")
    public String getTest() {

        return userRestClient.getTest();
    }

}
