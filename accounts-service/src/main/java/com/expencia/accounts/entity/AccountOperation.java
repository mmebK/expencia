package com.expencia.accounts.entity;


import com.expencia.accounts.enums.Category;
import com.expencia.accounts.enums.OperationType;
import com.expencia.accounts.enums.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountOperation {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date operationDate;
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private OperationType type;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private PaymentMode paymentMode;



    private String description;

    @ManyToOne
    private Account account;
}
