package org.yaod.stcc.core;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.yaod.stcc.core.transaction.context.ContextHolder;
import org.yaod.stcc.core.transaction.tcc.TccCoordinationInterceptor;
import org.yaod.stcc.core.utils.InvocationUtils;


/**
 * @author Yaod
 **/
@Aspect
public class StccTransactionAspect {
    @Around("@annotation(org.yaod.stcc.core.annotation.TryTCC)")
    public Object aroundHandlingStcc(final ProceedingJoinPoint tccTryJointPoint) throws Throwable {
        var transaction= ContextHolder.get();
        var invocation = InvocationUtils.convertInnovationFrm(tccTryJointPoint);
        return TccCoordinationInterceptor.INST.handleTCC(transaction, invocation, tccTryJointPoint);
    }
}
