CREATE TABLE m_user
(
    id              VARCHAR(255) NOT NULL,
    email           VARCHAR(255) NOT NULL,
    mark_for_delete BOOLEAN,
    name            VARCHAR(255) NOT NULL,
    password        VARCHAR(255) NOT NULL,
    username        VARCHAR(255) NOT NULL,
    PRIMARY KEY(id),
    UNIQUE(username)
);
CREATE INDEX idx_m_user_email ON m_user(email);
CREATE INDEX idx_m_user_username ON m_user(username);
CREATE INDEX idx_m_user_password ON m_user(password);

CREATE TABLE m_session
(
    id          VARCHAR(255) NOT NULL,
    hostname    VARCHAR(255) NOT NULL,
    session_id  VARCHAR(255) NOT NULL,
    username    VARCHAR(255) NOT NULL,
    PRIMARY KEY(id),
    UNIQUE(username)
);
CREATE INDEX idx_m_session_username ON m_session(username);
CREATE INDEX idx_m_session_session_id ON m_session(session_id);

CREATE TABLE m_module
(
    id              VARCHAR(255) NOT NULL,
    code            VARCHAR(255) NOT NULL,
    context_path    VARCHAR(255) NOT NULL,
    created_by      VARCHAR(255) NOT NULL,
    created_date    TIMESTAMP NOT NULL,
    enable          BOOLEAN,
    mark_for_delete BOOLEAN,
    name            VARCHAR(255) NOT NULL,
    updated_by      VARCHAR(255) NOT NULL,
    updated_date    TIMESTAMP NOT NULL,
    optlock         BIGINT,
    PRIMARY KEY(id)
);
CREATE INDEX idx_m_module_code ON m_module(code);

CREATE TABLE m_endpoint
(
    id              VARCHAR(255) NOT NULL,
    code            VARCHAR(255) NOT NULL,
    created_by      VARCHAR(255) NOT NULL,
    created_date    TIMESTAMP NOT NULL,
    enable          BOOLEAN,
    mark_for_delete BOOLEAN,
    module_code     VARCHAR(255) NOT NULL,
    path_variable   BYTEA,
    request_body    BYTEA,
    request_header  BYTEA,
    request_method  VARCHAR(255) NOT NULL,
    request_param   BYTEA,
    response_body   BYTEA,
    response_header BYTEA,
    updated_by      VARCHAR(255) NOT NULL,
    updated_date    TIMESTAMP NOT NULL,
    url             BYTEA NOT NULL,
    optlock         BIGINT,
    module_id       VARCHAR(255),
    PRIMARY KEY(id),
    FOREIGN KEY(module_id) REFERENCES m_module(id) MATCH SIMPLE
);
CREATE INDEX idx_m_endpoint_code ON m_endpoint(code);
CREATE INDEX idx_m_endpoint_module_code ON m_endpoint(module_code);