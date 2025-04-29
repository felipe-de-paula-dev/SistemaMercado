ALTER TABLE item_venda
DROP CONSTRAINT item_venda_idvenda_fkey;

ALTER TABLE item_venda
ADD CONSTRAINT item_venda_idvenda_fkey
FOREIGN KEY (idVenda)
REFERENCES venda(idVenda)
ON DELETE CASCADE;
