DROP TABLE user IF EXISTS;

CREATE TABLE user (
    id INT identity not null primary key,
    name VARCHAR(100),
    gender  CHAR(1),
    document_type INT,
    document_number INT,
    nationality INT,
    email VARCHAR(100),
    birth DATE,
    CONSTRAINT uc_type_doc_doc UNIQUE (document_type, document_number, nationality, gender)
);