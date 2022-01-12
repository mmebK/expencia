package com.expencia.accounts.service;

import com.expencia.accounts.dto.AccountOperationReqDTO;
import com.expencia.accounts.dto.AccountOperationResDTO;
import com.expencia.accounts.dto.ResultExpResDTO;
import com.expencia.accounts.dto.ResultStatsResDTO;
import com.expencia.accounts.entity.Account;
import com.expencia.accounts.entity.AccountOperation;
import com.expencia.accounts.entity.AppUser;
import com.expencia.accounts.enums.OperationType;
import com.expencia.accounts.exception.AppUserNotFoudException;
import com.expencia.accounts.mapper.AccountOperationMapper;
import com.expencia.accounts.openfeign.UserRestClient;
import com.expencia.accounts.repo.AccountOperationRepo;
import com.expencia.accounts.repo.AccountRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class AccountOperationServiceImpl implements AccountOperationService {

    private AccountRepo accountRepo;
    private AccountOperationRepo accountOperationRepo;
    private AccountOperationMapper operationMapper;
    private UserRestClient userRestClient;

    private AppUser getAppUser() {
        AppUser appUser;
        try {
            appUser = userRestClient.getUser();
            System.out.println(appUser);
        } catch (Exception e) {
            throw new AppUserNotFoudException("this client not found");
        }
        return appUser;
    }

    @Override
    public AccountOperationResDTO saveOperation(AccountOperationReqDTO operationReqDTO) {
        AppUser appUser = getAppUser();

        AccountOperation accountOperation = operationMapper.fromAccountOperationReqDTO(operationReqDTO);
        accountOperation.setOperationDate(new Date());
        Account account = accountRepo.findById(appUser.getAccountId()).orElse(null);

        accountOperation.setAccount(account);

        if (operationReqDTO.getType() == (OperationType.CREDIT)) {
            account.setIncome(account.getIncome().add(operationReqDTO.getAmount()));
            account.setBalance(account.getBalance().add(operationReqDTO.getAmount()));
            account.setTransactions(account.getTransactions() + 1);
        } else if (operationReqDTO.getType() == (OperationType.DEBIT)) {
            account.setExpenses(account.getExpenses().add(operationReqDTO.getAmount()));
            account.setBalance(account.getBalance().subtract(operationReqDTO.getAmount()));
            account.setTransactions(account.getTransactions() + 1);
        }
        accountOperationRepo.save(accountOperation);

        return operationMapper.fromAccountOperation(accountOperation);


    }


    @Override
    public AccountOperationResDTO updateOperation(Long operationId, AccountOperationReqDTO operationReqDTO) {
        AppUser appUser = getAppUser();
        Account account = accountRepo.findById(appUser.getAccountId()).orElse(null);
        AccountOperation operation = accountOperationRepo.getById(operationId);

        BigDecimal differenceAmount = operation.getAmount().subtract(operationReqDTO.getAmount()).abs();

        if (operationReqDTO.getType() == operation.getType()) {
            if (operationReqDTO.getType() == (OperationType.CREDIT)) {
                if (operationReqDTO.getAmount().compareTo(operation.getAmount()) > 0) {
                    account.setIncome(account.getIncome().add(differenceAmount));
                    account.setBalance(account.getBalance().add(differenceAmount));
                } else if (operationReqDTO.getAmount().compareTo(operation.getAmount()) < 0) {
                    account.setIncome(account.getIncome().subtract(differenceAmount));
                    account.setBalance(account.getBalance().subtract(differenceAmount));
                }


            } else if (operationReqDTO.getType() == (OperationType.DEBIT)) {
                if (operationReqDTO.getAmount().compareTo(operation.getAmount()) > 0) {

                    account.setExpenses(account.getExpenses().add(differenceAmount));
                    account.setBalance(account.getBalance().subtract(differenceAmount));
                } else if (operationReqDTO.getAmount().compareTo(operation.getAmount()) < 0) {
                    account.setExpenses(account.getExpenses().subtract(differenceAmount));
                    account.setBalance(account.getBalance().add(differenceAmount));
                }
            }
        } else if (operationReqDTO.getType() != operation.getType()) {
            log.info("we are here");
            //log.info(operation.get().toString());
            log.info(account.getBalance().toString());
            if (operationReqDTO.getType() == (OperationType.CREDIT)) {
                log.info("we are in credit");
                log.info("we are in creadit and the balance here is: " + account.getBalance());
                account.setExpenses(account.getExpenses().subtract(operation.getAmount()));
                account.setIncome(account.getIncome().add(operationReqDTO.getAmount()));
                account.setBalance(account.getBalance().add(operation.getAmount()).add(operationReqDTO.getAmount()));

            } else if (operationReqDTO.getType() == (OperationType.DEBIT)) {

                account.setExpenses(account.getExpenses().add(operationReqDTO.getAmount()));
                account.setIncome(account.getIncome().subtract(operation.getAmount()));
                account.setBalance(account.getBalance().subtract(operation.getAmount()).subtract(operationReqDTO.getAmount()));
            }

        }


        operation.setAmount(operationReqDTO.getAmount());
        operation.setType(operationReqDTO.getType());
        operation.setPaymentMode(operationReqDTO.getPaymentMode());
        operation.setDescription(operationReqDTO.getDescription());
        operation.setCategory(operationReqDTO.getCategory());


        accountOperationRepo.save(operation);


        return operationMapper.fromAccountOperation(operation);
    }


    @Override
    public AccountOperationResDTO getOperation(Long operationId) {
        AppUser appUser = getAppUser();
        Account account = accountRepo.findById(appUser.getAccountId()).orElse(null);

        assert account != null;
        return operationMapper.fromAccountOperation(accountOperationRepo.findByIdAndAccount_id(operationId, account.getId()));
    }

    @Override
    public void deleteAllOperation() {
        AppUser appUser = getAppUser();
        Account account = accountRepo.findById(appUser.getAccountId()).orElse(null);
        account.setTransactions(0);
        account.setIncome(BigDecimal.valueOf(0));
        account.setBalance(BigDecimal.valueOf(0));
        account.setExpenses(BigDecimal.valueOf(0));
        this.accountOperationRepo.deleteAll();
    }

    @Override
    public List findAllExpensesByMonth() {
        return accountOperationRepo.findAllExpensesByMonth();
    }

    @Override
    public List<ResultExpResDTO> getExpensesByCategory(OperationType type) {
        AppUser appUser = getAppUser();
        Account account = accountRepo.findById(appUser.getAccountId()).orElse(null);

        Long accountId = account.getId();
        return accountOperationRepo.getExpensesByCategory(type, accountId);
    }

    @Override
    public List<ResultStatsResDTO> getStatsByMonth(OperationType credit, OperationType debit) {
        AppUser appUser = getAppUser();
        Account account = accountRepo.findById(appUser.getAccountId()).orElse(null);
        Long accountId=account.getId();
        return accountOperationRepo.getStatsByMonth(credit,debit,accountId);
    }

    @Override
    public void deleteOperation(Long operationId) {
        AppUser appUser = getAppUser();
        Account account = accountRepo.findById(appUser.getAccountId()).orElse(null);

        AccountOperation accountOperation = accountOperationRepo.getById(operationId);
        if (accountOperation.getType() == OperationType.CREDIT) {
            account.setBalance(account.getBalance().subtract(accountOperation.getAmount()));
            account.setIncome(account.getIncome().subtract(accountOperation.getAmount()));
        } else {
            account.setBalance(account.getBalance().add(accountOperation.getAmount()));
            account.setExpenses(account.getExpenses().subtract(accountOperation.getAmount()));
        }
        account.setTransactions(account.getTransactions() - 1);


        accountOperationRepo.deleteByIdAndAccount_id(operationId, account.getId());
    }

    @Override
    public Page<AccountOperationResDTO> getAllOperations(int page, int size) {
        AppUser appUser = getAppUser();
        Account account = accountRepo.findById(appUser.getAccountId()).orElse(null);
        Pageable paging = PageRequest.of(page, size);

        List<AccountOperationResDTO> resDTOSList = accountOperationRepo.findAllByAccountId(account.getId()).stream().map(operation -> operationMapper.fromAccountOperation(operation)).collect(Collectors.toList());


        final int start = (int) paging.getOffset();
        final int end = Math.min((start + paging.getPageSize()), resDTOSList.size());
        final Page<AccountOperationResDTO> operationResDTOSpage = new PageImpl<>(resDTOSList.subList(start, end), paging, resDTOSList.size());


        return operationResDTOSpage;

    }


}
