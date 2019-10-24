/*
Navicat MySQL Data Transfer

Source Server         : mydb
Source Server Version : 50138
Source Host           : localhost:3306
Source Database       : sinopec

Target Server Type    : MYSQL
Target Server Version : 50138
File Encoding         : 65001

Date: 2019-10-22 23:10:53
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `user_role`
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `UR_AutoID` int(11) NOT NULL AUTO_INCREMENT,
  `USER_UUID` varchar(40) DEFAULT NULL,
  `ROLE_UUID` varchar(40) DEFAULT NULL,
  `UR_UUID` varchar(40) DEFAULT NULL,
  `UR_CREATE_TIME` datetime DEFAULT NULL,
  `UR_DEL` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`UR_AutoID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', '217be8ba-1eec-44ac-a337-ae42e7e54f90', 'admin-001', 'ur-001', '2018-06-22 15:39:45', '0');
INSERT INTO `user_role` VALUES ('2', '970231ce-7a66-413f-8c6c-6c8e0d4bd96b', 'zhanzhang-002', 'ur-002', '2018-06-22 15:39:45', '0');
INSERT INTO `user_role` VALUES ('3', '87e673d6-d5e6-4538-8faf-673032427dae', 'admin-001', '45e1ceea-7b5a-4597-9603-2430600de49d', '2019-09-11 04:26:41', '0');
INSERT INTO `user_role` VALUES ('4', '5022f542-a5b7-489c-855f-b2cc18def7e8', 'admin-001', '9e72547a-51c7-4e5c-b2e5-593f169188aa', '2019-09-11 04:41:14', '0');
INSERT INTO `user_role` VALUES ('5', 'be730d12-b89b-48b2-a6f7-0b1f6df0e23e', 'admin-001', 'a8e851f6-d8e3-4faf-b5f5-be02e681b32c', '2019-09-11 05:51:02', '1');
INSERT INTO `user_role` VALUES ('6', '931a20a9-52b4-429e-938c-95a5a00e33a5', 'admin-001', '62573a88-e302-4456-97a0-a7443a8af754', '2019-10-17 21:28:22', '0');
