package com.expencia.users.openfeign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "accountservice")
public interface AccountRestClient {



    @PostMapping("/createAccount")
    void createAccount(@RequestBody Long id);

    @GetMapping("/allAccounts")
    void getAccounts();

}
