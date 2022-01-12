package com.expencia.users.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AppUserDTO {

    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String city;
    private String country;
    private String postCode;
    private Long accountId;
}
