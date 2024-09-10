CREATE TABLE address (
    id BIG SERIAL PRIMARY KEY,
    street VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    state VARCHAR(255) NOT NULL,
    zip_code VARCHAR(20) NOT NULL
);

CREATE TABLE company (
    id BIG SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    cnpj VARCHAR(20) NOT NULL UNIQUE,
    address_id BIGINT REFERENCES address(id)
);

CREATE TABLE phone (
    id BIG SERIAL PRIMARY KEY,
    phone_number VARCHAR(20) NOT NULL,
    company_id BIGINT REFERENCES company(id)
);
