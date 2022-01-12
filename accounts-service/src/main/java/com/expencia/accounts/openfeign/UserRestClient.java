package com.expencia.accounts.openfeign;


import com.expencia.accounts.entity.AppUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "userservice")
public interface UserRestClient {

    @GetMapping("/currentUser")
    AppUser getUser();

    @GetMapping("/test")
    String getTest();

}
