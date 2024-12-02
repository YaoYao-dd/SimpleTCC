package org.yaod.stcc.core.transaction.enums;

/**
 * @author Yaod
 **/
public enum TransactionStatusEnum implements CodeableEnum {
    INIT(0), TRIED(1), CONFIRMED(2), CANCELED(4), FAILED(8);

    private final int code;
    TransactionStatusEnum(int code) {
        this.code = code;
    }

    @Override
    public int getCode() {
        return code;
    }
}
