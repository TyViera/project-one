CREATE TABLE `clients`
(
    `id`      int(10) unsigned NOT NULl,
    `client_nif`   varchar unsigned NOT NULL,
    `product_code` varchar unsigned NOT NULL,
    `quantity`   int(10) NOT NULL DEFAULT '',
    PRIMARY KEY (`id`),
    FOREIGN KEY (`client_nif`) REFERENCES `clients` (`nif`),
    FOREIGN KEY (`product_code`) REFERENCES `users` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
commit;