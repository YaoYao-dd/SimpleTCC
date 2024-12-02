package org.yaod.bank.transferin.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.yaod.stcc.core.annotation.ParticipateTCC;

import java.util.List;

/**
 * @author Yaod
 **/
@FeignClient(name= "mockserver", url = "http://localhost:3000/")
@ParticipateTCC
public interface BankTransferInClient {

    @RequestMapping(method = RequestMethod.GET, value = "/users")
    @ParticipateTCC
    List<User> getPosts();

    @RequestMapping(method = RequestMethod.GET, value = "/users/{id}")
    User getPostById(@PathVariable("id") String id);
}