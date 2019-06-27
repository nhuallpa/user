DROP TABLE person IF EXISTS;

CREATE TABLE person (
    id INT identity not null primary key,
    name VARCHAR(100),
    gender  CHAR(1),
    document_type INT,
    document_number INT,
    nationality INT,
    email VARCHAR(100),
    birth DATE,
    parent_id INT null,
    FOREIGN KEY (parent_id) REFERENCES person(id),
    CONSTRAINT uc_type_doc_doc UNIQUE (document_type, document_number, nationality, gender)
);