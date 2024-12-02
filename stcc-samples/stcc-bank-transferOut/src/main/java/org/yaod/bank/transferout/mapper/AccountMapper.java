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

    @Select(value = "select id, balance from account where id = #{id}")
    Account findBy(@Param("id") String id);

    @Select("select id, balance from account")
    List<Account> all();

    @Update("""
                update  account set available_balance = available_balance - #{amount}  where id = #{id}
                and available_balance>=#{amount}
                """)
    int tryWithdraw(@Param("id") String id, @Param("amount") BigDecimal amount);

    @Update("update  account set  balance = balance - #{amount} where id = #{id}")
    int confirmWithdraw(@Param("id") String id, @Param("amount") BigDecimal amount);

    @Update("update  account set available_balance= available_balance + #{amount} where id = #{id}")
    int cancelWithdraw(@Param("id") String id, @Param("amount") BigDecimal amount);
}