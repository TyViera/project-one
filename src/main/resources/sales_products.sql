CREATE TABLE sales_products (
    sale_id int(11) unsigned NOT NULL,
    product_id int(11) unsigned NOT NULL,
    amount smallint unsigned NOT NULL,

    PRIMARY KEY (sale_id, product_id),
    FOREIGN KEY (sale_id) REFERENCES sales(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

COMMIT;

INSERT INTO sales_products (sale_id, product_id, amount) VALUE (1, '1', 2);
INSERT INTO sales_products (sale_id, product_id, amount) VALUE (1, '2', 3);

SELECT * FROM sales_products;
