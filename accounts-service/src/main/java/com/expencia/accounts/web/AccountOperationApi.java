package com.expencia.accounts.web;


import com.expencia.accounts.dto.AccountOperationReqDTO;
import com.expencia.accounts.dto.AccountOperationResDTO;
import com.expencia.accounts.dto.ResultExpResDTO;
import com.expencia.accounts.dto.ResultStatsResDTO;
import com.expencia.accounts.entity.AccountOperation;
import com.expencia.accounts.enums.OperationType;
import com.expencia.accounts.repo.AccountOperationRepo;
import com.expencia.accounts.service.AccountOperationService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
public class AccountOperationApi {

    private final AccountOperationService accountOperationService;
    private final AccountOperationRepo repo;


    @PostMapping("/createOperation")
    public AccountOperationResDTO createOperation(@RequestBody AccountOperationReqDTO operationReqDTO) {
        System.out.println("we are in creaction of an operation");
        return accountOperationService.saveOperation(operationReqDTO);
    }

    @GetMapping("/getAllOperations")
    public Page<AccountOperationResDTO> getAllOperations(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "6") int size) {

        return accountOperationService.getAllOperations(page, size);

    }

    @GetMapping("/getOperation/{id}")
    public AccountOperationResDTO getOperation(@PathVariable Long id) {
        return accountOperationService.getOperation(id);
    }

    @DeleteMapping("/deleteOperation/{id}")
    public void deleteOperation(@PathVariable Long id) {
        accountOperationService.deleteOperation(id);
    }

    @PutMapping("/updateOperation/{id}")
    public AccountOperationResDTO createOperation(@PathVariable Long id, @RequestBody AccountOperationReqDTO operationReqDTO) {

        return accountOperationService.updateOperation(id, operationReqDTO);
    }


    @GetMapping("/getAllById/{id}")
    public Page<AccountOperation> getAll(@PathVariable long id, Pageable pageable) {
        return repo.findAllByAccount_Id(id, pageable);
    }

    @DeleteMapping("/deleteAllOperations")
    public void deleteAllOperations() {
        accountOperationService.deleteAllOperation();
    }

    @GetMapping("/getMonthlyStats")
    public List<ResultStatsResDTO> getStatsByMonth(@RequestParam(defaultValue = "CREDIT") OperationType credit, @RequestParam(defaultValue = "DEBIT") OperationType debit) {
        //return accountOperationService.findAllExpensesByMonth();
        return accountOperationService.getStatsByMonth(credit, debit);
    }

    @GetMapping("/getTotalExpenses")
    public List<ResultExpResDTO> getTotalExpenses(@RequestParam(defaultValue = "DEBIT") OperationType debit) {
        return this.accountOperationService.getExpensesByCategory(debit);
    }


}
