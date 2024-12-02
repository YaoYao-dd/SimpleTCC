package org.yaod.stcc.persistence;

import org.apache.ibatis.annotations.*;
import org.yaod.stcc.core.transaction.context.Participant;

import java.util.List;


/**
 * mybatis based transaction repository.
 * @author Yaod
 */
@Mapper
public interface TccTransactionMapper {
    @Insert(
            """
            Insert into stcc_trans_participant(
                branch_trans_id,
                global_trans_id,
                driving_branch_trans_id,
                trans_type,
                role,
                status,
                app_name,
                target_class,
                try_method,
                invocation,
                index_flag,
                initiator_flag

            )
            values(
                #{participant.branchTransId},
                #{participant.globalTransId},
                #{participant.drivingBranchTransId},
                'TCC',
                #{participant.role},
                #{participant.status},
                #{participant.appName},
                #{participant.invocationMeta.targetClazz},
                #{participant.invocationMeta.tryMethod},
                #{participant.invocationMeta},
                #{participant.index},
                #{isInitiator}
            )
            """
    )
    int persistParticipant(@Param("participant") Participant participant, @Param("isInitiator") int isInitiator);

    @Insert(
            """
            update stcc_trans_participant
                set status = #{participant.status}
                where branch_trans_id =  #{participant.branchTransId}
            """
    )
    int updateParticipantStatus(@Param("participant") Participant participant);

    @Results(id="allResultSetMap", value = {
            @Result(property = "branchTransId", column = "branch_trans_id"),
            @Result(property="globalTransId", column = "global_trans_id"),
            @Result(property="drivingBranchTransId", column = "driving_branch_trans_id"),
            @Result(property="role", column = "role"),

            @Result(property="status", column = "status"),
            @Result(property="appName", column = "app_name"),
            @Result(property="invocationMeta", column = "invocation"),
            @Result(property="appName", column = "app_name"),
            @Result(property="initiatorFlag", column = "initiator_flag"),
            @Result(property="index", column = "index_flag"),
    })
    @Select("select * from stcc_trans_participant where global_trans_id #{global_trans_id}")
    List<Participant> getParticipantsForTransaction(@Param("global_trans_id") String globalTransId);

    @ResultMap("allResultSetMap")
    @Select("select * from stcc_trans_participant where branch_trans_id = #{branchTransId}")
    Participant getParticipantsBy(@Param("branchTransId") String branchTransId);
}