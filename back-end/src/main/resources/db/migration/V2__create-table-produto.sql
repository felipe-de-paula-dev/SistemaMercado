CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE produto(
    idProduto UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(100) NOT NULL,
    preco FLOAT NOT NULL
);