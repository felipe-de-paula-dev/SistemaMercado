CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE cliente(
    idCliente UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(100) NOT NULL,
    documento VARCHAR(20) NOT NULL
);