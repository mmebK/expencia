package com.expencia.users.service;

import com.expencia.users.dao.AppUserRepo;
import com.expencia.users.dto.AppUserDTO;
import com.expencia.users.mapper.AppUserMapper;
import com.expencia.users.modal.AppUser;
import com.expencia.users.openfeign.AccountRestClient;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.UUID;

@Repository
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final AppUserRepo appUserRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AccountRestClient accountRestClient;
    private final AppUserMapper mapper;


    @Override
    public AppUser saveUser(String userName, String email, String passWord) {
        AppUser user1 = appUserRepo.findByUsername(userName);
        AppUser user2 = appUserRepo.findByEmail(email);
        if (user1 != null)
            throw new RuntimeException("User Already exists");
        if (user2 != null)
            throw new RuntimeException("email already used");
        AppUser appUser = new AppUser();
        appUser.setUsername(userName);
        appUser.setPassword(bCryptPasswordEncoder.encode(passWord));
        appUser.setActivated(true);
        appUser.setEmail(email);
        Long accountId = Math.abs(UUID.randomUUID().getMostSignificantBits());
        appUser.setAccountId(accountId);

        accountRestClient.createAccount(accountId);

        appUserRepo.save(appUser);


        return appUser;
    }

    @Override
    public Boolean validateProfile(String accountId, String id) {
        return accountId.equals(id);
    }

    @Override
    public AppUser loadUserByUserName(String userName) {

        return appUserRepo.findByUsername(userName);
    }


    @Override
    public AppUser updateUser(Principal principal, AppUserDTO user) {

        if (!principal.getName().equals(user.getUsername()))
            throw new RuntimeException("can't be performed by this user");
        AppUser dbUser = appUserRepo.findByUsername(user.getUsername());
        //System.out.println(dbUser);

        dbUser.setAddress(user.getAddress());
        dbUser.setFirstName(user.getFirstName());
        dbUser.setLastName(user.getLastName());
        dbUser.setCountry(user.getCountry());
        dbUser.setCity(user.getCity());
        dbUser.setPostCode(user.getPostCode());
        this.appUserRepo.save(dbUser);
        //System.out.println(dbUser);
        return dbUser;
    }

    @Override
    public AppUserDTO getCurrentUser(Principal principal) {
        return mapper.fromAppUser(appUserRepo.findByUsername(principal.getName()));
    }


}
