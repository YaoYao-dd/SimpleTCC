package org.yaod.bank.transferout.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.yaod.bank.transferout.model.Account;
import org.yaod.stcc.core.annotation.ParticipateTCC;

import java.math.BigDecimal;

/**
 * @author Yaod
 **/
@FeignClient(name= "mockserver", url = "http://localhost:8081/")
@ParticipateTCC
public interface BankTransferInClient {

    @RequestMapping(method = RequestMethod.POST, value = "/accounts/transferTo/{accountTo}")
    @ParticipateTCC
    Object depositTo(@PathVariable("accountTo") String accountTo,@RequestParam("amount") BigDecimal amount);

    @RequestMapping(method = RequestMethod.GET, value = "/accounts/{id}")
    Account getAccountById(@PathVariable("id") String id);
}