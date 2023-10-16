DROP TABLE IF EXISTS visita;
DROP TABLE IF EXISTS user;

CREATE TABLE user(
	nome VARCHAR(60) NOT NULL,
    cognome VARCHAR(60),
    password VARCHAR(60),
    cf VARCHAR(16) NOT NULL,
    PRIMARY KEY(cf)
);

CREATE TABLE visita(
	id INT AUTO_INCREMENT,
    tipo VARCHAR(60),
    data_visita VARCHAR(60),
    orario_visita VARCHAR(60),
    cf VARCHAR(16) NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(cf) REFERENCES user(cf) ON DELETE CASCADE
);