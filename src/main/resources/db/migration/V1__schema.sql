CREATE TABLE person
(
    id              SERIAL primary key,
    name            VARCHAR(100),
    gender          CHAR(1),
    document_type   INT,
    document_number INT,
    nationality     INT,
    email           VARCHAR(100),
    birthdate       DATE,
    parent_id       INT null,
    FOREIGN KEY (parent_id) REFERENCES person (id),
    CONSTRAINT uc_type_doc_doc UNIQUE (document_type, document_number, nationality, gender)
);


INSERT INTO person (name, gender, document_type, document_number, nationality, email, birthdate)
VALUES ('Claudio', 'MALE', 0, 41004467, 0, 'claudio@gmail.com', '2000-01-01');
INSERT INTO person (name, gender, document_type, document_number, nationality, email, birthdate)
VALUES ('Francisco', 'MALE', 0, 43004467, 0, 'fran@gmail.com', '2000-01-01');
INSERT INTO person (name, gender, document_type, document_number, nationality, email, birthdate)
VALUES ('Rodrigo', 'MALE', 0, 43034467, 0, 'rodri@gmail.com', '2000-01-01');