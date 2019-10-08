/*
Navicat MySQL Data Transfer

Source Server         : mydb
Source Server Version : 50138
Source Host           : localhost:3306
Source Database       : sinopec

Target Server Type    : MYSQL
Target Server Version : 50138
File Encoding         : 65001

Date: 2019-10-08 17:35:13
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `car_addoil`
-- ----------------------------
DROP TABLE IF EXISTS `car_addoil`;
CREATE TABLE `car_addoil` (
  `autoid` bigint(20) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(50) DEFAULT NULL,
  `carid` varchar(20) DEFAULT NULL,
  `stationid` varchar(20) DEFAULT NULL,
  `in_oil_quantity` double DEFAULT NULL,
  `in_oil_count` int(11) DEFAULT NULL,
  `datetime` varchar(20) DEFAULT NULL,
  `datetime1` bigint(20) DEFAULT NULL,
  `staytime` bigint(20) DEFAULT NULL,
  `year` int(11) DEFAULT NULL,
  `month` int(11) DEFAULT NULL,
  `day` int(11) DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`autoid`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of car_addoil
-- ----------------------------
INSERT INTO `car_addoil` VALUES ('1', '1', '津A1140', 'jyxd', '10', '10', '1', '3', '1', '2019', '10', '1', '1');
INSERT INTO `car_addoil` VALUES ('2', '2', '津B1140', 'jyxd', '10', '10', '2', '4', '1', '2019', '10', '1', '1');
INSERT INTO `car_addoil` VALUES ('3', '3', '津C1140', 'jyxd', '20', '10', '3', '5', '1', '2019', '10', '1', '2');
INSERT INTO `car_addoil` VALUES ('4', '4', '津A1140', 'jyxd', '10', '1', '4', '6', '1', '2019', '10', '2', '2');
INSERT INTO `car_addoil` VALUES ('5', '5', '津E2130', 'jyxd', '10', '10', '5', '7', '1', '2019', '10', '3', '1');
INSERT INTO `car_addoil` VALUES ('6', '6', '津A2103', 'jyxd', '30', '1', '6', '1', '1', '2019', '1', '4', '2');
INSERT INTO `car_addoil` VALUES ('7', '7', '津B2210', 'jyxd', '5', '1', '7', '2', '2', '2019', '2', '5', '2');
