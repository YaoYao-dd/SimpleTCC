package org.yaod.bank.transferout.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.yaod.bank.transferout.model.Account;

import java.math.BigDecimal;
import java.util.List;


/**
 * @author Yaod
 */
@Mapper
public interface AccountMapper {

    @Select(value = "select id, amount from account where id = #{id}")
    Account findBy(@Param("id") String id);

    @Select("select id, amount from account")
    List<Account> all();

    @Update("update  account set amount= amount - #{amount} where id = #{id}")
    int decrease(@Param("id") String id, @Param("amount") BigDecimal amount);
}