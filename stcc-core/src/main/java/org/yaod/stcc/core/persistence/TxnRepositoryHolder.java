package org.yaod.stcc.core.persistence;

/**
 * @author Yaod
 **/
public class TxnRepositoryHolder {

    public static TxnRepositoryHolder INST=new TxnRepositoryHolder();

    TccTransactionPersistence persistence;
    private TxnRepositoryHolder(){}

    public void setTxnPersistence(TccTransactionPersistence aPersistence){
        this.persistence=aPersistence;
    }

    public TccTransactionPersistence getTxnPersistence(){
       return this.persistence;
    }
}
