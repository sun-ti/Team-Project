/*
Navicat MySQL Data Transfer

Source Server         : mydb
Source Server Version : 50138
Source Host           : localhost:3306
Source Database       : sinopec

Target Server Type    : MYSQL
Target Server Version : 50138
File Encoding         : 65001

Date: 2019-10-08 17:36:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `stationstatistics_exception`
-- ----------------------------
DROP TABLE IF EXISTS `stationstatistics_exception`;
CREATE TABLE `stationstatistics_exception` (
  `autoid` bigint(20) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(50) DEFAULT NULL,
  `carid` varchar(20) DEFAULT NULL,
  `stationid` varchar(20) DEFAULT NULL,
  `datetime` varchar(20) DEFAULT NULL,
  `datetime1` bigint(20) DEFAULT NULL,
  `year` int(11) DEFAULT NULL,
  `month` int(11) DEFAULT NULL,
  `day` int(11) DEFAULT NULL,
  PRIMARY KEY (`autoid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of stationstatistics_exception
-- ----------------------------
INSERT INTO `stationstatistics_exception` VALUES ('1', '1', '津A1355', 'jyxd', '2019-10-04 00:00:00', '1', '2019', '10', '4');
INSERT INTO `stationstatistics_exception` VALUES ('2', '2', '津A8315', 'jyxd', '2019-10-05 00:00:00', '2', '2019', '10', '5');
INSERT INTO `stationstatistics_exception` VALUES ('3', '3', '津A1268', 'jyxd', '2019-10-05 14:00:00', '3', '2019', '10', '5');
