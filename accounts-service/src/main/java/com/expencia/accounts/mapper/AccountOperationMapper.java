package com.expencia.accounts.mapper;

import com.expencia.accounts.dto.AccountOperationReqDTO;
import com.expencia.accounts.dto.AccountOperationResDTO;
import com.expencia.accounts.dto.AccountReqDTO;
import com.expencia.accounts.dto.AccountResDTO;
import com.expencia.accounts.entity.Account;
import com.expencia.accounts.entity.AccountOperation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountOperationMapper {

    AccountOperation fromAccountOperationReqDTO(AccountOperationReqDTO operationReqDTO);
    Account fromAccountReqDTO(AccountReqDTO accountReqDTO);

    AccountOperationResDTO fromAccountOperation(AccountOperation operation);
    AccountResDTO fromAccount(Account account);
/*
Page result=new PageImpl<>(accountOperationRepo.findByAccountId(account.getId(), paging));
        resultResDTO.setTotalNumElements(result.getNumberOfElements());
        resultResDTO.setTotalNumPages(result.getTotalPages());




        PageImpl<AccountOperation> allOperation = new PageImpl<>(accountOperationRepo.findByAccountId(account.getId(), paging));

        resultResDTO.setTotalNumPages(allOperation.getTotalPages());
        resultResDTO.setTotalNumElements(allOperation.getNumberOfElements());
        Page<AccountOperationResDTO> listOfOperation = (Page<AccountOperationResDTO>) allOperation.stream().map(accountOperation -> operationMapper.fromAccountOperation(accountOperation)).collect(Collectors.toList());
 */
}
