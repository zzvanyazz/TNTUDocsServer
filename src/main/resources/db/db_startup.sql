create type document_status as enum ('ACTIVE', 'INOPERATIVE', 'ARCHIVED');

create table documents
(
    id          bigserial
        primary key,
    create_time timestamp,
    file_name   varchar(255),
    name        varchar(255),
    section_id  bigint,
    status      varchar(255)
);

create table registration_codes
(
    registration_code varchar(255) not null
        primary key,
    email             varchar(255),
    role_id           bigint
);

create table roles
(
    id               bigserial
        primary key,
    create_timestamp timestamp,
    description      varchar(255),
    name             varchar(255),
    update_timestamp timestamp
);

create table sections
(
    id   bigserial
        primary key,
    name varchar(255)
);

create table users
(
    id                    bigserial
        primary key,
    delete_timestamp      timestamp,
    email                 varchar(255),
    password_hash         varchar(255),
    username              varchar(255)
);

create table user_roles
(
    role_id bigint not null
        constraint fkh8ciramu9cc9q3qcqiv4ue8a6
            references roles,
    user_id bigint not null
        constraint fkhfh9dx7w3ubf1co1vdev94g3f
            references users,
    primary key (role_id, user_id)
);

INSERT INTO users (id, username, email, password_hash)
VALUES (1, 'admin', 'admin@mail.com', '$2y$12$2jyWBd7OWjcKIc1qP9EIEeHHlc8ALlFylvgLHccIk2FmMPUyvdDCG');


INSERT INTO roles (id, name, description)
VALUES (1, 'Admin', 'Admin of server');
INSERT INTO roles (id, name, description)
VALUES (2, 'Manager', 'Manager of server');
