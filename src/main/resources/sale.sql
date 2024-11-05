CREATE TABLE `sale` (
    `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
    `client_id` CHAR(36) NOT NULL,
    `sale_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`client_id`) REFERENCES `client` (`id`) ON DELETE CASCADE
);
COMMIT;