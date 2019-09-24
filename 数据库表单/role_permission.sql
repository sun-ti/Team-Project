/*
Navicat MySQL Data Transfer

Source Server         : mydb
Source Server Version : 50138
Source Host           : localhost:3306
Source Database       : sinopec

Target Server Type    : MYSQL
Target Server Version : 50138
File Encoding         : 65001

Date: 2019-09-25 01:27:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `role_permission`
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `RP_AutoID` int(11) NOT NULL AUTO_INCREMENT,
  `ROLE_UUID` varchar(40) DEFAULT NULL,
  `PERMISSION_UUID` varchar(40) DEFAULT NULL,
  `RP_CREATE_TIME` datetime DEFAULT NULL,
  `RP_DEL` tinyint(4) DEFAULT '0',
  `RP_UUID` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`RP_AutoID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
