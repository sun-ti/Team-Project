/*
Navicat MySQL Data Transfer

Source Server         : mydb
Source Server Version : 50138
Source Host           : localhost:3306
Source Database       : sinopec

Target Server Type    : MYSQL
Target Server Version : 50138
File Encoding         : 65001

Date: 2019-10-08 17:35:28
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `car_removeoil`
-- ----------------------------
DROP TABLE IF EXISTS `car_removeoil`;
CREATE TABLE `car_removeoil` (
  `autoid` bigint(20) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(20) DEFAULT NULL,
  `psd_id` varchar(20) DEFAULT NULL,
  `stationid` varchar(20) DEFAULT NULL,
  `cp_no` varchar(20) DEFAULT NULL COMMENT '理论车牌',
  `car_id` varchar(20) DEFAULT NULL COMMENT '实际车牌',
  `datetime` varchar(20) DEFAULT NULL COMMENT '送达时间',
  `datetime1` bigint(20) DEFAULT NULL,
  `psd_time` varchar(20) DEFAULT NULL COMMENT '配送时间',
  `state` int(11) DEFAULT NULL COMMENT '修改状态',
  `year` int(11) DEFAULT NULL,
  `month` int(11) DEFAULT NULL,
  `day` int(11) DEFAULT NULL,
  PRIMARY KEY (`autoid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of car_removeoil
-- ----------------------------
INSERT INTO `car_removeoil` VALUES ('1', '1', '12368', 'jyxd', '津A1268', '津A1268', '2019-10-04 16:00:00', '1', '2019-10-04 00:00:00', '0', '2019', '10', '4');
INSERT INTO `car_removeoil` VALUES ('2', '2', '12369', 'jyxd', '津A1269', '津B2618', '2019-10-05 17:00:00', '2', '2019-10-04 00:00:00', '0', '2019', '10', '5');
