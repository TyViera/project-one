CREATE TABLE `sale_product` (
    `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
    `sale_id` INT(11) UNSIGNED NOT NULL,
    `product_id` INT(11) UNSIGNED NOT NULL,
    `quantity` INT(11) NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`sale_id`) REFERENCES `sale` (`id`) ON DELETE CASCADE,
    FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE
);
COMMIT;