CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE item_venda(
    idItemVenda UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    data TIMESTAMP NOT NULL,
    subtotal FLOAT NOT NULL,
    qtde INTEGER NOT NULL,
    idVenda UUID NOT NULL,
    idProduto UUID NOT NULL,
    FOREIGN KEY (idVenda) REFERENCES venda(idVenda),
    FOREIGN KEY (idProduto) REFERENCES produto(idProduto)
);