/*
Navicat MySQL Data Transfer

Source Server         : mydb
Source Server Version : 50138
Source Host           : localhost:3306
Source Database       : sinopec

Target Server Type    : MYSQL
Target Server Version : 50138
File Encoding         : 65001

Date: 2019-09-25 01:27:17
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
