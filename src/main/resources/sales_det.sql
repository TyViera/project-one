CREATE TABLE `sales_det` (
                             `sale_id` INT(11) UNSIGNED NOT NULL,
                             `product_code` int NOT NULL,
                             `quantity` int NOT NULL,
                             PRIMARY KEY (`sale_id`, `product_code`),
                             FOREIGN KEY (`sale_id`) REFERENCES `sales_cab` (`id`) ON DELETE CASCADE,
                             FOREIGN KEY (`product_code`) REFERENCES `products` (`code`) ON DELETE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
