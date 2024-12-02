    private String branchTransId;
    private String globalTransId;

    //this invocation metadata is used for subsequent confirm/cancel.
    private Invocation invocationMeta;

    //it is auto set as running application's name, only eligible for coordinator.
    private String appName;
    private TransactionStatusEnum status;

    //Can be coordinator, can be participant.
    //a participant in upstream API can become a coordinator in the downstream for nested transaction.
    private TransactionRoleEnum role;

    private long createdTs;
    private long lastUpdatedTs;

create table if not exists `stcc_trans_participant`
(
    `branch_trans_id`    char(20)  not null comment 'branch trans id' primary key,
    `global_trans_id`    char(20)   not null      comment 'global trans id',
    `trans_type`         varchar(6)   not null comment 'transaction type',
    `role`               tinyint       not null comment 'participant role',
    `status`             tinyint       not null comment 'branch transaction status',
    `app_name`           varchar(64)   not null comment 'app name',
    `retry`              int default 0 not null comment 'retry number',
    `target_class`       varchar(512)  null comment 'target class',
    `target_method`      varchar(128)  null comment 'try method',
    `confirm_method`     varchar(128)  null comment 'confirm method',
    `cancel_method`      varchar(128)  null comment 'cancel method',
    `invocation` longblob      null comment 'invocation metadata',
    `version`            int default 0 not null,
    `create_time`        datetime      not null comment 'create time',
    `update_time`        datetime      not null DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment 'last updated time'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci comment 'stcc participant records';