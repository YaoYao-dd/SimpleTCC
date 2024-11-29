package org.yaod.stcc.persistence;

import org.apache.ibatis.annotations.*;
import org.yaod.stcc.core.persistence.TccTransactionPersistence;
import org.yaod.stcc.core.transaction.context.Participant;

import java.util.List;


/**
 * @author Yaod
 *  stcc_trans_participant table
 *     `branch_trans_id`    char(20)  not null comment 'branch trans id' primary key,
 *     `global_trans_id`    char(20)          comment 'global trans id',
 *     `trans_type`         varchar(6)   not null comment 'transaction type',
 *     `role`               tinyint       not null comment 'participant role',
 *     `status`             tinyint       not null comment 'branch transaction status',
 *     `app_name`           varchar(64)   not null comment 'app name',
 *     `retry`              int default 0 not null comment 'retry number',
 *     `target_class`       varchar(512)  null comment 'target class',
 *     `target_method`      varchar(128)  null comment 'try method',
 *     `confirm_method`     varchar(128)  null comment 'confirm method',
 *     `cancel_method`      varchar(128)  null comment 'cancel method',
 *     `invocation` longblob      null comment 'invocation metadata',
 *     `version`            int default 0 not null,
 *     `create_time`        datetime      not null comment 'create time',
 *     `update_time`        datetime      not null DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment 'last updated time'
 */
@Mapper
public interface TccTransactionMapper extends TccTransactionPersistence {

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
                target_method,
                confirm_method,
                cancel_method,
                invocation,
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
                #{participant.invocationMeta.confirmMethod},
                #{participant.invocationMeta.cancelMethod},
                #{participant.invocationMeta},
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

//    branch_trans_id,
//    global_trans_id,
//    trans_type,
//    role,
//    status,
//    app_name,
//    target_class,
//    target_method,
//    confirm_method,
//    cancel_method,
//    invocation,
//    initiator_flag
    @Results(value = {
            @Result(property = "branchTransId", column = "branch_trans_id"),
            @Result(property="globalTransId", column = "global_trans_id"),
            @Result(property="drivingBranchTransId", column = "driving_branch_trans_id"),
            @Result(property="role", column = "role"),

            @Result(property="status", column = "status"),
            @Result(property="appName", column = "app_name"),
            @Result(property="invocationMeta", column = "invocation"),
            @Result(property="appName", column = "app_name"),
            @Result(property="initiatorFlag", column = "initiator_flag"),
    })
    @Select("select * from stcc_trans_participant")
    List<Participant> allParticipants();

    @Results(value = {
            @Result(property = "branchTransId", column = "branch_trans_id"),
            @Result(property="globalTransId", column = "global_trans_id"),
            @Result(property="drivingBranchTransId", column = "driving_branch_trans_id"),
            @Result(property="role", column = "role"),
            @Result(property="status", column = "status"),
            @Result(property="appName", column = "app_name"),
            @Result(property="invocationMeta", column = "invocation"),
            @Result(property="appName", column = "app_name"),
            @Result(property="initiatorFlag", column = "initiator_flag"),
    })
    @Select("select * from stcc_trans_participant where branch_trans_id = #{branchTransId}")
    Participant getParticipantsBy(@Param("branchTransId") String branchTransId);
}