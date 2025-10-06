CREATE TABLE questions (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    `text` LONGTEXT NOT NULL,
    answer BOOLEAN NOT NULL,
    category_id BIGINT NOT NULL,
    created_at DATETIME,
    updated_at DATETIME
);

ALTER TABLE questions
    ADD CONSTRAINT FK_QUESTIONS_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES categories (id);