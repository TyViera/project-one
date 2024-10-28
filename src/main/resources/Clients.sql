CREATE TABLE `clients`
(
    `nif`      varchar(10) unsigned NOT NULl,
    `name`   varchar(250) NOT NULL DEFAULT '',
    `address` varchar(250) NOT NULL DEFAULT '',
    PRIMARY KEY (`nif`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
commit;