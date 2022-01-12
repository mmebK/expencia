package com.expencia.users.service;

import com.expencia.users.dto.AppUserDTO;
import com.expencia.users.modal.AppUser;

import java.security.Principal;


public interface UserService {
    AppUser saveUser(String userName, String email, String passWord);


    Boolean validateProfile(String accountId, String id);

    AppUser loadUserByUserName(String userName);

    AppUser updateUser(Principal pricipal, AppUserDTO user);

    AppUserDTO getCurrentUser(Principal principal);
}
