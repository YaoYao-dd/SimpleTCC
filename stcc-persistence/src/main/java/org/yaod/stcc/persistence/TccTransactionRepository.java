package org.yaod.stcc.persistence;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.yaod.stcc.core.persistence.TccTransactionPersistence;
import org.yaod.stcc.core.transaction.context.Participant;

import java.util.List;

/**
 * @author Yaod
 **/
public class TccTransactionRepository implements TccTransactionPersistence{


    private final TccTransactionMapper tccPersistence;

    public TccTransactionRepository(TccTransactionMapper txnMapper) {
        this.tccPersistence = txnMapper;
    }

    @Transactional(propagation= Propagation.REQUIRES_NEW)
    @Override
    public int persistParticipant(Participant participant, boolean isInitiator) {
        return this.tccPersistence.persistParticipant(participant,isInitiator?1:0);

    }


    @Transactional(propagation= Propagation.REQUIRES_NEW)
    @Override
    public int updateParticipantStatus(Participant participant) {
        return this.tccPersistence.updateParticipantStatus(participant);
    }


    @Override
    public List<Participant> getParticipantsForTransaction(String transId) {
        return this.tccPersistence.getParticipantsForTransaction(transId);
    }


    public Participant getParticipantsBy(String branchTransId) {
        return this.tccPersistence.getParticipantsBy(branchTransId);
    }
}
