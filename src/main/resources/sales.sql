CREATE TABLE `sales`
(
    id int(11) unsigned CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL AUTO_INCREMENT,
    code_client int(11) unsigned CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    sell_date timestamp not null default current_timestamp,
    foreign key (code_client) references clients(id),
    primary key (code_sale)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
commit;

--select * from sales;