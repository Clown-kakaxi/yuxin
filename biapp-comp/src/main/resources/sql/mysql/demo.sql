drop table CUST;

CREATE TABLE `cust` (
  `id` double NOT NULL auto_increment,
  `ADDRESS` varchar(300) default NULL,
  `BALANCE` double default NULL,
  `BIRTH` date default NULL,
  `CARDID` varchar(60) default NULL,
  `CUSTNAME` varchar(30) default NULL,
  `PASSWORD` varchar(150) default NULL,
  `PHONE` varchar(150) default NULL,
  `SEX` varchar(3) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


