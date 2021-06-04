create database docs;

create table users
(
    id                    bigserial                             not null
        constraint users_pkey
            primary key,
    username              varchar(128)                          not null,
    normalized_username   varchar(128)                          not null
        constraint users_normalized_username_key
            unique,
    password_hash         char(60)                              not null,
    email                 char(60)                              not null
        constraint users_email_key
            unique,
    enabled               boolean                  default true not null,
    delete_timestamp      timestamp with time zone,
    valid_token_timestamp timestamp with time zone default CURRENT_TIMESTAMP
);

create table roles
(
    id               bigserial                                          not null
        constraint roles_pkey
            primary key,
    name             varchar(32)                                        not null
        constraint roles_name_key
            unique,
    description      varchar(2048),
    create_timestamp timestamp with time zone default CURRENT_TIMESTAMP not null,
    update_timestamp timestamp with time zone
);

create table user_roles
(
    user_id bigint not null
        constraint fk_user_roles__users__user_id
            references users
            on update cascade on delete cascade,
    role_id bigint not null
        constraint fk_user_roles__roles__role_id
            references roles
            on update cascade on delete cascade,
    constraint user_roles_pkey
        primary key (user_id, role_id)
);

create index idx_user_roles__user_id
    on user_roles (user_id);

create index idx_user_roles__role_id
    on user_roles (role_id);

create table registration_codes
(
    registration_code text        not null
        constraint registration_codes_pkey
            primary key,
    email             varchar(64) not null
        constraint registration_codes_email_key
            unique,
    role_id           bigint      not null
        constraint fk_registration_codes__roles__role_id
            references roles
            on update cascade on delete cascade
);

create table sections
(
    id   bigserial    not null
        constraint sections_pk
            primary key,
    name varchar(256) not null
);

create unique index sections_name_uindex
    on sections (name);

create table documents
(
    id          bigserial                           not null,
    section_id  bigint                              not null
        constraint documents_sections_id_fk
            references sections
            on update cascade on delete cascade,
    name        varchar(1024)                       not null,
    create_time timestamp default CURRENT_TIMESTAMP not null,
    status      document_status                     not null,
    file_name   varchar(256)
);

create unique index documents_id_uindex
    on documents (id);

INSERT INTO public.roles (id, name, description, create_timestamp, update_timestamp) VALUES (1, 'Admin', 'Admin of server', '2021-02-13 12:33:56.599493', null);
INSERT INTO public.roles (id, name, description, create_timestamp, update_timestamp) VALUES (2, 'Manager', 'Manager of server', '2021-02-13 12:33:56.599493', null);

INSERT INTO public.users (id, username, normalized_username, password_hash, email, enabled, delete_timestamp, valid_token_timestamp) VALUES (1, 'Admin', 'admin', '$2y$12$2jyWBd7OWjcKIc1qP9EIEeHHlc8ALlFylvgLHccIk2FmMPUyvdDCG', 'admin@mail.com', true, null, '2021-02-13 12:33:56.575134');

INSERT INTO public.user_roles (user_id, role_id) VALUES (1, 1);
