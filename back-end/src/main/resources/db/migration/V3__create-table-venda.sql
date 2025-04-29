CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE venda(
    idVenda UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    data TIMESTAMP NOT NULL,
    total FLOAT NOT NULL,
    idCliente UUID NOT NULL,
    FOREIGN KEY (idCliente) REFERENCES cliente(idCliente)
);