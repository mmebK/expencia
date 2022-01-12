package com.expencia.users.modal;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {

    @Id
    private String id;

    private String username;

    private String firstName;

    private String lastName;

    private String address;

    private String city;

    private String country;

    private String postCode;

    private String email;

    private String password;

    private boolean activated;

    private AppUserRole role = AppUserRole.USER;

    private Long accountId;


}
