CREATE TABLE `clients` (
    `nif` varchar(9) NOT NULL,
    `name` varchar(20) NOT NULL DEFAULT '',
    `address` varchar(40) NOT NULL DEFAULT '',
    PRIMARY KEY (`nif`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO clients VALUES ('1', 'Carlos', 'Barcelona');
INSERT INTO clients VALUES ('2', 'Joan', 'Menorca');

COMMIT;
