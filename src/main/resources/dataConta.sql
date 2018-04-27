INSERT INTO Conta (id, chave, nome, dataCriacao, tipoConta)
VALUES(1, '01310200', 'Conta PF', sysdate, 'PF');

INSERT INTO Conta (id, chave, nome, dataCriacao, tipoConta)
VALUES(2, '09726121', 'Conta PF', sysdate, 'PF');

INSERT INTO Conta (id, chave, nome, dataCriacao, tipoConta)
VALUES(3, '09726110', 'Conta PF', sysdate, 'PF');

INSERT INTO Conta (id, chave, nome, dataCriacao, tipoConta)
VALUES(4, '22793012', 'Conta PJ', sysdate, 'PJ');

INSERT INTO Conta (id, chave, nome, dataCriacao, tipoConta)
VALUES(5, '90810240', 'Conta PJ', sysdate, 'PJ');

INSERT INTO Conta (id, chave, nome, dataCriacao, tipoConta)
VALUES(6, '22793850', 'Conta PJ', sysdate, 'PJ');


INSERT INTO TipoConta(id, tipoConta, cpf_cnpj, chave, nomeRazao, nomeFantasia, dataNascimento)
VALUES(1, 'PF', '43310065812', '01310200', 'Maria Santos', null, '1981-05-01');

INSERT INTO TipoConta(id, tipoConta, cpf_cnpj, nomeRazao, nomeFantasia, dataNascimento)
VALUES(2, 'PF', '53307536435', '09726121', 'Patricia Bilas', null, '1972-01-10');

INSERT INTO TipoConta(id, tipoConta, cpf_cnpj, chave, nomeRazao, nomeFantasia, dataNascimento)
VALUES(3, 'PF', '43310065812', '09726110', 'Marcos Silva', null, null);

INSERT INTO TipoConta(id, tipoConta, cpf_cnpj, nomeRazao, nomeFantasia, dataNascimento)
VALUES(4, 'PJ', '16653231000134', '22793012', 'Empresa1', 'Americans Import', null);

INSERT INTO TipoConta(id, tipoConta, cpf_cnpj, nomeRazao, nomeFantasia, dataNascimento)
VALUES(5, 'PJ', '94705836000135', '90810240', 'Empresa2', 'Brasil Imports', null);

INSERT INTO TipoConta(id, tipoConta, cpf_cnpj, nomeRazao, nomeFantasia, dataNascimento)
VALUES(6, 'PJ', '30666880000109', '22793850', 'Empresa3', 'LineUp Imports', null);

