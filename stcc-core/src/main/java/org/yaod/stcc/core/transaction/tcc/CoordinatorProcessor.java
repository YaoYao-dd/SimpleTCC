package org.yaod.stcc.core.transaction.tcc;

import org.aspectj.lang.ProceedingJoinPoint;
import org.yaod.stcc.core.persistence.TccTransactionRepository;
import org.yaod.stcc.core.transaction.context.ContextHolder;
import org.yaod.stcc.core.transaction.context.Invocation;
import org.yaod.stcc.core.transaction.context.Transaction;
import org.yaod.stcc.core.transaction.enums.TransactionStatusEnum;

import java.util.Objects;

/**
 * @author Yaod
 **/
public class CoordinatorProcessor implements StccProcessor {


    @Override
    public Object processTransaction(Transaction transaction, Invocation invocation, ProceedingJoinPoint jointPoint) throws Throwable {
        var trans= this.initTransactionWithInitiatorContext(invocation);
        TccTransactionRepository txnRepo = TccTransactionRepository.INST;
        Object result;
        try {
            txnRepo.persistParticipant(trans.getCoordinator(), true);
            result = jointPoint.proceed();
            trans.getCoordinator().setStatus(TransactionStatusEnum.TRIED);
            txnRepo.updateParticipantStatus(trans.getCoordinator());
        }catch(Exception exception){
            this.cancelGlobalTxn();
            throw exception;
        }finally {
            this.confirmGlobalTxn();
        }

        return result;
    }

    private Transaction initTransactionWithInitiatorContext(Invocation invocation){
        var trans=new Transaction();
        var participant=trans.startCoordinator();
        participant.setInvocationMeta(invocation);
        ContextHolder.set(trans);
        return trans;
    }

    private void cancelGlobalTxn(){
        var content = ContextHolder.get();
        var coordinator = content.getCoordinator();
        var invocationForCoordinator = coordinator.getInvocationMeta();

    }

    private Object cancelLocal(Invocation cancel) throws ClassNotFoundException {
        if (Objects.isNull(cancel)) {
            return null;
        }
        final Class<?> clazz = Class.forName(cancel.getTargetClazz());
        final String methodName=cancel.getCancelMethod();
        final Object[] args = cancel.getArguments();
        final Class<?>[] parameterTypes = cancel.getParameterTypes();
        final Object bean =null;//
        return null;
    }
    private void confirmGlobalTxn(){

    }
}
