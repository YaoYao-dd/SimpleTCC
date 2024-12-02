package org.yaod.bank.transferin.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.yaod.bank.transferin.model.Account;

import java.math.BigDecimal;
import java.util.List;


/**
 * @author Yaod
 */
@Mapper
public interface AccountMapper {

    @Select(value = "select id, balance, available_balance as availableBalance  from account where id = #{id}")
    Account findBy(@Param("id") String id);

    @Select("select id, balance from account")
    List<Account> all();

    @Update("update  account set balance = balance + #{amount}  where id = #{id}")
    int tryDeposit(@Param("id") String id, @Param("amount") BigDecimal amount);

    @Update("update  account set  available_balance = available_balance + #{amount} where id = #{id}")
    int confirmDeposit(@Param("id") String id, @Param("amount") BigDecimal amount);

    @Update("update  account set balance= balance + #{amount} where id = #{id}")
    int cancelDeposit(@Param("id") String id, @Param("amount") BigDecimal amount);
}