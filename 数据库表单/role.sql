/*
Navicat MySQL Data Transfer

Source Server         : mydb
Source Server Version : 50138
Source Host           : localhost:3306
Source Database       : sinopec

Target Server Type    : MYSQL
Target Server Version : 50138
File Encoding         : 65001

Date: 2019-10-10 04:59:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `ROLE_AutoID` int(11) NOT NULL AUTO_INCREMENT,
  `ROLE_NAME` varchar(25) DEFAULT NULL,
  `ROLE_UUID` varchar(40) DEFAULT NULL,
  `ROLE_DEL` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表不删除，1代表删除）',
  `ROLE_CREATE_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ROLE_AutoID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '1', 'admin-001', '1', '2017-10-02 11:00:00');
INSERT INTO `role` VALUES ('2', '2', 'admin-002', '1', '2017-10-02 11:00:00');
