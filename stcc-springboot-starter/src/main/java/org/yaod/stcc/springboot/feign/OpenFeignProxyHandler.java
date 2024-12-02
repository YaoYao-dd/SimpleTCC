package org.yaod.stcc.springboot.feign;


import org.yaod.stcc.core.annotation.ParticipateTCC;
import org.yaod.stcc.core.exception.StccRuntimeException;
import org.yaod.stcc.core.persistence.TxnRepositoryHolder;
import org.yaod.stcc.core.transaction.context.ContextHolder;
import org.yaod.stcc.core.transaction.context.Participant;
import org.yaod.stcc.core.transaction.enums.TransactionActionEnum;
import org.yaod.stcc.core.utils.InvocationUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Yaod
 **/
public class OpenFeignProxyHandler implements InvocationHandler {


    private InvocationHandler originalHandler;

    public OpenFeignProxyHandler(InvocationHandler originalHandler) {
        this.originalHandler = originalHandler;
    }

    @Override
    public Object invoke(final Object proxy, final Method tryMethod, final Object[] args) throws Throwable {

        var txnContext = ContextHolder.get();
        if (Objects.isNull(txnContext)) {
            return this.originalHandler.invoke(proxy, tryMethod, args);
        }
        ParticipateTCC participateTCC = tryMethod.getAnnotation(ParticipateTCC.class);
        if (Objects.isNull(participateTCC)) {
            return this.originalHandler.invoke(proxy, tryMethod, args);
        }

        var txnRepo = TxnRepositoryHolder.INST.getTxnPersistence();

        try {
            Participant undergoingParticipant=null;
            if(txnContext.getAction()== TransactionActionEnum.TRYING) {
                var invocation = InvocationUtils.constructInnovationForRpc(tryMethod, args);
                //register rpc participant into current transaction context.
                undergoingParticipant = txnContext.newParticipant(invocation);
                txnRepo.persistParticipant(undergoingParticipant, false);
            }else{
                undergoingParticipant=txnContext.activeParticipant();
            }

            var txnCopyForRpc = txnContext.stripCopy(undergoingParticipant);
            ContextHolder.setForNextRPC(txnCopyForRpc);
            return originalHandler.invoke(proxy, tryMethod, args);

        } catch (Throwable e) {
            throw new StccRuntimeException("api call failed", e);
        }

    }

}