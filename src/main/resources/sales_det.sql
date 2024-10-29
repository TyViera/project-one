CREATE TABLE `sales_cab` (
                             `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
                             `client_nif` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                             `sell_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                             PRIMARY KEY (`id`),
                             FOREIGN KEY (`client_nif`) REFERENCES `clients` (`nif`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
