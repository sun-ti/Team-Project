/*
Navicat MySQL Data Transfer

Source Server         : mydb
Source Server Version : 50138
Source Host           : localhost:3306
Source Database       : sinopec

Target Server Type    : MYSQL
Target Server Version : 50138
File Encoding         : 65001

Date: 2019-09-25 01:27:05
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `station`
-- ----------------------------
DROP TABLE IF EXISTS `station`;
CREATE TABLE `station` (
  `autoid` bigint(20) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(50) DEFAULT NULL,
  `stationid` varchar(10) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `datetime` varchar(50) DEFAULT NULL,
  `datetime1` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`autoid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of station
-- ----------------------------
INSERT INTO `station` VALUES ('1', '165c5930-e846-4505-a436-03c243b3c72f', 'jyxd', 'jieyuanxidao', '0', '2019-09-24 11:04:14', '1569294254395');
INSERT INTO `station` VALUES ('2', 'acb1942d-0ae0-4bbe-a698-8183eabc50a8', 'jyxd', '芥园西道', '0', '2019-09-24 11:15:28', '1569294928121');
INSERT INTO `station` VALUES ('3', '4345ecbb-5707-44a5-b703-150abd847a66', 'jyxd', 'null', '0', '2019-09-24 13:57:26', '1569304646561');
INSERT INTO `station` VALUES ('4', '3b2f9665-54f8-4df5-a633-7a801865a166', 'jyxd', 'null', '0', '2019-09-24 13:59:17', '1569304757223');
INSERT INTO `station` VALUES ('5', '81edc555-5d24-4f07-8008-21b3a54e0cf5', 'jyxd', 'null', '0', '2019-09-24 13:59:55', '1569304795911');
