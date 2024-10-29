CREATE TABLE `clients` (
                           `nif` varchar(255) NOT NULL,
                           `name` varchar(255) DEFAULT NULL,
                           `address` varchar(255) DEFAULT NULL,
                           PRIMARY KEY (`nif`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

