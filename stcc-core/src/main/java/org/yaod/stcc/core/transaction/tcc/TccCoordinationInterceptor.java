package org.yaod.stcc.core.transaction.tcc;

import org.aspectj.lang.ProceedingJoinPoint;
import org.yaod.stcc.core.transaction.context.Invocation;
import org.yaod.stcc.core.transaction.context.Transaction;
import org.yaod.stcc.core.transaction.enums.TransactionRoleEnum;

import java.util.EnumMap;
import java.util.Objects;

/**
 * @author Yaod
 **/
public class TccCoordinationInterceptor {

    public static TccCoordinationInterceptor INST= new TccCoordinationInterceptor();

    private final EnumMap<TransactionRoleEnum, StccProcessor> processors=new EnumMap<>(TransactionRoleEnum.class);


    private TccCoordinationInterceptor(){
        this.initialize();
    }


    private void initialize(){
        processors.put(TransactionRoleEnum.COORDINATOR, new CoordinatorProcessor());
        processors.put(TransactionRoleEnum.PARTICIPANT, new ParticipantProcessor());
    }
    private StccProcessor chooseByTransactionAction(Transaction transaction){
        TransactionRoleEnum currentRole;
        //if there is no associated transaction, means this app firstly initialize the transaction.
        //then it has to be the coordinator.
        if(Objects.isNull(transaction) || transaction.isInitiator()){
            currentRole=TransactionRoleEnum.COORDINATOR;
        }else{
            currentRole=TransactionRoleEnum.PARTICIPANT;
        }
        return processors.get(currentRole);
    }


    public Object handleTCC(Transaction transaction, Invocation invocation, ProceedingJoinPoint jointPoint) throws Throwable {

        StccProcessor stccProcessor = this.chooseByTransactionAction(transaction);
        return stccProcessor.processTransaction(transaction, invocation, jointPoint);
    }
}
