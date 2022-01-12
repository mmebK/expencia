package com.expencia.accounts.repo;

import com.expencia.accounts.dto.ResultExpResDTO;
import com.expencia.accounts.dto.ResultStatsResDTO;
import com.expencia.accounts.entity.AccountOperation;
import com.expencia.accounts.enums.OperationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface AccountOperationRepo extends JpaRepository<AccountOperation, Long>, PagingAndSortingRepository<AccountOperation, Long> {


    //List<AccountOperation> findAllByAccount_Id(Long accountId);

    AccountOperation findByIdAndAccount_id(Long operationId, Long accountId);

    List<AccountOperation> findAllByAccountId(Long id);

    Page<AccountOperation> findByAccountId(Long id, Pageable pageable);

    Page<AccountOperation> findAllByAccount_Id(Long id, Pageable pageable);

    void deleteByIdAndAccount_id(Long operationId, Long accountId);

    //@Query("select c from AccountOperation c")
   /* SELECT SUM(amount), DATE_FORMAT(op.operation_date, '%Y-%m')
    FROM db_accounts.account_operation op  where type="DEBIT" GROUP BY DATE_FORMAT(op.operation_date, '%Y/%m')*/
    /*@Query("select sum (op.amount),function('date_format', op.operationDate, '%Y-%m') " +
            "from AccountOperation op where op.type=com.expencia.accounts.enums.OperationType.DEBIT" +
            "group by function('date_format', op.operationDate, '%Y-%m')")*/


    @Query(value = " select sum (op.amount),function('date_format', op.operationDate, '%Y-%m') " +
            "from AccountOperation op where op.type=com.expencia.accounts.enums.OperationType.DEBIT" +
            " group by function('date_format',op.operationDate,'%Y-%m')")
    List findAllExpensesByMonth();

    /*@Query(" SELECT op.creditTot,tp.debitTot,op.opdate AS datte , (op.creditTot)-(tp.debitTot) AS balance  FROM join (select sum (op.amount),function('date_format', op.operationDate, '%Y-%m')) " +
            )
    List findAllExpensesByMonthsss();*/
    /*select op.creditTot,tp.debitTot,op.opdate as date, op.creditTot-tp.debitTot as balance from(
            select sum(ar.amount)as creditTot,DATE_FORMAT(ar.operation_date, '%Y/%m') as opdate
    from db_accounts.account_operation ar where type="CREDIT" group by DATE_FORMAT(ar.operation_date, '%Y/%m') )as op

    join(select sum(ar.amount) as debitTot,DATE_FORMAT(ar.operation_date, '%Y/%m') as opdate
    from db_accounts.account_operation ar where type="DEBIT" group by DATE_FORMAT(ar.operation_date, '%Y/%m')) as tp on op.opdate=tp.opdate*/
    @Query(nativeQuery = true, value = "SELECT op.creditTot as income,tp.debitTot as expense,op.opdate AS transactiondate , (op.creditTot)-(tp.debitTot) AS balance,op.creditCount+tp.debitCount as transactions FROM (select" +
            "  SUM(ar.amount) AS creditTot,Date_Format(ar.operation_date, '%Y/%m') AS opdate,COUNT(ar.amount) as creditCount" +
            "    FROM account_operation ar WHERE type=:#{#debit.name()} and account_id=:accountId GROUP BY Date_Format(ar.operation_date, '%Y/%m') )AS op" +
            "    JOIN (select SUM(ar.amount) AS debitTot,Date_Format(ar.operation_date, '%Y/%m') AS opdate,COUNT(ar.amount) as debitCount" +
            "    FROM account_operation ar WHERE type=:#{#credit.name()} and account_id=:accountId GROUP BY Date_Format(ar.operation_date, '%Y/%m')) AS tp ON op.opdate=tp.opdate order by op.opdate asc")
    List<ResultStatsResDTO> getStatsByMonth(OperationType debit, OperationType credit, Long accountId);

    /*@Query(nativeQuery = true, value = "SELECT qr.income AS  incomessss ,qr.expense as lkoerzp from (SELECT op.creditTot as income,tp.debitTot as expense,op.opdate AS transactiondate , (op.creditTot)-(tp.debitTot) AS balance,op.creditCount+tp.debitCount as transactions FROM (select" +
            "  SUM(ar.amount) AS creditTot,Date_Format(ar.operation_date, '%Y/%m') AS opdate,COUNT(ar.amount) as creditCount" +
            "    FROM account_operation ar WHERE type=:#{#debit.name()} GROUP BY Date_Format(ar.operation_date, '%Y/%m') )AS op" +
            "    JOIN (select SUM(ar.amount) AS debitTot,Date_Format(ar.operation_date, '%Y/%m') AS opdate,COUNT(ar.amount) as debitCount" +
            "    FROM account_operation ar WHERE type=:#{#credit.name()}  GROUP BY Date_Format(ar.operation_date, '%Y/%m')) AS tp ON op.opdate=tp.opdate) as qr")
    List findStatsByCategory(OperationType debit, OperationType credit);*/

    @Query(nativeQuery = true, value = "select SUM(op.amount) as totalexpenses,op.category   from  account_operation op WHERE type=:#{#debit.name()} and account_id=:accountId group by op.category")
    List<ResultExpResDTO> getExpensesByCategory(OperationType debit, Long accountId);


    /* @Query(value = "SELECT "
             + " DATE_FORMAT(operation.operationDate,'%Y-%m'), SUM(operation.amount) "²
             + " FROM AccountOperation operation"
             + " GROUP BY DATE_FORMAT(operation.production.productionDate,'%Y-%m')")
     public List getMonthlyScheduleAdherenceBySection();*/


}
