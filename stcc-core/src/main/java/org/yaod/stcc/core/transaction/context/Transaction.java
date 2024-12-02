package org.yaod.stcc.core.transaction.context;
import java.util.ArrayList;
import java.util.List;

import de.huxhorn.sulky.ulid.ULID;
import lombok.Data;
import lombok.Getter;
import org.yaod.stcc.core.transaction.enums.TransactionActionEnum;
import org.yaod.stcc.core.transaction.enums.TransactionRoleEnum;
import org.yaod.stcc.core.transaction.enums.TransactionStatusEnum;

@Data
public class Transaction {

    private static ULID ulidGen = new ULID();

    private String globalTransId;

    private TransactionActionEnum action;

    private final String transType = "TCC";
    @Getter
    private Participant coordinator;
    private List<Participant> participants=new ArrayList<>();

    private Participant activeParticipant;

    private int participantIndex=0;

    public Transaction() {
        this.globalTransId = ulidGen.nextULID();
    }

    public Participant startCoordinator(){
        this.action=TransactionActionEnum.TRYING;

        var aCoordinator=new Participant(this.getGlobalTransId(), ulidGen.nextULID());
        this.coordinator=aCoordinator;
        this.coordinator.setStatus(TransactionStatusEnum.INIT);
        this.coordinator.setRole(TransactionRoleEnum.COORDINATOR);
        this.coordinator.setInitiatorFlag(true);
        this.activeParticipant=aCoordinator;
        return aCoordinator;
    }

    public Participant newParticipant(Invocation invocationData){
        var newPar=new Participant(this.getGlobalTransId(), ulidGen.nextULID(), this.coordinator.getBranchTransId());
        newPar.setInvocationMeta(invocationData);
        newPar.setIndex(this.participantIndex++);
        this.registerParticipant(newPar);
        this.activeParticipant=newPar;
        return newPar;
    }

    public Participant activeParticipant(){
        return this.activeParticipant;
    }

    public void resetActiveParticipant(){
        this.activeParticipant=null;
    }

    private void registerParticipant(Participant aCoordinator){
        this.participants.add(aCoordinator);
    }

    public Transaction stripCopy(Participant newParticipant){
        var dumpTrans=new Transaction();
        dumpTrans.globalTransId=this.globalTransId;
        dumpTrans.action=this.action;
        dumpTrans.setCoordinator(newParticipant.stripCopy());
        return dumpTrans;
    }

    public boolean isInitiator() {
        return this.coordinator != null && this.coordinator.isInitiatorFlag();
    }
}


