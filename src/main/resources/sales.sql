CREATE TABLE `sales` (
                             `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
                             `client_nif` VARCHAR(20) NOT NULL UNIQUE,
                             `sell_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                             PRIMARY KEY (`id`),
                             FOREIGN KEY (`client_nif`) REFERENCES `clients` (`nif`)
);