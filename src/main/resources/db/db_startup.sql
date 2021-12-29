create type document_status as enum ('ACTIVE', 'INOPERATIVE', 'ARCHIVED');
CREATE TABLE users
(
    id                    BIGSERIAL           NOT NULL PRIMARY KEY,
    username              VARCHAR(128)        NOT NULL,
    normalized_username   VARCHAR(128) UNIQUE NOT NULL,
    password_hash         CHAR(60)            NOT NULL,
    email                 CHAR(60) UNIQUE     NOT NULL,
    enabled               BOOLEAN             NOT NULL DEFAULT true,
    delete_timestamp      TIMESTAMP WITH TIME ZONE,
    valid_token_timestamp TIMESTAMP WITH TIME ZONE     DEFAULT current_timestamp
);

INSERT INTO users (id, username, normalized_username, email, password_hash)
VALUES (1, 'Admin', 'admin', 'admin@mail.com', '$2y$12$2jyWBd7OWjcKIc1qP9EIEeHHlc8ALlFylvgLHccIk2FmMPUyvdDCG ');

CREATE TABLE roles
(
    id               BIGSERIAL                NOT NULL PRIMARY KEY,
    name             VARCHAR(32) UNIQUE       NOT NULL,
    description      VARCHAR(2048),
    create_timestamp TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT current_timestamp,
    update_timestamp TIMESTAMP WITH TIME ZONE
);

INSERT INTO roles (id, name, description)
VALUES (1, 'Admin', 'Admin of server');
INSERT INTO roles (id, name, description)
VALUES (2, 'Manager', 'Manager of server');

CREATE TABLE user_roles
(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,

    PRIMARY KEY (user_id, role_id),

    CONSTRAINT fk_user_roles__users__user_id FOREIGN KEY (user_id)
        REFERENCES users (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT fk_user_roles__roles__role_id FOREIGN KEY (role_id)
        REFERENCES roles (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

CREATE INDEX idx_user_roles__user_id ON user_roles (user_id);
CREATE INDEX idx_user_roles__role_id ON user_roles (role_id);

INSERT INTO user_roles (user_id, role_id)
VALUES (1, 1);

CREATE TABLE registration_codes
(
    registration_code TEXT UNIQUE PRIMARY KEY,
    email             VARCHAR(64) UNIQUE NOT NULL,
    role_id           BIGINT             NOT NULL,

    CONSTRAINT fk_registration_codes__roles__role_id FOREIGN KEY (role_id)
        REFERENCES roles (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
)

