CREATE TABLE sales (
    id int(11) unsigned NOT NULL AUTO_INCREMENT,
    client_nif varchar(9) NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (client_nif) REFERENCES clients(nif)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

COMMIT;

INSERT INTO sales (id, client_nif) VALUE (1, '1');

SELECT * FROM sales;
