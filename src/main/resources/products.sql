CREATE TABLE products (
    id int(11) unsigned NOT NULL AUTO_INCREMENT,
    name varchar(20) NOT NULL DEFAULT '',
    times_sold int DEFAULT 0,

    PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

INSERT INTO products (name, times_sold) VALUES ( 'laptop', DEFAULT);
INSERT INTO products (name, times_sold) VALUES ( 'iphone', DEFAULT);
INSERT INTO products (name, times_sold) VALUES ( 'airpods', DEFAULT);

COMMIT;
