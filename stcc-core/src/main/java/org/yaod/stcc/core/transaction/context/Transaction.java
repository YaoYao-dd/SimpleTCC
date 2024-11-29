package org.yaod.stcc.core.transaction.context;
import java.util.ArrayList;
import java.util.List;

import de.huxhorn.sulky.ulid.ULID;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.yaod.stcc.core.transaction.enums.TransactionRoleEnum;

@Data
public class Transaction {

    private static ULID ulidGen = new ULID();

    private String globalTransId;


    private final String transType = "TCC";
    @Getter
    private Participant coordinator;
    private List<Participant> participants=new ArrayList<>();

    public Transaction() {
        this.globalTransId = ulidGen.nextULID();
    }

    public Participant startCoordinator(){
        var aCoordinator=new Participant(this.getGlobalTransId(), ulidGen.nextULID());
        this.coordinator=aCoordinator;
        this.coordinator.setRole(TransactionRoleEnum.COORDINATOR);
        this.coordinator.setInitiatorFlag(true);
        return aCoordinator;
    }

    public Participant newParticipant(Invocation invocationData){
        var newPar=new Participant(this.getGlobalTransId(), ulidGen.nextULID(), this.coordinator.getBranchTransId());
        newPar.setInvocationMeta(invocationData);
        this.registerParticipant(newPar);
        return newPar;
    }

    private void registerParticipant(Participant aCoordinator){
        this.participants.add(aCoordinator);
    }

    public Transaction stripCopy(){
        var dumpTrans=new Transaction();
        dumpTrans.globalTransId=this.globalTransId;
        return dumpTrans;
    }

    public boolean isInitiator() {
        return this.coordinator != null && this.coordinator.isInitiatorFlag();
    }
}


