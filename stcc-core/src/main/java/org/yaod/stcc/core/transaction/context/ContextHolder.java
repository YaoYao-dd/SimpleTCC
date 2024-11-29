package org.yaod.stcc.core.transaction.context;

/**
 * @author Yaod
 **/
public class ContextHolder {

    private static final ThreadLocal<Transaction> HOLDER=new ThreadLocal<>();

    public static Transaction get(){
        return HOLDER.get();
    }

    public static void set(Transaction trans){
        HOLDER.set(trans);
    }
}
