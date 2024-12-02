package org.yaod.stcc.core.transaction.context;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

/**
 * @author Yaod
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Invocation {
    String targetClazz;
    Class<?>[] parameterTypes;
    Object[] arguments;
    String tryMethod;
    String confirmMethod;
    String cancelMethod;
 }
