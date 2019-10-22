/*
Navicat MySQL Data Transfer

Source Server         : mydb
Source Server Version : 50138
Source Host           : localhost:3306
Source Database       : sinopec

Target Server Type    : MYSQL
Target Server Version : 50138
File Encoding         : 65001

Date: 2019-10-22 23:08:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `permission`
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `PERMISSION_AutoID` int(11) NOT NULL AUTO_INCREMENT,
  `PERMISSION_NAME` varchar(255) DEFAULT NULL,
  `PERMISSION_URL` varchar(255) DEFAULT NULL,
  `PERMISSION_CREATE_TIME` datetime DEFAULT NULL,
  `PERMISSION_UUID` varchar(40) DEFAULT NULL,
  `PERMISSION_TYPE` tinyint(4) DEFAULT NULL COMMENT '分为三类：1 系统，2 导航，3 菜单(打开某个功能)',
  `PERMISSION_PARENT_UUID` varchar(40) DEFAULT NULL COMMENT '父级权限uuid',
  `PERMISSION_DEL` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`PERMISSION_AutoID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES ('1', '1', '1', '2019-09-28 00:00:00', 'pms-2-008', '1', '1', '1');
INSERT INTO `permission` VALUES ('2', '2', '2', '2019-09-28 01:00:00', 'pms-2-009', '2', '2', '0');
INSERT INTO `permission` VALUES ('3', '3', '3', '2019-09-28 01:00:00', 'pms-1-001', '1', '2', '0');
