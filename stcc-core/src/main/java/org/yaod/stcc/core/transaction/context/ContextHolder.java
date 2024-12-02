package org.yaod.stcc.core.transaction.context;

import java.util.Objects;

/**
 * @author Yaod
 **/
public class ContextHolder {

    private static final ThreadLocal<Transaction> HOLDER=new ThreadLocal<>();
    private static final ThreadLocal<Transaction> HOLDER4NEXT_RPC=new ThreadLocal<>();

    private static ContextRetriever contextRetriever;

    public static Transaction get(){
        return HOLDER.get();
    }

    public static void set(Transaction trans){
        HOLDER.set(trans);
    }

    public static void setForNextRPC(Transaction trans){
        HOLDER4NEXT_RPC.set(trans);
    }
    public static Transaction getForNextRPC(){
        return HOLDER4NEXT_RPC.get();
    }

}
