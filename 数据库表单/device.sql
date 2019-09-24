/*
Navicat MySQL Data Transfer

Source Server         : mydb
Source Server Version : 50138
Source Host           : localhost:3306
Source Database       : sinopec

Target Server Type    : MYSQL
Target Server Version : 50138
File Encoding         : 65001

Date: 2019-09-25 01:26:30
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `device`
-- ----------------------------
DROP TABLE IF EXISTS `device`;
CREATE TABLE `device` (
  `autoid` bigint(20) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `deviceip` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `stationid` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `kind` int(11) DEFAULT NULL,
  `note` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  PRIMARY KEY (`autoid`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of device
-- ----------------------------
INSERT INTO `device` VALUES ('1', '3160d0b8-acbf-4c8d-a428-bb51945e0020', '172.30.9.14', 'jyxd', '1', '入口监控', '0');
INSERT INTO `device` VALUES ('2', '9a841a03-abdf-483c-89d0-3c66eed92735', '172.30.9.15', 'jyxd', '1', '出口监控', '0');
INSERT INTO `device` VALUES ('3', '576040fd-9a50-42f2-96e7-a4cea3d092be', '172.30.9.16', 'jyxd', '1', '车道1监控', '0');
INSERT INTO `device` VALUES ('4', '2bbcbb7f-574b-427c-b204-1c6302155bcc', '172.30.9.17', 'jyxd', '1', '车道2监控', '0');
INSERT INTO `device` VALUES ('5', 'be8c8177-d940-4c68-8d43-702245040f18', '172.30.9.18', 'jyxd', '1', '车道3监控', '0');
INSERT INTO `device` VALUES ('6', '787118cc-247e-4bb7-a5bf-a9fb05889b82', '172.30.9.13', 'jyxd', '5', '车流统计', '0');
INSERT INTO `device` VALUES ('7', 'a4de8131-f632-45df-b438-1ad1863a0316', '172.30.9.11', 'jyxd', '2', '客流统计', '0');
INSERT INTO `device` VALUES ('8', '890c148c-066c-4565-9868-969317ec6ff3', '172.30.9.12', 'jyxd', '3', '人脸识别', '0');
INSERT INTO `device` VALUES ('9', '37c4e959-b256-4ad6-8f70-c3e2e9247950', '172.30.9.21', 'jyxd', '4', '西侧卸油区', '0');
INSERT INTO `device` VALUES ('10', '38ee1e8a-9d95-46e9-9957-4c47485b13cf', '172.30.9.12', 'jyxd', '4', '东侧卸油区', '0');
