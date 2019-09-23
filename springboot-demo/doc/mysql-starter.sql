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

INSERT INTO `permission` VALUES (1, '系统管理', NULL, '2019-03-04 10:41:11', 'pms-1-001', 2, NULL, 0);
INSERT INTO `permission` VALUES (2, '数据管理', NULL, '2019-03-04 10:41:11', 'pms-1-002', 2, NULL, 0);
INSERT INTO `permission` VALUES (3, '运营分析', NULL, '2019-03-04 10:41:11', 'pms-1-003', 2, NULL, 0);
INSERT INTO `permission` VALUES (4, '安全预警', NULL, '2019-03-04 10:41:11', 'pms-1-004', 2, NULL, 0);
INSERT INTO `permission` VALUES (5, '权限管理', NULL, '2019-03-04 10:41:11', 'pms-2-001', 2, 'pms-1-001', 0);
INSERT INTO `permission` VALUES (6, '设备管理', NULL, '2019-03-04 10:41:11', 'pms-2-002', 2, 'pms-1-001', 0);
INSERT INTO `permission` VALUES (7, '日志管理', NULL, '2019-03-04 10:41:11', 'pms-2-003', 3, 'pms-1-001', 0);
INSERT INTO `permission` VALUES (8, '接口管理', NULL, '2019-03-04 10:41:11', 'pms-2-004', 3, 'pms-1-001', 0);
INSERT INTO `permission` VALUES (9, '站点管理', NULL, '2019-03-04 10:41:11', 'pms-2-005', 3, 'pms-1-001', 0);
INSERT INTO `permission` VALUES (10, '客户管理', NULL, '2019-03-04 10:41:11', 'pms-2-006', 3, 'pms-1-001', 0);
INSERT INTO `permission` VALUES (11, '用户基本信息', 'user/index', '2019-03-04 10:41:11', 'pms-3-001', 3, 'pms-2-001', 0);
INSERT INTO `permission` VALUES (12, '权限分类设置', 'permission/index', '2019-03-04 10:41:11', 'pms-3-002', 3, 'pms-2-001', 0);
INSERT INTO `permission` VALUES (13, '摄像头注册', '', '2019-03-04 10:41:11', 'pms-3-003', 3, 'pms-2-002', 0);
INSERT INTO `permission` VALUES (14, '油机注册', '', '2019-03-04 10:41:11', 'pms-3-004', 3, 'pms-2-002', 0);
INSERT INTO `permission` VALUES (15, '摄像头数据', NULL, '2019-03-04 10:41:11', 'pms-2-007', 2, 'pms-1-002', 0);
INSERT INTO `permission` VALUES (16, '油机数据', 'user/index', '2019-03-04 10:41:11', 'pms-2-008', 3, 'pms-1-002', 0);
INSERT INTO `permission` VALUES (17, '画像数据', NULL, '2019-03-04 10:41:11', 'pms-2-009', 3, 'pms-1-002', 0);
INSERT INTO `permission` VALUES (18, '导入导出', NULL, '2019-03-04 10:41:11', 'pms-2-010', 2, 'pms-1-002', 0);
INSERT INTO `permission` VALUES (19, '干道车流数据', NULL, '2019-03-04 10:41:11', 'pms-3-009', 3, 'pms-2-007', 0);
INSERT INTO `permission` VALUES (21, '车牌数据', 'monitorinfo/car_plate', '2019-03-04 10:41:11', 'pms-3-011', 3, 'pms-2-007', 0);
INSERT INTO `permission` VALUES (22, '车道车牌数据', NULL, '2019-03-04 10:41:11', 'pms-3-012', 3, 'pms-2-007', 0);
INSERT INTO `permission` VALUES (23, '便利店人流数据', 'monitorinfo/human_flow', '2019-03-04 10:41:11', 'pms-3-013', 3, 'pms-2-007', 0);
INSERT INTO `permission` VALUES (24, '便利店人脸数据', 'monitorinfo/human_face', '2019-03-04 10:41:11', 'pms-3-014', 3, 'pms-2-007', 0);
INSERT INTO `permission` VALUES (25, '卸油区视频数据', NULL, '2019-03-04 10:41:11', 'pms-3-015', 3, 'pms-2-007', 0);
INSERT INTO `permission` VALUES (26, '数据导入', NULL, '2019-03-04 10:41:11', 'pms-3-018', 3, 'pms-2-010', 0);
INSERT INTO `permission` VALUES (27, '数据导出', NULL, '2019-03-04 10:41:11', 'pms-3-019', 3, 'pms-2-010', 0);
INSERT INTO `permission` VALUES (28, '单站分析', '', '2019-09-16 05:15:05', 'c05c21da-fe32-4120-b16d-4d661418e991', 2, 'pms-1-003', 0);
INSERT INTO `permission` VALUES (29, '综合分析', '', '2019-09-16 05:16:37', '05b3f8ff-78cc-40f6-a63b-ad5f772b031f', 2, 'pms-1-003', 0);
INSERT INTO `permission` VALUES (30, '入站率分析', '', '2019-09-16 05:18:05', 'b2c0ddc0-3484-4f3e-8c32-1b532ac753b0', 3, 'c05c21da-fe32-4120-b16d-4d661418e991', 0);
INSERT INTO `permission` VALUES (31, '加油客户分析', '', '2019-09-16 05:18:14', '6ef081e0-01de-4853-9333-011566036d24', 3, 'c05c21da-fe32-4120-b16d-4d661418e991', 0);
INSERT INTO `permission` VALUES (32, '入店比分析', '', '2019-09-16 05:18:23', '19c07274-ecf1-443e-b307-865e8e1ddfd7', 3, 'c05c21da-fe32-4120-b16d-4d661418e991', 0);
INSERT INTO `permission` VALUES (33, '非油品客户分析', '', '2019-09-16 05:18:31', 'e70fe106-f3c9-43d3-bacb-76d20d61bcaa', 3, 'c05c21da-fe32-4120-b16d-4d661418e991', 0);
INSERT INTO `permission` VALUES (34, '站内停留分析', '', '2019-09-16 05:18:38', '3dd46b73-99e8-4156-a960-ebd08b39306d', 3, 'c05c21da-fe32-4120-b16d-4d661418e991', 0);
INSERT INTO `permission` VALUES (35, '入站率分析', '', '2019-09-16 05:18:48', '6c18b034-1d3a-43c3-b994-00f800b99c4e', 3, '05b3f8ff-78cc-40f6-a63b-ad5f772b031f', 0);
INSERT INTO `permission` VALUES (36, '加油客户分析', '', '2019-09-16 05:18:55', '36e59353-3150-45d9-8edd-edbcd32f51d7', 3, '05b3f8ff-78cc-40f6-a63b-ad5f772b031f', 0);
INSERT INTO `permission` VALUES (37, '入店比分析', '', '2019-09-16 05:19:02', '89e0a46b-2a6a-443a-a4f5-7a35f1245597', 3, '05b3f8ff-78cc-40f6-a63b-ad5f772b031f', 0);
INSERT INTO `permission` VALUES (38, '非油品客户分析', '', '2019-09-16 05:19:10', 'd2ad93ad-28f9-4263-bc7d-c37613c2dd4c', 3, '05b3f8ff-78cc-40f6-a63b-ad5f772b031f', 0);
INSERT INTO `permission` VALUES (39, '站内停留分析', '', '2019-09-16 05:29:22', '772a41ec-a14a-4cfb-91b6-1e5c27b7f9ec', 3, '05b3f8ff-78cc-40f6-a63b-ad5f772b031f', 0);
INSERT INTO `permission` VALUES (40, '卸油操作', '', '2019-09-16 05:29:32', '7089a975-d548-4790-9764-fcc3a76970d7', 2, 'pms-1-004', 0);
INSERT INTO `permission` VALUES (41, '卸油配送单', '', '2019-09-16 05:29:40', '0f7e1199-29cd-49cb-8096-8e94a4ba3cc3', 3, '7089a975-d548-4790-9764-fcc3a76970d7', 0);
INSERT INTO `permission` VALUES (42, '卸油车信息', '', '2019-09-16 05:29:49', '927e85a0-7348-4120-9d1d-99ad9f9ecda3', 3, '7089a975-d548-4790-9764-fcc3a76970d7', 0);
INSERT INTO `permission` VALUES (43, '卸油预警', '', '2019-09-16 05:29:58', '73d2a70e-e447-4f82-8ceb-f4e04d96c1dd', 3, '7089a975-d548-4790-9764-fcc3a76970d7', 0);
INSERT INTO `permission` VALUES (44, '卸油记录', '', '2019-09-16 05:30:06', 'e2b7b1ea-3c71-4465-b4da-5519fc07baf9', 3, '7089a975-d548-4790-9764-fcc3a76970d7', 0);
INSERT INTO `permission` VALUES (45, '黑名单管理', '', '2019-09-16 05:30:12', 'bf7d3afd-40f6-4411-8ce5-67492e0056da', 2, 'pms-1-004', 0);
INSERT INTO `permission` VALUES (46, '黑名单登记', '', '2019-09-16 05:30:20', '30d6d6b6-0047-4e1d-8e38-3e386f71ee97', 3, 'bf7d3afd-40f6-4411-8ce5-67492e0056da', 0);
INSERT INTO `permission` VALUES (47, '黑名单分析', '', '2019-09-16 05:30:26', '7ed95063-aba0-4125-81d5-ebdc27afaa20', 3, 'bf7d3afd-40f6-4411-8ce5-67492e0056da', 0);
INSERT INTO `permission` VALUES (48, '角色权限管理', 'role/index', '2019-09-18 02:19:37', '129b2f62-a47c-4e23-b894-7b2404fe98d1', 3, 'pms-2-001', 0);
INSERT INTO `role` VALUES (1, '超级管理员', 'admin-001', 0, '2018-06-22 15:39:45');
INSERT INTO `role` VALUES (2, '站长', 'zhanzhang-002', 0, '2018-06-22 16:39:45');
INSERT INTO `role_permission` VALUES (1, 'admin-001', 'pms-1-001', '2018-06-22 15:39:45', 0, 'rp-001');
INSERT INTO `role_permission` VALUES (2, 'admin-001', 'pms-1-002', '2018-06-22 15:39:45', 0, 'rp-002');
INSERT INTO `role_permission` VALUES (3, 'admin-001', 'pms-1-003', '2018-06-22 15:39:45', 0, 'rp-003');
INSERT INTO `role_permission` VALUES (4, 'admin-001', 'pms-1-004', '2018-06-22 15:39:45', 1, 'rp-004');
INSERT INTO `role_permission` VALUES (5, 'admin-001', 'pms-2-001', '2018-06-22 15:39:45', 0, 'rp-005');
INSERT INTO `role_permission` VALUES (6, 'admin-001', 'pms-2-002', '2018-06-22 15:39:45', 1, 'rp-006');
INSERT INTO `role_permission` VALUES (7, 'admin-001', 'pms-2-003', '2018-06-22 15:39:45', 1, 'rp-007');
INSERT INTO `role_permission` VALUES (8, 'admin-001', 'pms-2-004', '2018-06-22 15:39:45', 1, 'rp-008');
INSERT INTO `role_permission` VALUES (9, 'admin-001', 'pms-2-005', '2018-06-22 15:39:45', 1, 'rp-009');
INSERT INTO `role_permission` VALUES (10, 'admin-001', 'pms-2-006', '2018-06-22 15:39:45', 1, 'rp-010');
INSERT INTO `role_permission` VALUES (11, 'admin-001', 'pms-2-007', '2018-06-22 15:39:45', 0, 'rp-011');
INSERT INTO `role_permission` VALUES (12, 'admin-001', 'pms-2-008', '2018-06-22 15:39:45', 1, 'rp-012');
INSERT INTO `role_permission` VALUES (13, 'admin-001', 'pms-2-009', '2018-06-22 15:39:45', 1, 'rp-013');
INSERT INTO `role_permission` VALUES (14, 'admin-001', 'pms-2-010', '2018-06-22 15:39:45', 1, 'rp-014');
INSERT INTO `role_permission` VALUES (15, 'admin-001', 'pms-3-001', '2018-06-22 15:39:45', 0, 'rp-015');
INSERT INTO `role_permission` VALUES (16, 'admin-001', 'pms-3-002', '2018-06-22 15:39:45', 0, 'rp-016');
INSERT INTO `role_permission` VALUES (17, 'admin-001', 'pms-3-003', '2018-06-22 15:39:45', 0, 'rp-017');
INSERT INTO `role_permission` VALUES (18, 'admin-001', 'pms-3-004', '2018-06-22 15:39:45', 0, 'rp-018');
INSERT INTO `role_permission` VALUES (19, 'admin-001', 'pms-3-005', '2018-06-22 15:39:45', 0, 'rp-019');
INSERT INTO `role_permission` VALUES (20, 'admin-001', 'pms-3-006', '2018-06-22 15:39:45', 0, 'rp-020');
INSERT INTO `role_permission` VALUES (21, 'admin-001', 'pms-3-007', '2018-06-22 15:39:45', 0, 'rp-021');
INSERT INTO `role_permission` VALUES (22, 'admin-001', 'pms-3-008', '2018-06-22 15:39:45', 0, 'rp-022');
INSERT INTO `role_permission` VALUES (23, 'admin-001', '129b2f62-a47c-4e23-b894-7b2404fe98d1', '2018-06-22 15:39:45', 0, 'rp-03');
INSERT INTO `role_permission` VALUES (25, 'admin-001', 'pms-3-009', '2019-09-19 11:28:55', 1, '6af448b6-ce59-4f74-b39a-9ce5a5b67d33');
INSERT INTO `role_permission` VALUES (26, 'admin-001', 'pms-3-010', '2019-09-19 11:28:55', 0, '5d7fb157-8efb-4983-99d8-93d302c805da');
INSERT INTO `role_permission` VALUES (27, 'admin-001', 'pms-3-011', '2019-09-19 11:29:08', 0, '1387a24d-b0b8-44ff-8d49-80d21faf2d4b');
INSERT INTO `role_permission` VALUES (28, 'admin-001', 'pms-3-012', '2019-09-19 11:29:08', 1, '2fb462b2-03c6-4bd6-b179-5a23dad47ade');
INSERT INTO `role_permission` VALUES (29, 'admin-001', 'pms-3-013', '2019-09-19 11:29:09', 0, '557a1f18-7366-432d-bef8-546d15b1e006');
INSERT INTO `role_permission` VALUES (30, 'admin-001', 'pms-3-014', '2019-09-19 11:29:09', 0, 'a6741d9c-3531-4428-9de6-d9309d68c9d3');
INSERT INTO `role_permission` VALUES (31, 'admin-001', 'pms-3-015', '2019-09-19 11:29:09', 1, 'cecec7d9-5e5b-422a-a48c-43e4f41d7d2a');
INSERT INTO `role_permission` VALUES (32, 'admin-001', 'pms-3-018', '2019-09-19 11:29:10', 0, '963381bc-ff67-45fc-a79f-299b17a1f3f8');
INSERT INTO `role_permission` VALUES (33, 'admin-001', 'pms-3-019', '2019-09-19 11:29:10', 0, '2db33cc2-1aac-475b-b671-adb628eb4019');
INSERT INTO `role_permission` VALUES (34, 'admin-001', 'c05c21da-fe32-4120-b16d-4d661418e991', '2019-09-19 11:29:11', 0, 'b7bcc0b1-d02a-4275-86a6-0729a7bc878a');
INSERT INTO `role_permission` VALUES (35, 'admin-001', '05b3f8ff-78cc-40f6-a63b-ad5f772b031f', '2019-09-19 11:29:11', 0, '5587979b-8996-49b2-b007-b57f7c338a83');
INSERT INTO `role_permission` VALUES (36, 'admin-001', 'b2c0ddc0-3484-4f3e-8c32-1b532ac753b0', '2019-09-19 11:29:11', 0, 'b3d40eb0-afeb-4c86-a414-6bc0e5862744');
INSERT INTO `role_permission` VALUES (37, 'admin-001', '6ef081e0-01de-4853-9333-011566036d24', '2019-09-19 11:29:13', 0, 'ca2bfdcd-1f80-4473-9a15-22096d0a44da');
INSERT INTO `role_permission` VALUES (38, 'admin-001', '19c07274-ecf1-443e-b307-865e8e1ddfd7', '2019-09-19 11:29:13', 0, '457ee611-54da-4228-b785-711fb0e3a567');
INSERT INTO `role_permission` VALUES (39, 'admin-001', 'e70fe106-f3c9-43d3-bacb-76d20d61bcaa', '2019-09-19 11:29:14', 0, 'ad77e2d0-d56d-405b-8d57-49d5d8d4233a');
INSERT INTO `role_permission` VALUES (40, 'admin-001', '3dd46b73-99e8-4156-a960-ebd08b39306d', '2019-09-19 11:29:14', 0, 'c9650e37-5ee4-450e-b76d-c3e734b12ca0');
INSERT INTO `role_permission` VALUES (41, 'admin-001', '6c18b034-1d3a-43c3-b994-00f800b99c4e', '2019-09-19 11:29:14', 0, '0d7d9c21-0458-419a-b171-78024f1a1860');
INSERT INTO `role_permission` VALUES (42, 'admin-001', '36e59353-3150-45d9-8edd-edbcd32f51d7', '2019-09-19 11:29:15', 0, 'caaf7ab2-8fbc-41a4-877c-4fd8c7f0c485');
INSERT INTO `role_permission` VALUES (43, 'admin-001', '89e0a46b-2a6a-443a-a4f5-7a35f1245597', '2019-09-19 11:29:15', 0, 'c3a88a60-b5f4-4042-bf24-f2ec6c65da01');
INSERT INTO `role_permission` VALUES (44, 'admin-001', 'd2ad93ad-28f9-4263-bc7d-c37613c2dd4c', '2019-09-19 11:29:16', 0, '3cfa7204-378b-4f34-b7df-85e57144fb9f');
INSERT INTO `role_permission` VALUES (45, 'admin-001', '772a41ec-a14a-4cfb-91b6-1e5c27b7f9ec', '2019-09-19 11:29:16', 0, '1c5f7d7f-0a84-4d08-855a-09126ea94398');
INSERT INTO `role_permission` VALUES (46, 'admin-001', '7089a975-d548-4790-9764-fcc3a76970d7', '2019-09-19 11:29:16', 0, 'a6df8ad7-8174-4212-bd11-3497db9cfd2a');
INSERT INTO `role_permission` VALUES (47, 'admin-001', '0f7e1199-29cd-49cb-8096-8e94a4ba3cc3', '2019-09-19 11:29:18', 0, 'ed9bffd2-2b1b-4db5-bc90-ad71542282aa');
INSERT INTO `role_permission` VALUES (48, 'admin-001', '927e85a0-7348-4120-9d1d-99ad9f9ecda3', '2019-09-19 11:29:18', 0, '3c6a0a72-330e-4204-a35a-799ed6f33472');
INSERT INTO `role_permission` VALUES (49, 'admin-001', '73d2a70e-e447-4f82-8ceb-f4e04d96c1dd', '2019-09-19 11:29:19', 0, 'de4044ca-8c67-468c-8656-3904ab46fb11');
INSERT INTO `role_permission` VALUES (50, 'admin-001', 'e2b7b1ea-3c71-4465-b4da-5519fc07baf9', '2019-09-19 11:29:19', 0, '25603d76-edfc-413f-a680-9d21270f88c7');
INSERT INTO `role_permission` VALUES (51, 'admin-001', 'bf7d3afd-40f6-4411-8ce5-67492e0056da', '2019-09-19 11:29:20', 0, 'be518113-bf91-4fa7-8e8c-0b6af07e1156');
INSERT INTO `role_permission` VALUES (52, 'admin-001', '30d6d6b6-0047-4e1d-8e38-3e386f71ee97', '2019-09-19 11:29:20', 0, 'd27800cd-d3c7-4e7e-ba0f-84e0b6f294ef');
INSERT INTO `role_permission` VALUES (53, 'admin-001', '7ed95063-aba0-4125-81d5-ebdc27afaa20', '2019-09-19 11:29:20', 0, '40fde6f6-f589-4048-928c-e2d11a9b0292');
INSERT INTO `role_permission` VALUES (54, 'zhanzhang-002', 'pms-2-001', '2019-09-22 03:21:30', 1, 'a94744e8-46aa-4560-9cd3-48d5559c5eaa');
INSERT INTO `role_permission` VALUES (55, 'zhanzhang-002', 'pms-1-002', '2019-09-22 03:21:36', 0, 'f90e8f06-1468-4f15-aed4-6d05982863dc');
INSERT INTO `role_permission` VALUES (56, 'zhanzhang-002', 'pms-3-003', '2019-09-22 03:21:39', 0, '78592691-9faf-49ed-8dd1-0e3be550b7fa');
INSERT INTO `role_permission` VALUES (57, 'zhanzhang-002', 'pms-3-011', '2019-09-22 03:21:44', 0, '96b805b9-158c-4e5f-bf5a-57ab6765144e');
INSERT INTO `role_permission` VALUES (58, 'zhanzhang-002', 'pms-3-014', '2019-09-22 03:21:46', 0, '1c391582-f550-4565-9779-1d3c3ba0dda6');
INSERT INTO `user` VALUES (1, 'admin', '123456', '2018-06-19 11:57:33', '2018-06-19 11:57:33', 0, 0, '217be8ba-1eec-44ac-a337-ae42e7e54f90');
INSERT INTO `user` VALUES (2, 'user', '123456', '2018-06-19 11:57:33', '2018-06-19 11:57:33', 0, 0, '970231ce-7a66-413f-8c6c-6c8e0d4bd96b');
INSERT INTO `user_role` VALUES (1, '217be8ba-1eec-44ac-a337-ae42e7e54f90', 'admin-001', 'ur-001', '2018-06-22 15:39:45', 0);
INSERT INTO `user_role` VALUES (2, '970231ce-7a66-413f-8c6c-6c8e0d4bd96b', 'zhanzhang-002', 'ur-002', '2018-06-22 15:39:45', 0);
INSERT INTO `user_role` VALUES (3, '87e673d6-d5e6-4538-8faf-673032427dae', 'admin-001', '45e1ceea-7b5a-4597-9603-2430600de49d', '2019-09-11 04:26:41', 0);
INSERT INTO `user_role` VALUES (4, '5022f542-a5b7-489c-855f-b2cc18def7e8', 'admin-001', '9e72547a-51c7-4e5c-b2e5-593f169188aa', '2019-09-11 04:41:14', 0);
INSERT INTO `user_role` VALUES (5, 'be730d12-b89b-48b2-a6f7-0b1f6df0e23e', 'admin-001', 'a8e851f6-d8e3-4faf-b5f5-be02e681b32c', '2019-09-11 05:51:02', 0);
