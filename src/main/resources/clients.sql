CREATE TABLE `client` (
    `id` CHAR(36) NOT NULL,
    `name` VARCHAR(255) NOT NULL,
    `address` VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
    );
COMMIT;