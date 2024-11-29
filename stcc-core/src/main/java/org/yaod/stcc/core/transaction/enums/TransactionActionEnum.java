package org.yaod.stcc.core.transaction.enums;

/**
 * @author Yaod
 **/
public enum TransactionActionEnum implements CodeableEnum {
    TRYING(1), CONFIRMING(2), CANCELING(4);

    private final int code;
    TransactionActionEnum(int code) { this.code = code; }

    @Override
    public int getCode() {
        return code;
    }
}
