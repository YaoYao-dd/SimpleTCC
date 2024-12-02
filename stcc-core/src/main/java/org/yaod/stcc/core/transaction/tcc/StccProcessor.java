package org.yaod.stcc.core.transaction.tcc;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaod.stcc.core.objectholder.SingletonObjectHolder;
import org.yaod.stcc.core.persistence.TxnRepositoryHolder;
import org.yaod.stcc.core.transaction.context.Invocation;
import org.yaod.stcc.core.transaction.context.Participant;
import org.yaod.stcc.core.transaction.context.Transaction;
import org.yaod.stcc.core.transaction.enums.TransactionStatusEnum;

import java.util.Objects;

/**
 * @author Yaod
 **/
public abstract class StccProcessor {
    private static final Logger LOGGER= LoggerFactory.getLogger(CoordinatorProcessor.class);
    public abstract Object processTransaction(Transaction transaction, Invocation invocation, ProceedingJoinPoint jointPoint) throws Throwable;



    protected void confirmOrCancelTxnCluster(Transaction txnContext, boolean isConfirm) {
        try {

            var coordinator = txnContext.getCoordinator();
            confirmOrCancelParticipantTxn(txnContext, coordinator, true, isConfirm);

            for (var participant : txnContext.getParticipants()) {
                confirmOrCancelParticipantTxn(txnContext, participant, false, isConfirm);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected Object confirmOrCancelParticipantTxn(Transaction txnContext, Participant participant, boolean isLocal, boolean isConfirm) {
        Object result=null;

        var txnRepo = TxnRepositoryHolder.INST.getTxnPersistence();
        var participantDB = txnRepo.getParticipantsBy(participant.getBranchTransId());

        //if the transaction status is not tried, skip cancel/confirm,
        //will have a report for in-stable transaction for human process.
        //e.g. for init status, it may be tried, or not tried at all, need analysis data in detail.
        if(participantDB==null || participantDB.getStatus() != TransactionStatusEnum.TRIED){
            LOGGER.warn(String.format("%s %s ","skip confirming/canceling as there is tried transaction available,", participant.getBranchTransId()));
            return result;
        }

        try {
            var invocationData = participant.getInvocationMeta();
            if (Objects.isNull(invocationData)) {
                return null;
            }
            txnContext.setActiveParticipant(participant);

            String methodName;
            if (isLocal && isConfirm) {
                methodName = invocationData.getConfirmMethod();
            } else if (isLocal && !isConfirm) {
                methodName = invocationData.getCancelMethod();
            } else {
                //for rpc call, always use the try method to execute all try/confirm/cancel operations.
                //the transaction context will instruct the target rpc to execute different operations on remote API process.
                methodName = invocationData.getTryMethod();
            }

            final Class<?> clazz = Class.forName(invocationData.getTargetClazz());
            final var targetObject = SingletonObjectHolder.INST.getObject(clazz);
            final Object[] args = invocationData.getArguments();
            final Class<?>[] parameterTypes = invocationData.getParameterTypes();

            result = MethodUtils.invokeMethod(targetObject, true, methodName, args, parameterTypes);
            participant.setStatus(isConfirm ? TransactionStatusEnum.CONFIRMED : TransactionStatusEnum.CANCELED);
            txnRepo.updateParticipantStatus(participant);
        } catch (Exception e) {
            LOGGER.error("confirm/cancel participant failed.", e);
        }

        txnRepo.updateParticipantStatus(participant);

        return result;
    }
}
