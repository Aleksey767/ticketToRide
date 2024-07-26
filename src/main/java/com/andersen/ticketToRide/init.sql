CREATE TYPE city_name AS ENUM ('BRISTOL','BIRMINGHAM','COVENTRY','NORTHAMPTON','LONDON','READING','SWINDEN');
CREATE TYPE roles AS ENUM ('ADMIN', 'USER');

CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       role roles DEFAULT 'USER',
                       balance numeric(15, 2) NOT NULL,
                       real_name VARCHAR(255) NOT NULL
);

CREATE TABLE ticket (
                        id SERIAL PRIMARY KEY,
                        departure city_name NOT NULL,
                        arrival city_name NOT NULL,
                        price numeric(10,2) NOT NULL,
                        currency VARCHAR(10) NOT NULL,
                        traveller_amount INT NOT NULL,
                        traveller_id INT NOT NULL,
                        FOREIGN KEY (traveller_id) REFERENCES users (id) ON DELETE CASCADE
);