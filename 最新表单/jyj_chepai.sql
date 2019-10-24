/*
Navicat MySQL Data Transfer

Source Server         : mydb
Source Server Version : 50138
Source Host           : localhost:3306
Source Database       : sinopec

Target Server Type    : MYSQL
Target Server Version : 50138
File Encoding         : 65001

Date: 2019-10-22 23:08:13
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `jyj_chepai`
-- ----------------------------
DROP TABLE IF EXISTS `jyj_chepai`;
CREATE TABLE `jyj_chepai` (
  `autoid` bigint(20) NOT NULL AUTO_INCREMENT,
  `clientIp` varchar(20) DEFAULT NULL,
  `oilGunNum` int(11) DEFAULT NULL,
  `plates` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`autoid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jyj_chepai
-- ----------------------------
INSERT INTO `jyj_chepai` VALUES ('1', '192.168.1.10', '1', '津A12345');
INSERT INTO `jyj_chepai` VALUES ('2', '192.168.1.10', '1', '津A12346');
INSERT INTO `jyj_chepai` VALUES ('3', '192.168.1.10', '1', '津A12347');
