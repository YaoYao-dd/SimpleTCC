package org.yaod.bank.transferin.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Account {

    private String id;
    private BigDecimal balance;

    private BigDecimal availableBalance;
}
