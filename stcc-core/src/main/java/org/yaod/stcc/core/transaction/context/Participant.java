package org.yaod.stcc.core.transaction.context;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.yaod.stcc.core.transaction.enums.TransactionRoleEnum;
import org.yaod.stcc.core.transaction.enums.TransactionStatusEnum;

/**
 * @author Yaod
 **/
@Data
@NoArgsConstructor
public class Participant {



    public Participant(String aTransId, String aBranchTransId, String aDrivingBranchTransId) {
        this.globalTransId = aTransId;
        this.branchTransId= aBranchTransId;
        this.drivingBranchTransId = aDrivingBranchTransId;
        var now=System.currentTimeMillis();
        this.createdTs=now;
        this.lastUpdatedTs=now;
        this.status= TransactionStatusEnum.TRYING;

    }
    public Participant(String aTransId, String aBranchTransId) {
        this(aTransId,aBranchTransId, null);
    }

    public Participant stripCopy(){
        var stripPar=new Participant(this.globalTransId, this.branchTransId);
        stripPar.branchTransId=this.branchTransId;
        stripPar.createdTs=this.createdTs;
        stripPar.lastUpdatedTs=this.lastUpdatedTs;
        stripPar.status=this.status;
        stripPar.role=this.role;
        stripPar.initiatorFlag=false;
        return stripPar;
    }

    private  String drivingBranchTransId;
    private String branchTransId;
    private String globalTransId;

    //this invocation metadata is used for subsequent confirm/cancel.
    private Invocation invocationMeta;

    //it is auto set as running application's name, only eligible for coordinator.
    private String appName="";
    private TransactionStatusEnum status;

    //Can be coordinator, can be participant.
    //a participant in upstream API can become a coordinator in the downstream for nested transaction.
    private TransactionRoleEnum role;

    private long createdTs;
    private long lastUpdatedTs;

    private boolean initiatorFlag = false;
}
