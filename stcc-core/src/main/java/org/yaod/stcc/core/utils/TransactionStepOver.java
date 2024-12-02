package org.yaod.stcc.core.utils;

import org.yaod.stcc.core.transaction.context.Participant;
import org.yaod.stcc.core.transaction.context.Transaction;

/**
 * @author Yaod
 **/
public class TransactionStepOver {

    /**
     * create new  transaction entity which would be sent to downstream api.
     * the participant(client) in current api will become the coordinator in the downstream api.
     *
     * @param currTran
     * @param newParticipant
     * @return .
     */
    public static Transaction stripTxnForNextRpcHop(Transaction currTran, Participant newParticipant){
        var transToNextApp= currTran.stripCopy(newParticipant);
        transToNextApp.setCoordinator(newParticipant.stripCopy());
        return transToNextApp;
    }

}
