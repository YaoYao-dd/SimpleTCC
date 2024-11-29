package org.yaod.stcc.core.transaction.tcc;

import org.aspectj.lang.ProceedingJoinPoint;
import org.yaod.stcc.core.transaction.context.Invocation;
import org.yaod.stcc.core.transaction.context.Transaction;

/**
 * @author Yaod
 **/
public interface StccProcessor {

    public abstract Object processTransaction(Transaction transaction, Invocation invocation, ProceedingJoinPoint jointPoint) throws Throwable;
}
