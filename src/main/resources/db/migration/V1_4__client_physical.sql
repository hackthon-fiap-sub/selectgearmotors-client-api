create table client.tb_client_physical (
    id bigserial not null,
    social_id VARCHAR(14) UNIQUE,
    social_id_dispatch_date date,
    document_id varchar(255),
    document_district varchar(255),
    document_dispatch_date date,
    birth_date date,
    client_id bigint not null,
    create_by varchar(255) not null,
    created_date timestamp(6) not null,
    last_modified_by varchar(255),
    last_modified_date timestamp(6),
    status varchar(255) not null,
    primary key (id),
    FOREIGN KEY (client_id) REFERENCES client.tb_client(id)
);

CREATE UNIQUE INDEX idx_client_social_id ON client.tb_client_physical(social_id);

