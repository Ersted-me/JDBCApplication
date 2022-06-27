CREATE TABLE developer(
    id BIGINT NOT NULL AUTO_INCREMENT,
    firstname VARCHAR(100) NOT NULL,
    lastname VARCHAR(100) NOT NULL,
    specialtyId BIGINT NOT NULL,
    status VARCHAR(50) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (specialtyId) REFERENCES specialty (id)
);