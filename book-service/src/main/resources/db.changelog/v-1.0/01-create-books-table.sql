CREATE TABLE books (
         id BIGSERIAL NOT NULL,
         author VARCHAR(255),
         description VARCHAR(255),
         genre VARCHAR(255),
         isbn VARCHAR(255),
         title VARCHAR(255),
         PRIMARY KEY (id)
);