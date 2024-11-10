-- Eliminación de tablas para evitar errores si ya existen
DROP TABLE IF EXISTS `sales_det`;
DROP TABLE IF EXISTS `sales`;
DROP TABLE IF EXISTS `clients`;
DROP TABLE IF EXISTS `products`;

-- Creación de la tabla clients
CREATE TABLE `clients` (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    nif VARCHAR(20) NOT NULL UNIQUE,
    address VARCHAR(255) NOT NULL
);

-- Creación de la tabla products
CREATE TABLE `products` (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(50) NOT NULL UNIQUE
);

-- Creación de la tabla sales
CREATE TABLE `sales` (
     id INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
     client_id INT NOT NULL,
     sell_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
     PRIMARY KEY (id),
     FOREIGN KEY (client_id) REFERENCES clients(id)
);

-- Creación de la tabla sales_det (sin AUTO_INCREMENT en sale_id)
CREATE TABLE `sales_det` (
     sale_id INT(11) UNSIGNED NOT NULL,
     product_id INT NOT NULL,
     quantity INT NOT NULL,
     PRIMARY KEY (sale_id, product_id),
     FOREIGN KEY (sale_id) REFERENCES sales(id) ON DELETE CASCADE,
     FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE RESTRICT
);

-- Inserciones en la tabla clients
INSERT INTO `clients` (`name`, `nif`, `address`) VALUES
('Carlos Martínez', '12345678A', 'Calle Mayor, 10, Madrid, España'),
('Ana López', '23456789B', 'Avenida de la Constitución, 45, Barcelona, España'),
('José García', '34567890C', 'Calle del Prado, 32, Valencia, España'),
('María Sánchez', '45678901D', 'Gran Vía, 15, Sevilla, España'),
('Luis Fernández', '56789012E', 'Paseo de Gracia, 18, Barcelona, España'),
('Laura Rodríguez', '67890123F', 'Calle de la Paz, 10, Zaragoza, España'),
('David Moreno', '78901234G', 'Avenida de la Libertad, 20, Bilbao, España'),
('Elena Ruiz', '89012345H', 'Calle Real, 25, Málaga, España'),
('Pablo Gómez', '90123456I', 'Paseo de la Castellana, 100, Madrid, España'),
('Sandra Torres', '01234567J', 'Calle Nueva, 8, Murcia, España');

-- Inserciones en la tabla products
INSERT INTO `products` (`name`, `code`) VALUES
('Laptop Pro', 'LP1001'),
('Smartphone X', 'SP2001'),
('Tablet Plus', 'TP3001'),
('Smartwatch Basic', 'SW4001'),
('Wireless Earbuds', 'WE5001'),
('4K Monitor', 'KM6001'),
('Gaming Keyboard', 'GK7001'),
('Bluetooth Speaker', 'BS8001'),
('External Hard Drive', 'EH9001'),
('USB-C Hub', 'UC10001');

-- Inserciones en la tabla sales
INSERT INTO `sales` (`client_id`, `sell_date`) VALUES
(1, '2023-01-15 10:30:00'),
(2, '2023-01-20 15:45:00'),
(3, '2023-02-10 12:15:00'),
(4, '2023-02-15 09:00:00'),
(5, '2023-03-01 17:30:00'),
(6, '2023-03-10 11:25:00'),
(7, '2023-04-05 16:40:00'),
(8, '2023-04-20 10:50:00'),
(9, '2023-05-01 14:30:00'),
(10, '2023-05-15 13:20:00'),
(1, '2023-01-25 10:30:00');


-- Inserciones en la tabla sales_det
INSERT INTO `sales_det` (`sale_id`, `product_id`, `quantity`) VALUES
(1, 1, 1), -- Venta 1: 1 Laptop Pro
(1, 3, 2), -- Venta 1: 2 Tablet Plus
(2, 2, 1), -- Venta 2: 1 Smartphone X
(2, 4, 1), -- Venta 2: 1 Smartwatch Basic
(3, 5, 3), -- Venta 3: 3 Wireless Earbuds
(4, 6, 2), -- Venta 4: 2 4K Monitor
(4, 1, 1), -- Venta 4: 1 Laptop Pro
(5, 7, 1), -- Venta 5: 1 Gaming Keyboard
(5, 8, 2), -- Venta 5: 2 Bluetooth Speaker
(6, 9, 2), -- Venta 6: 2 External Hard Drive
(6, 5, 1), -- Venta 6: 1 Wireless Earbuds
(7, 1, 2), -- Venta 7: 2 Laptop Pro
(8, 10, 1), -- Venta 8: 1 USB-C Hub
(9, 3, 1), -- Venta 9: 1 Tablet Plus
(9, 2, 1), -- Venta 9: 1 Smartphone X
(10, 4, 2), -- Venta 10: 2 Smartwatch Basic
(10, 7, 1), -- Venta 10: 1 Gaming Keyboard
(11, 6, 4); -- Venta 10: 1 Gaming Keyboard

