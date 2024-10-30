CREATE TABLE `sales_det` (
         `sale_id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
         `product_code` INT NOT NULL,
         `quantity` INT NOT NULL,
         PRIMARY KEY (`sale_id`, `product_code`),
         FOREIGN KEY (`sale_id`) REFERENCES `sales` (`id`) ON DELETE CASCADE,
         FOREIGN KEY (`product_code`) REFERENCES `products` (`id`) ON DELETE RESTRICT
);