package org.yaod.stcc.core.transaction.tcc;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaod.stcc.core.objectholder.SingletonObjectHolder;
import org.yaod.stcc.core.persistence.TxnRepositoryHolder;
import org.yaod.stcc.core.transaction.context.ContextHolder;
import org.yaod.stcc.core.transaction.context.Invocation;
import org.yaod.stcc.core.transaction.context.Participant;
import org.yaod.stcc.core.transaction.context.Transaction;
import org.yaod.stcc.core.transaction.enums.TransactionActionEnum;
import org.yaod.stcc.core.transaction.enums.TransactionStatusEnum;

import java.util.Objects;

/**
 * @author Yaod
 **/
public class CoordinatorProcessor extends StccProcessor {


    @Override
    public Object processTransaction(Transaction transaction, Invocation invocation, ProceedingJoinPoint jointPoint) throws Throwable {
        var trans = this.initTransactionWithInitiatorContext(invocation);
        var txnRepo = TxnRepositoryHolder.INST.getTxnPersistence();
        Object result;
        try {
            txnRepo.persistParticipant(trans.getCoordinator(), true);
            trans.setAction(TransactionActionEnum.TRYING);
            result = jointPoint.proceed();
            trans.getCoordinator().setStatus(TransactionStatusEnum.TRIED);
            txnRepo.updateParticipantStatus(trans.getCoordinator());

            this.confirmGlobalTxn(trans);
        } catch (Exception exception) {
            this.cancelGlobalTxn(trans);
            throw exception;
        }

        return result;
    }

    private Transaction initTransactionWithInitiatorContext(Invocation invocation) {
        var trans = new Transaction();
        var participant = trans.startCoordinator();
        participant.setInvocationMeta(invocation);
        ContextHolder.set(trans);
        return trans;
    }
    private void confirmGlobalTxn(Transaction txnContext) {
        txnContext.setAction(TransactionActionEnum.CONFIRMING);
        this.confirmOrCancelTxnCluster(txnContext, true);
        txnContext.resetActiveParticipant();
    }

    private void cancelGlobalTxn(Transaction txnContext) {
        txnContext.setAction(TransactionActionEnum.CANCELING);
        this.confirmOrCancelTxnCluster(txnContext,false);
        txnContext.resetActiveParticipant();
    }

}
