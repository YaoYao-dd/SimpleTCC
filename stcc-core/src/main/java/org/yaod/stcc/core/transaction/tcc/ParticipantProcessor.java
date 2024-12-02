package org.yaod.stcc.core.transaction.tcc;

import org.aspectj.lang.ProceedingJoinPoint;
import org.yaod.stcc.core.exception.StccRuntimeException;
import org.yaod.stcc.core.persistence.TxnRepositoryHolder;
import org.yaod.stcc.core.transaction.context.Invocation;
import org.yaod.stcc.core.transaction.context.Transaction;
import org.yaod.stcc.core.transaction.enums.TransactionActionEnum;
import org.yaod.stcc.core.transaction.enums.TransactionStatusEnum;
import org.yaod.stcc.core.utils.InvocationUtils;

/**
 * @author Yaod
 **/
public class ParticipantProcessor extends StccProcessor {
    @Override
    public Object processTransaction(Transaction trans, Invocation invocation, ProceedingJoinPoint jointPoint) {

        //re-establish the invocation for confirm/cancel.
        trans.getCoordinator().setInvocationMeta(InvocationUtils.convertInnovationFrm(jointPoint));

        var txnRepo = TxnRepositoryHolder.INST.getTxnPersistence();
        Object result = null;
        try {
            if (trans.getAction() == TransactionActionEnum.TRYING) {
                txnRepo.persistParticipant(trans.getCoordinator(), false);
                result = jointPoint.proceed();
                trans.getCoordinator().setStatus(TransactionStatusEnum.TRIED);
                txnRepo.updateParticipantStatus(trans.getCoordinator());
            } else if (trans.getAction() == TransactionActionEnum.CONFIRMING) {
                confirmDrivenParticipatesTxn(trans);
            } else if (trans.getAction() == TransactionActionEnum.CANCELING) {
                cancelDrivenParticipatesTxn(trans);
            } else {
                result = jointPoint.proceed();
            }

        } catch (Throwable exception) {

            throw new StccRuntimeException("ParticipantProcessor failing", exception);
        }

        return result;
    }
    protected void confirmDrivenParticipatesTxn(Transaction txnContext) {
        txnContext.setAction(TransactionActionEnum.CONFIRMING);
        this.confirmOrCancelTxnCluster(txnContext, true);
        txnContext.resetActiveParticipant();
    }

    protected void cancelDrivenParticipatesTxn(Transaction txnContext) {
        txnContext.setAction(TransactionActionEnum.CANCELING);
        this.confirmOrCancelTxnCluster(txnContext,false);
        txnContext.resetActiveParticipant();
    }

}
