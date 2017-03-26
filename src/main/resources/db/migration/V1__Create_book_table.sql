CREATE TABLE books
(
    id CHAR(36) PRIMARY KEY,
    title VARCHAR(500) NOT NULL,
    author VARCHAR(255) NOT NULL,
    description TEXT,
    isbn VARCHAR(17) NOT NULL,
    pages SMALLINT NOT NULL,
    publisher VARCHAR(255),
    published DATE
);