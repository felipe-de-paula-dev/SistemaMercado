ALTER TABLE cliente
ADD CONSTRAINT documento_unique UNIQUE (documento);

ALTER TABLE venda
ADD CONSTRAINT fk_cliente
FOREIGN KEY (idCliente)
REFERENCES cliente(idCliente)
ON DELETE CASCADE;