package org.yaod.bank.transferout.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yaod.bank.transferout.mapper.AccountMapper;
import org.yaod.bank.transferout.model.Account;
import org.yaod.stcc.core.annotation.TryTCC;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountMapper mapper;

    //@Transactional
    @TryTCC(confirm = "confirm", cancel = "cancel")
    public Account transferOut(String accountId, BigDecimal amount){

        var impactedRows= mapper.decrease(accountId, amount);
        System.out.println(impactedRows);
        //raise RuntimeException.
        //int d= 230/0;
        return mapper.findBy(accountId);

    }

    public void confirm(){
        System.out.println("confirming");
    }

    public void cancel(){
        System.out.println("cancelling");
    }

    public Account findBy(String accountId) {
        return mapper.findBy(accountId);
    }


    public List<Account> all() {
        return mapper.all();
    }
}
