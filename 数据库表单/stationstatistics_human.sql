/*
Navicat MySQL Data Transfer

Source Server         : mydb
Source Server Version : 50138
Source Host           : localhost:3306
Source Database       : sinopec

Target Server Type    : MYSQL
Target Server Version : 50138
File Encoding         : 65001

Date: 2019-10-08 17:36:39
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `stationstatistics_human`
-- ----------------------------
DROP TABLE IF EXISTS `stationstatistics_human`;
CREATE TABLE `stationstatistics_human` (
  `autoid` bigint(20) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(50) DEFAULT NULL,
  `stationid` varchar(20) DEFAULT NULL,
  `in_human_num` bigint(20) DEFAULT NULL,
  `datetime` varchar(20) DEFAULT NULL,
  `datetime1` bigint(20) DEFAULT NULL,
  `year` int(11) DEFAULT NULL,
  `month` int(11) DEFAULT NULL,
  `day` int(11) DEFAULT NULL,
  PRIMARY KEY (`autoid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of stationstatistics_human
-- ----------------------------
INSERT INTO `stationstatistics_human` VALUES ('1', '1', 'jyxd', '160', '2019-10-03', '2', '2019', '10', '3');
INSERT INTO `stationstatistics_human` VALUES ('2', '2', 'jyxd', '20', '2019-10-04', '3', '2019', '10', '4');
INSERT INTO `stationstatistics_human` VALUES ('3', '3', 'jyxd', '180', '2019-08-02', '1', '2019', '8', '2');
