package org.yaod.bank.transferout.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaod.bank.transferout.client.BankTransferInClient;
import org.yaod.bank.transferout.mapper.AccountMapper;
import org.yaod.bank.transferout.model.Account;
import org.yaod.stcc.core.annotation.TryTCC;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService {

    private static final Logger LOGGER= LoggerFactory.getLogger(AccountService.class);
    @Autowired
    private AccountMapper mapper;
    @Autowired
    private BankTransferInClient transferInClient;


    //@Transactional
    @TryTCC(confirm = "confirmWithdraw", cancel = "cancelWithdraw")
    public Account transferMoneyToAnotherBankAcct(String fromAccId, String toAccId, BigDecimal amount){
        LOGGER.info("trying withdraw");
        checkImpactedRows(mapper.tryWithdraw(fromAccId, amount));
        transferInClient.depositTo(toAccId, amount);

        return findBy(fromAccId);

    }

    public void confirmWithdraw(String fromAccId, String toAccId, BigDecimal amount){
        LOGGER.info("confirming");
        checkImpactedRows(mapper.confirmWithdraw(fromAccId, amount));
    }

    public void cancelWithdraw(String fromAccId, String toAccId, BigDecimal amount){
        LOGGER.info("cancelling");
        checkImpactedRows(mapper.cancelWithdraw(fromAccId, amount));
    }

    public Account findBy(String accountId) {
        return mapper.findBy(accountId);
    }


    public List<Account> all() {
        return mapper.all();
    }


    void checkImpactedRows(int rows){
        if(rows!=1){
            throw new RuntimeException("local transaction fails. impacted " +rows);
        }
    }
}
