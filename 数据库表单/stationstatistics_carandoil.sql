/*
Navicat MySQL Data Transfer

Source Server         : mydb
Source Server Version : 50138
Source Host           : localhost:3306
Source Database       : sinopec

Target Server Type    : MYSQL
Target Server Version : 50138
File Encoding         : 65001

Date: 2019-10-08 17:36:28
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `stationstatistics_carandoil`
-- ----------------------------
DROP TABLE IF EXISTS `stationstatistics_carandoil`;
CREATE TABLE `stationstatistics_carandoil` (
  `autoid` bigint(20) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(50) DEFAULT NULL,
  `stationid` varchar(20) DEFAULT NULL,
  `out_num` bigint(20) DEFAULT NULL,
  `in_num` bigint(20) DEFAULT NULL,
  `in_oil_quantity` double DEFAULT NULL,
  `in_oil_count` int(11) DEFAULT NULL,
  `datetime` varchar(20) DEFAULT NULL,
  `datetime1` bigint(20) DEFAULT NULL,
  `year` int(11) DEFAULT NULL,
  `month` int(11) DEFAULT NULL,
  `day` int(11) DEFAULT NULL,
  PRIMARY KEY (`autoid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of stationstatistics_carandoil
-- ----------------------------
INSERT INTO `stationstatistics_carandoil` VALUES ('1', '1', 'jyxd', '12', '8', '80', '10', '2019-10-03', '2', '2019', '10', '3');
INSERT INTO `stationstatistics_carandoil` VALUES ('2', '2', 'jyxd', '20', '9', '90', '10', '1', '3', '2019', '10', '4');
INSERT INTO `stationstatistics_carandoil` VALUES ('3', '3', 'jyxd', '12', '10', '90', '90', '2', '1', '2019', '8', '2');
