package org.yaod.bank.transferin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yaod.bank.transferin.client.BankTransferInClient;
import org.yaod.bank.transferin.mapper.AccountMapper;
import org.yaod.bank.transferin.model.Account;
import org.yaod.stcc.core.annotation.TryTCC;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountMapper mapper;
    @Autowired
    private BankTransferInClient client;


    //@Transactional
    @TryTCC(confirm = "confirm", cancel = "cancel")
    public Account tryDeposit(String accountId, BigDecimal amount){
        System.out.println("trying");
        checkImpactedRows(mapper.tryDeposit(accountId, amount));
        var result =client.getPosts();
        return mapper.findBy(accountId);

    }

    public void confirm(String accountId, BigDecimal amount){
        System.out.println("confirming");
        checkImpactedRows(mapper.confirmDeposit(accountId, amount));
    }

    public void cancel(String accountId, BigDecimal amount){
        System.out.println("cancelling");
        checkImpactedRows(mapper.cancelDeposit(accountId, amount));
    }

    public Account findBy(String accountId) {
        return mapper.findBy(accountId);
    }


    public List<Account> all() {
        return mapper.all();
    }

    void checkImpactedRows(int rows){
        if(rows!=1){
            throw new RuntimeException("local transaction fails.");
        }
    }
}
