/*
Navicat MySQL Data Transfer

Source Server         : mydb
Source Server Version : 50138
Source Host           : localhost:3306
Source Database       : sinopec

Target Server Type    : MYSQL
Target Server Version : 50138
File Encoding         : 65001

Date: 2019-10-08 17:35:20
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `car_plan`
-- ----------------------------
DROP TABLE IF EXISTS `car_plan`;
CREATE TABLE `car_plan` (
  `autoid` bigint(20) NOT NULL AUTO_INCREMENT,
  `stationid` varchar(20) DEFAULT NULL,
  `carid` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`autoid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of car_plan
-- ----------------------------
