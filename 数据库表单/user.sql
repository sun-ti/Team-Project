/*
Navicat MySQL Data Transfer

Source Server         : mydb
Source Server Version : 50138
Source Host           : localhost:3306
Source Database       : sinopec

Target Server Type    : MYSQL
Target Server Version : 50138
File Encoding         : 65001

Date: 2019-10-10 05:00:25
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `USER_AutoID` int(11) NOT NULL AUTO_INCREMENT,
  `USERNAME` varchar(16) DEFAULT NULL,
  `PASSWORD` varchar(16) DEFAULT NULL,
  `USER_CREATE_TIME` datetime DEFAULT NULL,
  `USER_REFRESH_TIME` datetime DEFAULT NULL COMMENT '同步时间',
  `USER_REFRESH_MARK` tinyint(4) DEFAULT '0' COMMENT '同步标志（0未同步，1已同步）',
  `USER_DEL` tinyint(4) DEFAULT '0' COMMENT '删除标志（0代表不删除，1代表删除）',
  `USER_UUID` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`USER_AutoID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', '123456', '2018-06-19 11:57:33', '2018-06-19 11:57:33', '0', '0', '217be8ba-1eec-44ac-a337-ae42e7e54f90');
INSERT INTO `user` VALUES ('2', 'user', '123456', '2018-06-19 11:57:33', '2018-06-19 11:57:33', '0', '0', '970231ce-7a66-413f-8c6c-6c8e0d4bd96b');
