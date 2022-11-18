CREATE TABLE application
(
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    uuid        RAW(16)                                 NOT NULL default random_uuid(),
    name        VARCHAR2(255),
    url         VARCHAR2(255),
    description VARCHAR2(255),
    CONSTRAINT pk_application PRIMARY KEY (id)
);

CREATE TABLE dependency
(
    id             BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    uuid           RAW(16)                                 NOT NULL default random_uuid(),
    name           VARCHAR2(255),
    url            VARCHAR2(255),
    description    VARCHAR2(255),
    application_id BIGINT,
    CONSTRAINT pk_dependency PRIMARY KEY (id)
);


CREATE TABLE endpoint
(
    id            BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    uuid          RAW(16)                                 NOT NULL default random_uuid(),
    name          VARCHAR2(255),
    description   VARCHAR2(255),
    data          CLOB,
    dependency_id BIGINT,
    CONSTRAINT pk_endpoint PRIMARY KEY (id)
);


ALTER TABLE dependency
    ADD CONSTRAINT FK_DEPENDENCY_ON_APPLICATION FOREIGN KEY (application_id) REFERENCES application (id);


ALTER TABLE endpoint
    ADD CONSTRAINT FK_ENDPOINT_ON_DEPENDENCY FOREIGN KEY (dependency_id) REFERENCES dependency (id);