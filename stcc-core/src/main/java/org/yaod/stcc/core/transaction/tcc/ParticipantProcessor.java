package org.yaod.stcc.core.transaction.tcc;

import org.aspectj.lang.ProceedingJoinPoint;
import org.yaod.stcc.core.transaction.context.Invocation;
import org.yaod.stcc.core.transaction.context.Transaction;

/**
 * @author Yaod
 **/
public class ParticipantProcessor implements StccProcessor {
    @Override
    public Object processTransaction(Transaction transaction, Invocation invocation, ProceedingJoinPoint jointPoint) {
        return null;
    }


}
