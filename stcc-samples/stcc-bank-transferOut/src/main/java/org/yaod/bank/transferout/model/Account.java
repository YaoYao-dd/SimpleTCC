package org.yaod.bank.transferout.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Account {

    private String id;
    private BigDecimal amount;
}
