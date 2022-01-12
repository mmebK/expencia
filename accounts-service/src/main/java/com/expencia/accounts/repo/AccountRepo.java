package com.expencia.accounts.repo;

import com.expencia.accounts.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface AccountRepo extends JpaRepository<Account,Long> {


    //Account findByAppUser_Id(String id);


}
