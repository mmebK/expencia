package com.expencia.accounts.enums;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public enum Category {

    RENT(OperationType.DEBIT),
    FOOD(OperationType.DEBIT),
    BUILD(OperationType.DEBIT),
    UTILITIES(OperationType.DEBIT),
    TRANSPORTATION(OperationType.DEBIT),
    INSURANCE(OperationType.DEBIT),
    SHOPPING(OperationType.DEBIT),
    ENTERTAINMENT(OperationType.DEBIT),
    HEALTHCARE(OperationType.DEBIT),
    HOUSING(OperationType.DEBIT),
    TAXES(OperationType.DEBIT),
    CLOTHING(OperationType.DEBIT),
    EDUCATION(OperationType.DEBIT),
    PERSONAL_CARE(OperationType.DEBIT),
    MISCELLANEOUS(OperationType.DEBIT),
    SALARY(OperationType.CREDIT),
    BUSINESS(OperationType.CREDIT),
    EXTRA_INCOME(OperationType.DEBIT);

    OperationType operationType;

    Category(OperationType operationType) {
        this.operationType = operationType;
    }
}
