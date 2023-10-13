-- ----------------------------
-- Table structure for gen_data_source
-- ----------------------------
DROP TABLE IF EXISTS gen_data_source;
CREATE TABLE gen_data_source
(
    id            SERIAL PRIMARY KEY,
    type          text        NOT NULL,
    name          text        NOT NULL,
    host          text        NOT NULL,
    port          text        NOT NULL,
    url_suffix    text        NOT NULL,
    username      text        NOT NULL,
    password      text        NOT NULL,
    order_key     bigint      NOT NULL DEFAULT 0,
    created_time  timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_time timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    remark        text        NOT NULL DEFAULT ''
);

-- ----------------------------
-- Table structure for gen_schema
-- ----------------------------
DROP TABLE IF EXISTS gen_schema;
CREATE TABLE gen_schema
(
    id             SERIAL PRIMARY KEY,
    data_source_id bigint      NOT NULL REFERENCES gen_data_source (id) ON DELETE CASCADE ON UPDATE RESTRICT,
    name           text        NOT NULL,
    order_key      bigint      NOT NULL DEFAULT 0,
    created_time   timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_time  timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    remark         text        NOT NULL DEFAULT ''
);

CREATE INDEX fk_schema_data_source ON gen_schema (data_source_id);

-- ----------------------------
-- Table structure for gen_table
-- ----------------------------
DROP TABLE IF EXISTS gen_table;
CREATE TABLE gen_table
(
    id            SERIAL PRIMARY KEY,
    schema_id     bigint      NOT NULL REFERENCES gen_schema (id) ON DELETE CASCADE ON UPDATE RESTRICT,
    name          text        NOT NULL,
    comment       text        NOT NULL,
    type          text        NOT NULL,
    order_key     bigint      NOT NULL DEFAULT 0,
    created_time  timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_time timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    remark        text        NOT NULL DEFAULT ''
);

CREATE INDEX fk_table_schema ON gen_table (schema_id);

-- ----------------------------
-- Table structure for gen_column
-- ----------------------------
DROP TABLE IF EXISTS gen_column;
CREATE TABLE gen_column
(
    id                SERIAL PRIMARY KEY,
    table_id          bigint      NOT NULL REFERENCES gen_table (id) ON DELETE CASCADE ON UPDATE RESTRICT,
    order_key         bigint      NOT NULL,
    name              text        NOT NULL,
    type_code         int         NOT NULL,
    type              text        NOT NULL,
    display_size      bigint      NOT NULL DEFAULT 0,
    numeric_precision bigint      NOT NULL DEFAULT 0,
    default_value     text        NULL     DEFAULT NULL,
    comment           text        NOT NULL,
    is_pk             boolean     NOT NULL DEFAULT FALSE,
    is_auto_increment boolean     NOT NULL DEFAULT FALSE,
    is_fk             boolean     NOT NULL DEFAULT FALSE,
    is_unique         boolean     NOT NULL DEFAULT FALSE,
    is_not_null       boolean     NOT NULL DEFAULT FALSE,
    created_time      timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_time     timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    remark            text        NOT NULL DEFAULT ''
);

CREATE INDEX fk_column_table ON gen_column (table_id);

-- ----------------------------
-- Table structure for gen_association
-- ----------------------------
DROP TABLE IF EXISTS gen_association;
CREATE TABLE gen_association
(
    id                SERIAL PRIMARY KEY,
    comment           text        NOT NULL DEFAULT '',
    source_column_id  bigint      NOT NULL REFERENCES gen_column (id) ON DELETE CASCADE ON UPDATE RESTRICT,
    target_column_id  bigint      NOT NULL REFERENCES gen_column (id) ON DELETE CASCADE ON UPDATE RESTRICT,
    association_type  text        NOT NULL,
    dissociate_action text        NULL     DEFAULT NULL,
    order_key         bigint      NOT NULL DEFAULT 0,
    created_time      timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_time     timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    remark            text        NOT NULL DEFAULT ''
);

CREATE INDEX fk_association_source_column ON gen_association (source_column_id);
CREATE INDEX fk_association_target_column ON gen_association (target_column_id);

-- ----------------------------
-- Table structure for gen_package
-- ----------------------------
DROP TABLE IF EXISTS gen_package;
CREATE TABLE gen_package
(
    id            SERIAL PRIMARY KEY,
    parent_id     bigint      NULL     DEFAULT NULL REFERENCES gen_package (id) ON DELETE CASCADE ON UPDATE RESTRICT,
    name          text        NOT NULL,
    order_key     bigint      NOT NULL DEFAULT 0,
    created_time  timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_time timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    remark        text        NOT NULL DEFAULT ''
);

CREATE INDEX fk_package_parent ON gen_package (parent_id);

-- ----------------------------
-- Table structure for gen_entity
-- ----------------------------
DROP TABLE IF EXISTS gen_entity;
CREATE TABLE gen_entity
(
    id            SERIAL PRIMARY KEY,
    package_id    bigint      NULL     DEFAULT NULL REFERENCES gen_package (id) ON DELETE SET NULL ON UPDATE RESTRICT,
    table_id      bigint      NOT NULL REFERENCES gen_table (id) ON DELETE CASCADE ON UPDATE RESTRICT,
    name          text        NOT NULL,
    comment       text        NOT NULL,
    author        text        NOT NULL DEFAULT '',
    gen_path      text        NOT NULL DEFAULT '/',
    is_add        boolean     NOT NULL DEFAULT TRUE,
    is_edit       boolean     NOT NULL DEFAULT TRUE,
    is_list       boolean     NOT NULL DEFAULT TRUE,
    is_query      boolean     NOT NULL DEFAULT TRUE,
    order_key     bigint      NOT NULL DEFAULT 0,
    created_time  timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_time timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    remark        text        NOT NULL DEFAULT ''
);

CREATE UNIQUE INDEX u_entity_table ON gen_entity (table_id);
CREATE INDEX fk_entity_package ON gen_entity (package_id);
CREATE INDEX fk_entity_table ON gen_entity (table_id);

-- ----------------------------
-- Table structure for gen_enum
-- ----------------------------
DROP TABLE IF EXISTS gen_enum;
CREATE TABLE gen_enum
(
    id            SERIAL PRIMARY KEY,
    package_id    bigint      NULL     DEFAULT NULL REFERENCES gen_package (id) ON DELETE SET NULL ON UPDATE RESTRICT,
    name          text        NOT NULL,
    comment       text        NOT NULL,
    order_key     bigint      NOT NULL DEFAULT 0,
    created_time  timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_time timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    remark        text        NOT NULL DEFAULT ''
);

CREATE INDEX fk_enum_package ON gen_enum (package_id);

-- ----------------------------
-- Table structure for gen_enum_item
-- ----------------------------
DROP TABLE IF EXISTS gen_enum_item;
CREATE TABLE gen_enum_item
(
    id            SERIAL PRIMARY KEY,
    enum_id       bigint      NOT NULL REFERENCES gen_enum (id) ON DELETE CASCADE ON UPDATE RESTRICT,
    name          text        NOT NULL,
    value         text        NOT NULL,
    comment       text        NOT NULL DEFAULT '',
    created_time  timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_time timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    remark        text        NOT NULL DEFAULT ''
);

CREATE INDEX fk_enum_item_enum ON gen_enum_item (enum_id);

-- ----------------------------
-- Table structure for gen_property
-- ----------------------------
DROP TABLE IF EXISTS gen_property;
CREATE TABLE gen_property
(
    id                     SERIAL PRIMARY KEY,
    entity_id              bigint      NOT NULL REFERENCES gen_entity (id) ON DELETE CASCADE ON UPDATE RESTRICT,
    column_id              bigint      NULL     DEFAULT NULL REFERENCES gen_column (id) ON DELETE SET NULL ON UPDATE RESTRICT,
    name                   text        NOT NULL,
    comment                text        NOT NULL,
    type                   text        NOT NULL,
    type_table_id          bigint      NULL     DEFAULT NULL REFERENCES gen_table (id) ON DELETE SET NULL ON UPDATE RESTRICT,
    is_list                boolean     NOT NULL DEFAULT FALSE,
    is_not_null            boolean     NOT NULL DEFAULT FALSE,
    is_id                  boolean     NOT NULL DEFAULT FALSE,
    id_generation_type     text        NULL     DEFAULT NULL,
    is_key                 boolean     NOT NULL DEFAULT FALSE,
    is_logical_delete      boolean     NOT NULL DEFAULT FALSE,
    is_id_view             boolean     NOT NULL DEFAULT FALSE,
    id_view_annotation     text        NULL     DEFAULT NULL,
    association_type       text        NULL     DEFAULT NULL,
    association_annotation text        NULL     DEFAULT NULL,
    dissociate_annotation  text        NULL     DEFAULT NULL,
    other_annotation       text        NULL     DEFAULT NULL,
    enum_id                bigint      NULL     DEFAULT NULL REFERENCES gen_enum (id) ON DELETE SET NULL ON UPDATE RESTRICT,
    created_time           timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_time          timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    remark                 text        NOT NULL DEFAULT ''
);

CREATE INDEX fk_property_column ON gen_property (column_id);
CREATE INDEX fk_property_entity ON gen_property (entity_id);
CREATE INDEX fk_property_enum ON gen_property (enum_id);
CREATE INDEX fk_property_type_table ON gen_property (type_table_id);

-- ----------------------------
-- Table structure for gen_type_mapping
-- ----------------------------
DROP TABLE IF EXISTS gen_type_mapping;
CREATE TABLE gen_type_mapping
(
    id              bigint      NOT NULL,
    type_expression text        NOT NULL,
    is_regex        boolean     NOT NULL DEFAULT FALSE,
    property_type   text        NOT NULL,
    order_key       bigint      NOT NULL DEFAULT 0,
    created_time    timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_time   timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    remark          text        NOT NULL DEFAULT ''
);
