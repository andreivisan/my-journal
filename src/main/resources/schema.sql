CREATE TABLE entry (
    id serial PRIMARY KEY NOT NULL,
    created_on TIMESTAMP NOT NULL,
    body VARCHAR(25000) NOT NULL,
    status VARCHAR(100) NOT NULL
);