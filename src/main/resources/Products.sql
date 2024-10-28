CREATE TABLE `clients`
(
    `code`      int(10) unsigned NOT NULl,
    `name`   varchar(250) NOT NULL DEFAULT '',
    PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
commit;