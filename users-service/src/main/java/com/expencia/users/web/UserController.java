package com.expencia.users.web;


import com.expencia.users.dao.AppUserRepo;
import com.expencia.users.dto.AppUserDTO;
import com.expencia.users.modal.AppUser;
import com.expencia.users.openfeign.AccountRestClient;
import com.expencia.users.service.UserService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {

    private UserService userService;
    private AppUserRepo userRepo;
    private AccountRestClient accountRestClient;


    @PostMapping("/register")
    public AppUser register(@RequestBody UserForm userForm) {

        return userService.saveUser(userForm.getUsername(), userForm.getEmail(), userForm.getPassword());
    }


    /*@GetMapping("/getuser")
    public AppUser getUser(@PathVariable Long id) {
        return userRepo.findById(id).orElse(null);
    }*/

    @GetMapping("/getall")
    public List<AppUser> getall() {

        return userRepo.findAll();
    }


    @PutMapping("/updateUserInfo")
    public AppUser updateUser(Principal principal, @RequestBody AppUserDTO user) {


        System.out.println("update called");
        // AppUser dbUser = userService.updateUser(principal, user);
        return userService.updateUser(principal, user);
    }

    @GetMapping("/currentUser")
    public AppUserDTO getUser(Principal principal) {
        System.out.println(principal.getName());
        System.out.println("this is called");
        return this.userService.getCurrentUser(principal);

    }

    @GetMapping("/accounts")
    public void getAccounts() {
        accountRestClient.getAccounts();
        ;
    }


    @GetMapping("/test")
    public String test() {
        return "this will work";
    }

}


@Data

@JsonIgnoreProperties
class UserForm {
    private String username;
    private String password;
    private String email;
}

