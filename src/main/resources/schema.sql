-- Create the User table
CREATE TABLE IF NOT EXISTS customer (
                      user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      login VARCHAR(255) NOT NULL,
                      password_hash VARCHAR(255) NOT NULL
);

-- Create the Order table
CREATE TABLE IF NOT EXISTS customer_order (
                       order_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       created_at TIMESTAMP NOT NULL,
                       customer_id BIGINT NOT NULL,
                       status VARCHAR NOT NULL,
                       FOREIGN KEY (customer_id) REFERENCES customer(user_id)
);

-- Create the Goods table
CREATE TABLE IF NOT EXISTS goods (
                        goods_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        order_code VARCHAR(255) NOT NULL,
                        title VARCHAR(255),
                        price DOUBLE,
                        stock INT
);

-- Create the OrderItem table
CREATE TABLE IF NOT EXISTS order_item (
                            order_item_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            order_id BIGINT NOT NULL,
                            goods_id BIGINT NOT NULL,
                            amount INT,
                            FOREIGN KEY (order_id) REFERENCES customer_order(order_id),
                            FOREIGN KEY (goods_id) REFERENCES goods(goods_id)
);