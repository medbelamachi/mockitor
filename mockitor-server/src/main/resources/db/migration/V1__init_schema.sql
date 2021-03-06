CREATE TABLE mck_application
(
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    uuid        RAW(16)                                 NOT NULL default random_uuid(),
    name        VARCHAR2(255),
    url         VARCHAR2(255),
    description VARCHAR2(255),
    CONSTRAINT pk_mck_application PRIMARY KEY (id)
);

CREATE TABLE mck_dependency
(
    id             BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    uuid           RAW(16)                                 NOT NULL default random_uuid(),
    name           VARCHAR2(255),
    url            VARCHAR2(255),
    description    VARCHAR2(255),
    application_id BIGINT,
    CONSTRAINT pk_mck_dependency PRIMARY KEY (id)
);


CREATE TABLE mck_endpoint
(
    id            BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    uuid          RAW(16)                                 NOT NULL default random_uuid(),
    name          VARCHAR2(255),
    description   VARCHAR2(255),
    data          CLOB,
    dependency_id BIGINT,
    CONSTRAINT pk_mck_endpoint PRIMARY KEY (id)
);


ALTER TABLE mck_dependency
    ADD CONSTRAINT FK_DEPENDENCY_ON_APPLICATION FOREIGN KEY (application_id) REFERENCES mck_application (id);


ALTER TABLE mck_endpoint
    ADD CONSTRAINT FK_ENDPOINT_ON_DEPENDENCY FOREIGN KEY (dependency_id) REFERENCES mck_dependency (id);