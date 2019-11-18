CREATE TABLE `guanli_interface` (
  `autoid` bigint(20) NOT NULL AUTO_INCREMENT,
  `interface_id` bigint(20) DEFAULT NULL,
  `interface_fun` varchar(1000) DEFAULT NULL,
  `interface_url` varchar(2000) DEFAULT NULL,
  `datetime` varchar(20) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  PRIMARY KEY (`autoid`)
)