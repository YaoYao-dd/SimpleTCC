package org.yaod.stcc.core.persistence;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.yaod.stcc.core.transaction.context.Participant;

import java.util.List;

/**
 * @author Yaod
 **/
public interface TccTransactionPersistence {

    int persistParticipant(Participant participant,  boolean isInitiator);
    List<Participant> getParticipantsForTransaction(String transId);
    Participant getParticipantsBy( String branchTransId);

    int updateParticipantStatus(Participant participant);
}
