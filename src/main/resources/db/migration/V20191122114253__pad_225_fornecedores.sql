create table fornecedores(
    id int IDENTITY NOT NULL,
    razao_social varchar(80) NOT NULL,
    cnpj varchar(14) NOT NULL,
    nome_fantasia varchar(80) NOT NULL,
    telefone varchar(14) NOT NULL,
    email varchar(80) NOT NULL
);