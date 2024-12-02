package org.yaod.stcc.core.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.yaod.stcc.core.annotation.TryTCC;
import org.yaod.stcc.core.transaction.context.Invocation;

import java.lang.reflect.Method;

/**
 * @author Yaod
 **/
public class InvocationUtils {

    public static Invocation convertInnovationFrm(ProceedingJoinPoint joinPoint){
        var pjpSignature =(MethodSignature)joinPoint.getSignature();
        var tryMethod = pjpSignature.getMethod();
        var arguments= joinPoint.getArgs();
        var tryTcc=tryMethod.getAnnotation(TryTCC.class);
        var confirmMethod= tryTcc.confirm();
        var cancelMethod = tryTcc.cancel();
        var targetClass = joinPoint.getTarget().getClass().getName();

        return new Invocation(targetClass,tryMethod.getParameterTypes(), arguments,tryMethod.getName(), confirmMethod,cancelMethod);

    }


    /**
     * rpc call have no cancel and confirm method, because at current process, it always calls the same name.
     * just with different transaction content, in the remote process, it will execute accordingly.
     * @param tryMethod
     * @param arguments
     * @return
     */
    public static Invocation constructInnovationForRpc(Method tryMethod, Object[] arguments){
        //rpc call
        return new Invocation(tryMethod.getDeclaringClass().getName(),tryMethod.getParameterTypes(), arguments,tryMethod.getName(), "","");

    }
}
