/*
Navicat MySQL Data Transfer

Source Server         : mydb
Source Server Version : 50138
Source Host           : localhost:3306
Source Database       : sinopec

Target Server Type    : MYSQL
Target Server Version : 50138
File Encoding         : 65001

Date: 2019-10-10 04:58:53
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
) ENGINE=InnoDB AUTO_INCREMENT=117 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of car_plan
-- ----------------------------
INSERT INTO `car_plan` VALUES ('1', 'jyxd', '津CA1859');
INSERT INTO `car_plan` VALUES ('2', 'jyxd', '津CA1859');
INSERT INTO `car_plan` VALUES ('3', 'jyxd', '津AN8966');
INSERT INTO `car_plan` VALUES ('4', 'jyxd', '津AN8966');
INSERT INTO `car_plan` VALUES ('5', 'jyxd', '津AN8959');
INSERT INTO `car_plan` VALUES ('6', 'jyxd', '津AN8959');
INSERT INTO `car_plan` VALUES ('7', 'jyxd', '津CA3767');
INSERT INTO `car_plan` VALUES ('8', 'jyxd', '津CA3767');
INSERT INTO `car_plan` VALUES ('9', 'jyxd', '津AN8906');
INSERT INTO `car_plan` VALUES ('10', 'jyxd', '津CA3767');
INSERT INTO `car_plan` VALUES ('11', 'jyxd', '津CA3767');
INSERT INTO `car_plan` VALUES ('12', 'jyxd', '津AN8906');
INSERT INTO `car_plan` VALUES ('13', 'jyxd', '津CA5073');
INSERT INTO `car_plan` VALUES ('14', 'jyxd', '津AL5526');
INSERT INTO `car_plan` VALUES ('15', 'jyxd', '津AL5526');
INSERT INTO `car_plan` VALUES ('16', 'jyxd', '津CA5073');
INSERT INTO `car_plan` VALUES ('17', 'jyxd', '津CA5073');
INSERT INTO `car_plan` VALUES ('18', 'jyxd', '津CA5073');
INSERT INTO `car_plan` VALUES ('19', 'jyxd', '津CA1859');
INSERT INTO `car_plan` VALUES ('20', 'jyxd', '津CA1859');
INSERT INTO `car_plan` VALUES ('21', 'jyxd', '津AN8960');
INSERT INTO `car_plan` VALUES ('22', 'jyxd', '津CA1859');
INSERT INTO `car_plan` VALUES ('23', 'jyxd', '津CA5073');
INSERT INTO `car_plan` VALUES ('24', 'jyxd', '津AN8713');
INSERT INTO `car_plan` VALUES ('25', 'jyxd', '津AN8713');
INSERT INTO `car_plan` VALUES ('26', 'jyxd', '津CA3767');
INSERT INTO `car_plan` VALUES ('27', 'jyxd', '津CA3767');
INSERT INTO `car_plan` VALUES ('28', 'jyxd', '津AN8713');
INSERT INTO `car_plan` VALUES ('29', 'jyxd', '津AN8713');
INSERT INTO `car_plan` VALUES ('30', 'jyxd', '津AN8713');
INSERT INTO `car_plan` VALUES ('31', 'jyxd', '津CA1859');
INSERT INTO `car_plan` VALUES ('32', 'jyxd', '津CA1859');
INSERT INTO `car_plan` VALUES ('33', 'jyxd', '津CA1859');
INSERT INTO `car_plan` VALUES ('34', 'jyxd', '津CA5763');
INSERT INTO `car_plan` VALUES ('35', 'jyxd', '津AN8960');
INSERT INTO `car_plan` VALUES ('36', 'jyxd', '津AN8960');
INSERT INTO `car_plan` VALUES ('37', 'jyxd', '津AN8960');
INSERT INTO `car_plan` VALUES ('38', 'jyxd', '津AN8960');
INSERT INTO `car_plan` VALUES ('39', 'jyxd', '津CA5763');
INSERT INTO `car_plan` VALUES ('40', 'jyxd', '津AN8713');
INSERT INTO `car_plan` VALUES ('41', 'jyxd', '津AN8713');
INSERT INTO `car_plan` VALUES ('42', 'jyxd', '津AN8713');
INSERT INTO `car_plan` VALUES ('43', 'jyxd', '津AN8713');
INSERT INTO `car_plan` VALUES ('44', 'jyxd', '津CA3767');
INSERT INTO `car_plan` VALUES ('45', 'jyxd', '津CA3767');
INSERT INTO `car_plan` VALUES ('46', 'jyxd', '津CA3767');
INSERT INTO `car_plan` VALUES ('47', 'jyxd', '津AN8959');
INSERT INTO `car_plan` VALUES ('48', 'jyxd', '津AN8959');
INSERT INTO `car_plan` VALUES ('49', 'jyxd', '津AN8713');
INSERT INTO `car_plan` VALUES ('50', 'jyxd', '津AN8713');
INSERT INTO `car_plan` VALUES ('51', 'jyxd', '津CA1859');
INSERT INTO `car_plan` VALUES ('52', 'jyxd', '津CA5073');
INSERT INTO `car_plan` VALUES ('53', 'jyxd', '津CA5073');
INSERT INTO `car_plan` VALUES ('54', 'jyxd', '津AN8966');
INSERT INTO `car_plan` VALUES ('55', 'jyxd', '津AN8959');
INSERT INTO `car_plan` VALUES ('56', 'jyxd', '津AN8959');
INSERT INTO `car_plan` VALUES ('57', 'jyxd', '津AN8959');
INSERT INTO `car_plan` VALUES ('58', 'jyxd', '津CA5073');
INSERT INTO `car_plan` VALUES ('59', 'jyxd', '津CA5073');
INSERT INTO `car_plan` VALUES ('60', 'jyxd', '津CA5073');
INSERT INTO `car_plan` VALUES ('61', 'jyxd', '津CA5073');
INSERT INTO `car_plan` VALUES ('62', 'jyxd', '津CA1859');
INSERT INTO `car_plan` VALUES ('63', 'jyxd', '津CA1859');
INSERT INTO `car_plan` VALUES ('64', 'jyxd', '津CA1859');
INSERT INTO `car_plan` VALUES ('65', 'jyxd', '津CA1859');
INSERT INTO `car_plan` VALUES ('66', 'jyxd', '津CA3767');
INSERT INTO `car_plan` VALUES ('67', 'jyxd', '津CA3767');
INSERT INTO `car_plan` VALUES ('68', 'jyxd', '津CA3767');
INSERT INTO `car_plan` VALUES ('69', 'jyxd', '津CA3767');
INSERT INTO `car_plan` VALUES ('70', 'jyxd', '津CA5763');
INSERT INTO `car_plan` VALUES ('71', 'jyxd', '津CA5073');
INSERT INTO `car_plan` VALUES ('72', 'jyxd', '津CA5763');
INSERT INTO `car_plan` VALUES ('73', 'jyxd', '津CA5073');
INSERT INTO `car_plan` VALUES ('74', 'jyxd', '津AN8713');
INSERT INTO `car_plan` VALUES ('75', 'jyxd', '津CA1859');
INSERT INTO `car_plan` VALUES ('76', 'jyxd', '津CA1859');
INSERT INTO `car_plan` VALUES ('77', 'jyxd', '津AN8959');
INSERT INTO `car_plan` VALUES ('78', 'jyxd', '津AN8959');
INSERT INTO `car_plan` VALUES ('79', 'jyxd', '津CA1859');
INSERT INTO `car_plan` VALUES ('80', 'jyxd', '津CA1859');
INSERT INTO `car_plan` VALUES ('81', 'jyxd', '津AL5526');
INSERT INTO `car_plan` VALUES ('82', 'jyxd', '津CA5073');
INSERT INTO `car_plan` VALUES ('83', 'jyxd', '津CA1859');
INSERT INTO `car_plan` VALUES ('84', 'jyxd', '津CA1859');
INSERT INTO `car_plan` VALUES ('85', 'jyxd', '津CA1859');
INSERT INTO `car_plan` VALUES ('86', 'jyxd', '津CA1859');
INSERT INTO `car_plan` VALUES ('87', 'jyxd', '津CA1859');
INSERT INTO `car_plan` VALUES ('88', 'jyxd', '津CA1859');
INSERT INTO `car_plan` VALUES ('89', 'jyxd', '津CA1859');
INSERT INTO `car_plan` VALUES ('90', 'jyxd', '津CA1859');
INSERT INTO `car_plan` VALUES ('91', 'jyxd', '津AN8966');
INSERT INTO `car_plan` VALUES ('92', 'jyxd', '津AN8966');
INSERT INTO `car_plan` VALUES ('93', 'jyxd', '津CA5073');
INSERT INTO `car_plan` VALUES ('94', 'jyxd', '津CA3767');
INSERT INTO `car_plan` VALUES ('95', 'jyxd', '津CA3767');
INSERT INTO `car_plan` VALUES ('96', 'jyxd', '津CA5073');
INSERT INTO `car_plan` VALUES ('97', 'jyxd', '津AL5526');
INSERT INTO `car_plan` VALUES ('98', 'jyxd', '津AL5526');
INSERT INTO `car_plan` VALUES ('99', 'jyxd', '津AL5526');
INSERT INTO `car_plan` VALUES ('100', 'jyxd', '津CA5719');
INSERT INTO `car_plan` VALUES ('101', 'jyxd', '津CA5719');
INSERT INTO `car_plan` VALUES ('102', 'jyxd', '津AN8713');
INSERT INTO `car_plan` VALUES ('103', 'jyxd', '津AN8713');
INSERT INTO `car_plan` VALUES ('104', 'jyxd', '津AN8713');
INSERT INTO `car_plan` VALUES ('105', 'jyxd', '津AN8713');
INSERT INTO `car_plan` VALUES ('106', 'jyxd', '津AN8966');
INSERT INTO `car_plan` VALUES ('107', 'jyxd', '津AN8966');
INSERT INTO `car_plan` VALUES ('108', 'jyxd', '津AN8966');
INSERT INTO `car_plan` VALUES ('109', 'jyxd', '津AN8966');
INSERT INTO `car_plan` VALUES ('110', 'jyxd', '津CA5073');
INSERT INTO `car_plan` VALUES ('111', 'jyxd', '津AN8953');
INSERT INTO `car_plan` VALUES ('112', 'jyxd', '津CA1859');
INSERT INTO `car_plan` VALUES ('113', 'jyxd', '津CA1859');
INSERT INTO `car_plan` VALUES ('114', 'jyxd', '津CA1859');
INSERT INTO `car_plan` VALUES ('115', 'jyxd', '津CA1859');
INSERT INTO `car_plan` VALUES ('116', 'jyxd', '津CA1859');
