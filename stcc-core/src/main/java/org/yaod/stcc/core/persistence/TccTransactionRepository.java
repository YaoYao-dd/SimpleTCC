package org.yaod.stcc.core.persistence;

import org.yaod.stcc.core.transaction.context.Participant;

import java.util.List;

/**
 * @author Yaod
 **/
public class TccTransactionRepository {

    public static TccTransactionRepository INST=new TccTransactionRepository();
    private TccTransactionPersistence tccPersistence;

    private TccTransactionRepository() {
    }

    public void setTccPersistence(TccTransactionPersistence tccPersistence) {
        this.tccPersistence = tccPersistence;
    }

    public void persistParticipant(Participant participant, boolean isInitiator) {
        this.tccPersistence.persistParticipant(participant,isInitiator?1:0);
    }

    public void updateParticipantStatus(Participant participant) {
        this.tccPersistence.updateParticipantStatus(participant);
    }


    public List<Participant> allParticipants() {
        return this.tccPersistence.allParticipants();
    }


    public Participant getParticipantsBy(String branchTransId) {
        return this.tccPersistence.getParticipantsBy(branchTransId);
    }
}
