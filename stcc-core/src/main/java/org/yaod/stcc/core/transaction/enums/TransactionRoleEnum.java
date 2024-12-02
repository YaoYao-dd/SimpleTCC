package org.yaod.stcc.core.transaction.enums;


public enum TransactionRoleEnum implements CodeableEnum {
    COORDINATOR(1), PARTICIPANT(2);

    private final int code;
    TransactionRoleEnum(int code) { this.code = code; }

    @Override
    public int getCode() {
        return code;
    }
}

