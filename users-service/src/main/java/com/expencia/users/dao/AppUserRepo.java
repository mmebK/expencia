package com.expencia.users.dao;

import com.expencia.users.modal.AppUser;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AppUserRepo extends MongoRepository<AppUser, String> {
    AppUser findByUsername(String username);

    AppUser findByEmail(String email);

}
