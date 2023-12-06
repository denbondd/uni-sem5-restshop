CREATE DATABASE IF NOT EXISTS shop_patterns;

USE shop_patterns;

DROP TABLE IF EXISTS shop_has_product;
DROP TABLE IF EXISTS shop;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS brand;

DROP PROCEDURE IF EXISTS get_catalog;

CREATE TABLE brand (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(48) NOT NULL
);

CREATE TABLE category (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(48) NOT NULL
);

CREATE TABLE product (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(48) NOT NULL,
    price INT NOT NULL,
    category_id INT NOT NULL,
    brand_id INT NOT NULL,
    
    FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (brand_id) REFERENCES brand(id) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE shop (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(48) NOT NULL,
    location VARCHAR(96) NOT NULL
);

CREATE TABLE shop_has_product (
	shop_id INT NOT NULL,
    product_id INT NOT NULL,
    amount INT NOT NULL,
    
    PRIMARY KEY (shop_id, product_id),
    FOREIGN KEY (shop_id) REFERENCES shop(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE ON UPDATE CASCADE
);


DELIMITER //
CREATE PROCEDURE get_catalog(IN shop_id INT)
BEGIN
    SELECT * FROM shop_has_product JOIN product ON shop_has_product.product_id = product.id WHERE shop_has_product.shop_id = shop_id;
END //
DELIMITER ;