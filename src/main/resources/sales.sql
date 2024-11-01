CREATE TABLE `sales` (
                             `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
                             `client_id` INT NOT NULL,
                             `sell_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                             PRIMARY KEY (`id`),
                             FOREIGN KEY (`client_id`) REFERENCES `clients` (`id`)
);