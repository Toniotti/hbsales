create table fornecedores(
    id_fornecedor int IDENTITY NOT NULL,
    PRIMARY KEY(id_fornecedor),
    razao_social varchar(80) NOT NULL,
    cnpj varchar(14) NOT NULL,
    nome_fantasia varchar(80) NOT NULL,
    telefone varchar(14) NOT NULL,
    email varchar(80) NOT NULL
);

create unique index ix_fornecedores_01 on fornecedores (cnpj asc);
