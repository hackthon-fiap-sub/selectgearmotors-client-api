create table client.tb_client_legal (
    id bigserial not null,
    company_id VARCHAR(14) UNIQUE,
    client_id bigint not null,
    create_by varchar(255) not null,
    created_date timestamp(6) not null,
    last_modified_by varchar(255),
    last_modified_date timestamp(6),
    status varchar(255) not null,
    primary key (id),
    FOREIGN KEY (client_id) REFERENCES client.tb_client(id)
);

CREATE UNIQUE INDEX idx_client_company_id ON client.tb_client_legal(company_id);