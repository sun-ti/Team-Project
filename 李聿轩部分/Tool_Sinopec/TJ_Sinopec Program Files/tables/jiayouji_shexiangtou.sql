CREATE TABLE `jiayouji_shexiangtou` (
  `autoid` bigint(20) NOT NULL AUTO_INCREMENT,
  `jiayouji_id` bigint(20) DEFAULT NULL,
  `shexiangtou_id` bigint(20) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  PRIMARY KEY (`autoid`)
)
