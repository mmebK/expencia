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
@AllArgsConstructor
@NoArgsConstructor
public class AccountOperationReqDTO {


    private BigDecimal amount;
    private String description;
    private OperationType type;
    private Category category;
    private PaymentMode paymentMode;


}
