package com.expencia.accounts.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {

    String id;

    String username;

    String email;

    Long accountId;


}
