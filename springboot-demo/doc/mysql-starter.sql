DROP TABLE IF EXISTS user;
CREATE TABLE user
(
    `USER_AutoID`       int NOT NULL AUTO_INCREMENT,
    `USERNAME`          varchar(16) DEFAULT NULL,
    `PASSWORD`          varchar(16) DEFAULT NULL,
    `USER_CREATE_TIME`  datetime    DEFAULT NULL,
    `USER_REFRESH_TIME` datetime    DEFAULT NULL COMMENT '同步时间',
    `USER_REFRESH_MARK` tinyint     DEFAULT '0' COMMENT '同步标志（0未同步，1已同步）',
    `USER_DEL`          tinyint     DEFAULT '0' COMMENT '删除标志（0代表不删除，1代表删除）',
    `USER_UUID`         varchar(40) DEFAULT NULL,
    PRIMARY KEY (`USER_AutoID`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

DROP TABLE IF EXISTS role;
CREATE TABLE role
(
    `ROLE_AutoID`      int NOT NULL AUTO_INCREMENT,
    `ROLE_NAME`        varchar(25) DEFAULT NULL,
    `ROLE_UUID`        varchar(40) DEFAULT NULL,
    `ROLE_DEL`         tinyint(1)  DEFAULT '0' COMMENT '删除标志（0代表不删除，1代表删除）',
    `ROLE_CREATE_TIME` datetime    DEFAULT NULL,
    PRIMARY KEY (`ROLE_AutoID`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

DROP TABLE IF EXISTS user_role;
CREATE TABLE user_role
(
    `UR_AutoID`      int NOT NULL AUTO_INCREMENT,
    `USER_UUID`      varchar(40) DEFAULT NULL,
    `ROLE_UUID`      varchar(40) DEFAULT NULL,
    `UR_UUID`        varchar(40) DEFAULT NULL,
    `UR_CREATE_TIME` datetime    DEFAULT NULL,
    `UR_DEL`         tinyint     DEFAULT '0',
    PRIMARY KEY (`UR_AutoID`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;


DROP TABLE IF EXISTS permission;
CREATE TABLE permission
(
    `PERMISSION_AutoID`      int NOT NULL AUTO_INCREMENT,
    `PERMISSION_NAME`        varchar(255) DEFAULT NULL,
    `PERMISSION_URL`         varchar(255) DEFAULT NULL,
    `PERMISSION_CREATE_TIME` datetime     DEFAULT NULL,
    `PERMISSION_UUID`        varchar(40)  DEFAULT NULL,
    `PERMISSION_TYPE`        tinyint      DEFAULT NULL COMMENT '分为三类：1 系统，2 导航，3 菜单(打开某个功能)',
    `PERMISSION_PARENT_UUID` varchar(40)  DEFAULT NULL COMMENT '父级权限uuid',
    `PERMISSION_DEL`         tinyint      DEFAULT '0',
    PRIMARY KEY (`PERMISSION_AutoID`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;


DROP TABLE IF EXISTS role_permission;
CREATE TABLE role_permission
(
    `RP_AutoID`       int NOT NULL AUTO_INCREMENT,
    `ROLE_UUID`       varchar(40) DEFAULT NULL,
    `PERMISSION_UUID` varchar(40) DEFAULT NULL,
    `RP_CREATE_TIME`  datetime    DEFAULT NULL,
    `RP_DEL`          tinyint     DEFAULT '0',
    `RP_UUID`         varchar(40) DEFAULT NULL,
    PRIMARY KEY (`RP_AutoID`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;



DROP TABLE IF EXISTS `monitorinfo`;
CREATE TABLE `monitorinfo` (
                               `_id` int(11) NOT NULL AUTO_INCREMENT,
                               `uuid` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
                               `stationid` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
                               `lcommand` int(13) DEFAULT NULL,
                               `deviceip` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
                               `errMsg` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
                               `datetime` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
                               `datetime1` mediumtext COLLATE utf8_unicode_ci,
                               `pic1` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
                               `pic2` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
                               `state` int(2) unsigned zerofill DEFAULT NULL,
                               PRIMARY KEY (`_id`)
) ENGINE=InnoDB AUTO_INCREMENT=32795 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
