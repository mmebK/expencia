package com.expencia.accounts.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    private Long id;

    private BigDecimal balance= BigDecimal.valueOf(0L);

    private BigDecimal income= BigDecimal.valueOf(0L);

    private BigDecimal expenses= BigDecimal.valueOf(0);

    private int transactions=0;

   /* @Transient
    private AppUser appUser;*/


}
