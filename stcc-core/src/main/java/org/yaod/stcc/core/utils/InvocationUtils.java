package org.yaod.stcc.core.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.yaod.stcc.core.annotation.TryTCC;
import org.yaod.stcc.core.transaction.context.Invocation;

/**
 * @author Yaod
 **/
public class InvocationUtils {

    public static Invocation convertInnovation(ProceedingJoinPoint joinPoint){
        var pjpSignature =(MethodSignature)joinPoint.getSignature();
        var tryMethod = pjpSignature.getMethod();
        var arguments= joinPoint.getArgs();
        var tryTcc=tryMethod.getAnnotation(TryTCC.class);
        var confirmMethod= tryTcc.confirm();
        var concelMethod = tryTcc.cancel();
        var targetClass = joinPoint.getTarget().getClass().getName();

        return new Invocation(targetClass,tryMethod.getParameterTypes(), arguments,tryMethod.getName(), confirmMethod,concelMethod);

    }
}
