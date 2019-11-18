DROP TABLE IF EXISTS `gongsi_quyu`;
CREATE TABLE `gongsi_quyu` (
  `autoid` bigint(20) NOT NULL AUTO_INCREMENT,
  `gongsi_id` varchar(20) DEFAULT NULL,
  `quyu_id` varchar(20) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  PRIMARY KEY (`autoid`)
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8;