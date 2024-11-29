package org.yaod.stcc.core.serializer;

import com.google.gson.Gson;
import org.yaod.stcc.core.transaction.context.Transaction;

/**
 * serialize/de-serialize striped transaction object.
 * we need pass the transaction content to downstream, chose Json for easier human being reading.
 * @author Yaod
 **/
public class TransactionSerializer{
    //need make sure is gson is thread-safe, if is, remove the synchronized modifier.
    private static final Gson gson =new Gson();

    public static TransactionSerializer INST=new TransactionSerializer();
    private TransactionSerializer(){}
    public synchronized String serialize(Transaction trans){
        return gson.toJson(trans);
    }

    public synchronized Transaction deSerialize(String transJson){
        return gson.fromJson(transJson, Transaction.class);
    }
}
