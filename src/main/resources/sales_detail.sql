CREATE TABLE `sales_details`
(
    code_sale int(11) unsigned CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    code_product int(11) unsigned NOT NULL CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
    quantity int(6),
    foreign key (code_sale) references sales(id),
    foreign key (code_product) references products(id),
    primary key (code_sale, code_product)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
commit;

--select * from sales_details;