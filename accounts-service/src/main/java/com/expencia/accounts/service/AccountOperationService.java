package com.expencia.accounts.service;

import com.expencia.accounts.dto.AccountOperationReqDTO;
import com.expencia.accounts.dto.AccountOperationResDTO;
import com.expencia.accounts.dto.ResultExpResDTO;
import com.expencia.accounts.dto.ResultStatsResDTO;
import com.expencia.accounts.enums.OperationType;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AccountOperationService {


    AccountOperationResDTO saveOperation(AccountOperationReqDTO operationReqDTO);

    AccountOperationResDTO updateOperation(Long operationId, AccountOperationReqDTO operationReqDTO);


    void deleteOperation(Long operationId);

    Page<AccountOperationResDTO> getAllOperations(int page, int size);

    AccountOperationResDTO getOperation(Long operationId);

    void deleteAllOperation();

    List findAllExpensesByMonth();
    //List getData(OperationType debit, OperationType credit);
    // Long findByMonth(int month);

    List<ResultExpResDTO> getExpensesByCategory(OperationType type);

    List<ResultStatsResDTO> getStatsByMonth(OperationType credit, OperationType debit);

}
