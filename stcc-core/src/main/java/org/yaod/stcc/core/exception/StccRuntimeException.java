package org.yaod.stcc.core.exception;

/**
 * @author Yaod
 **/
public class StccRuntimeException extends RuntimeException{
    public StccRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public StccRuntimeException(Throwable cause) {
        super(cause);
    }
}
