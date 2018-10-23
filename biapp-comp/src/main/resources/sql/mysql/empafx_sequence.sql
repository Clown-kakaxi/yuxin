
CREATE TABLE  `empafx_sequence` (
  `seqkey` VARCHAR(128) NOT NULL,
  `roolingkey` VARCHAR(64) NOT NULL,
  `startval` DECIMAL(16,0) DEFAULT '1',
  `minval` DECIMAL(16,0) DEFAULT NULL,
  `maxval` DECIMAL(16,0) DEFAULT NULL,
  `incremental` DECIMAL(2,0) DEFAULT NULL,
  `cutoff` DECIMAL(16,0) DEFAULT NULL,
  `cachesize` DECIMAL(3,0) DEFAULT NULL,
  `cyclical` CHAR(1) DEFAULT NULL,
  `cycletime` VARCHAR(2) DEFAULT NULL,
  `createdate` DATETIME DEFAULT NULL ,
  `lasteditdate` DATETIME DEFAULT NULL ,
  PRIMARY KEY  (`seqkey`,`roolingkey`)
);