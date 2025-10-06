CREATE TABLE categories (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) UNIQUE NOT NULL,
    description VARCHAR(255) NOT NULL,
    created_at DATETIME,
    updated_at DATETIME
);