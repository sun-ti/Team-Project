/*
Navicat MySQL Data Transfer

Source Server         : mydb
Source Server Version : 50138
Source Host           : localhost:3306
Source Database       : sinopec

Target Server Type    : MYSQL
Target Server Version : 50138
File Encoding         : 65001

Date: 2019-10-08 17:35:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `jyj`
-- ----------------------------
DROP TABLE IF EXISTS `jyj`;
CREATE TABLE `jyj` (
  `autoid` bigint(20) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(50) DEFAULT NULL,
  `orderNum` varchar(50) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `quantity` double DEFAULT NULL,
  `money` double DEFAULT NULL,
  `payType` int(11) DEFAULT NULL,
  `cardNum` varchar(20) DEFAULT NULL,
  `oilGunNum` varchar(20) DEFAULT NULL,
  `startTime` varchar(20) DEFAULT NULL,
  `endTime` varchar(20) DEFAULT NULL,
  `plateOld` varchar(20) DEFAULT NULL,
  `plate` varchar(20) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  PRIMARY KEY (`autoid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jyj
-- ----------------------------
