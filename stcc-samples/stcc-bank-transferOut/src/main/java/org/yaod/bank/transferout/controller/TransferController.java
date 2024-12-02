package org.yaod.bank.transferout.controller;

import de.huxhorn.sulky.ulid.ULID;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import org.yaod.bank.transferout.model.Account;
import org.yaod.bank.transferout.service.AccountService;
import org.yaod.stcc.core.config.StccConfig;
import org.yaod.stcc.core.transaction.context.Participant;
import org.yaod.stcc.core.transaction.enums.TransactionRoleEnum;
import org.yaod.stcc.core.transaction.enums.TransactionStatusEnum;
import org.yaod.stcc.persistence.TccTransactionMapper;

import java.math.BigDecimal;
import java.util.List;

@Tag(name="Money movement")
@RestController
@RequestMapping("accounts")
public class TransferController {

    @Autowired
    private AccountService service;

    @Autowired
    private StccConfig hw;

    @Autowired
    private TccTransactionMapper tm;



    @Operation(summary = "")
    @PostMapping("transfer/{fromAcctId}")
    public Account transferMoney(@PathVariable String fromAcctId,@RequestParam String toAcctId, @RequestParam BigDecimal amount){
        return service.transferMoneyToAnotherBankAcct(fromAcctId, toAcctId, amount);
    }

    @Operation(summary = "")
    @GetMapping("{accountId}")
    public Account getAccountBy(@PathVariable String accountId){
        return service.findBy(accountId);
    }

    @Operation(summary = "")
    @GetMapping("")
    public List<Account> getAll(){

        return service.all();
    }
}
