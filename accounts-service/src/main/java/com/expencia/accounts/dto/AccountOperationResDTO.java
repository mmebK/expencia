package com.expencia.accounts.dto;


import com.expencia.accounts.enums.Category;
import com.expencia.accounts.enums.OperationType;
import com.expencia.accounts.enums.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountOperationResDTO {

    private Long id;
    private BigDecimal amount;
    private OperationType type;
    private String description;
    private Date operationDate;
    private Category category;
    private PaymentMode paymentMode;

}
