CREATE OR REPLACE FUNCTION update_modified_time()
    RETURNS TRIGGER AS
$$
BEGIN
    NEW.modified_time = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TABLE IF EXISTS "gen_package" CASCADE;
DROP TABLE IF EXISTS "gen_enum" CASCADE;
DROP TABLE IF EXISTS "gen_enum_item" CASCADE;
DROP TABLE IF EXISTS "gen_model" CASCADE;
DROP TABLE IF EXISTS "gen_data_source" CASCADE;
DROP TABLE IF EXISTS "gen_schema" CASCADE;
DROP TABLE IF EXISTS "gen_table" CASCADE;
DROP TABLE IF EXISTS "gen_column" CASCADE;
DROP TABLE IF EXISTS "gen_association" CASCADE;
DROP TABLE IF EXISTS "gen_entity" CASCADE;
DROP TABLE IF EXISTS "gen_property" CASCADE;
DROP TABLE IF EXISTS "gen_type_mapping" CASCADE;

-- ----------------------------
-- Table structure for gen_package
-- ----------------------------
CREATE TABLE "gen_package"
(
    "id"            BIGSERIAL PRIMARY KEY,
    "parent_id"     bigint      NULL     DEFAULT NULL REFERENCES "gen_package" ("id") ON DELETE CASCADE ON UPDATE RESTRICT,
    "name"          text        NOT NULL,
    "order_key"     bigint      NOT NULL DEFAULT 0,
    "created_time"  timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "modified_time" timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "remark"        text        NOT NULL DEFAULT ''
);

CREATE INDEX "fk_package_parent" ON "gen_package" ("parent_id");

COMMENT ON TABLE "gen_package" IS '生成包';
COMMENT ON COLUMN "gen_package"."id" IS 'ID';
COMMENT ON COLUMN "gen_package"."parent_id" IS '父包';
COMMENT ON COLUMN "gen_package"."name" IS '包名称';
COMMENT ON COLUMN "gen_package"."order_key" IS '自定排序';
COMMENT ON COLUMN "gen_package"."created_time" IS '创建时间';
COMMENT ON COLUMN "gen_package"."modified_time" IS '修改时间';
COMMENT ON COLUMN "gen_package"."remark" IS '备注';

CREATE TRIGGER "trg_update_gen_package_modified_time"
    BEFORE UPDATE
    ON "gen_package"
    FOR EACH ROW
EXECUTE FUNCTION update_modified_time();

-- ----------------------------
-- Table structure for gen_enum
-- ----------------------------
CREATE TABLE "gen_enum"
(
    "id"            BIGSERIAL PRIMARY KEY,
    "package_id"    bigint      NULL     DEFAULT NULL REFERENCES "gen_package" ("id") ON DELETE SET NULL ON UPDATE RESTRICT,
    "name"          text        NOT NULL,
    "comment"       text        NOT NULL,
    "enum_type"     text        NULL,
    "order_key"     bigint      NOT NULL DEFAULT 0,
    "created_time"  timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "modified_time" timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "remark"        text        NOT NULL DEFAULT ''
);

CREATE INDEX "fk_enum_package" ON "gen_enum" ("package_id");

COMMENT ON TABLE "gen_enum" IS '生成枚举';
COMMENT ON COLUMN "gen_enum"."id" IS 'ID';
COMMENT ON COLUMN "gen_enum"."package_id" IS '所属包';
COMMENT ON COLUMN "gen_enum"."name" IS '枚举名';
COMMENT ON COLUMN "gen_enum"."comment" IS '枚举注释';
COMMENT ON COLUMN "gen_enum"."enum_type" IS '枚举类型';
COMMENT ON COLUMN "gen_enum"."order_key" IS '自定排序';
COMMENT ON COLUMN "gen_enum"."created_time" IS '创建时间';
COMMENT ON COLUMN "gen_enum"."modified_time" IS '修改时间';
COMMENT ON COLUMN "gen_enum"."remark" IS '备注';

CREATE TRIGGER "trg_update_gen_enum_modified_time"
    BEFORE UPDATE
    ON "gen_enum"
    FOR EACH ROW
EXECUTE FUNCTION update_modified_time();

-- ----------------------------
-- Table structure for gen_enum_item
-- ----------------------------
CREATE TABLE "gen_enum_item"
(
    "id"            BIGSERIAL PRIMARY KEY,
    "enum_id"       bigint      NOT NULL REFERENCES "gen_enum" ("id") ON DELETE CASCADE ON UPDATE RESTRICT,
    "name"          text        NOT NULL,
    "value"         text        NOT NULL,
    "comment"       text        NOT NULL DEFAULT '',
    "created_time"  timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "modified_time" timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "remark"        text        NOT NULL DEFAULT ''
);

CREATE INDEX "fk_enum_item_enum" ON "gen_enum_item" ("enum_id");

COMMENT ON TABLE "gen_enum_item" IS '生成枚举元素';
COMMENT ON COLUMN "gen_enum_item"."id" IS 'ID';
COMMENT ON COLUMN "gen_enum_item"."enum_id" IS '对应枚举 ID';
COMMENT ON COLUMN "gen_enum_item"."name" IS '元素名';
COMMENT ON COLUMN "gen_enum_item"."value" IS '元素值';
COMMENT ON COLUMN "gen_enum_item"."comment" IS '元素注释';
COMMENT ON COLUMN "gen_enum_item"."created_time" IS '创建时间';
COMMENT ON COLUMN "gen_enum_item"."modified_time" IS '修改时间';
COMMENT ON COLUMN "gen_enum_item"."remark" IS '备注';

CREATE TRIGGER "trg_update_gen_enum_item_modified_time"
    BEFORE UPDATE
    ON "gen_enum_item"
    FOR EACH ROW
EXECUTE FUNCTION update_modified_time();

-- ----------------------------
-- Table structure for gen_model
-- ----------------------------
CREATE TABLE "gen_model"
(
    "id"            BIGSERIAL PRIMARY KEY,
    "name"          text        NOT NULL,
    "value"         text        NOT NULL,
    "created_time"  timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "modified_time" timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "remark"        text        NOT NULL
);

COMMENT ON TABLE "gen_model" IS '生成模型';
COMMENT ON COLUMN "gen_model"."id" IS 'ID';
COMMENT ON COLUMN "gen_model"."name" IS '名称';
COMMENT ON COLUMN "gen_model"."value" IS '模型 JSON 数据';
COMMENT ON COLUMN "gen_model"."created_time" IS '创建时间';
COMMENT ON COLUMN "gen_model"."modified_time" IS '修改时间';
COMMENT ON COLUMN "gen_model"."remark" IS '备注';

CREATE TRIGGER "trg_update_gen_model_modified_time"
    BEFORE UPDATE
    ON "gen_model"
    FOR EACH ROW
EXECUTE FUNCTION update_modified_time();

-- ----------------------------
-- Table structure for gen_data_source
-- ----------------------------
CREATE TABLE "gen_data_source"
(
    "id"            BIGSERIAL PRIMARY KEY,
    "type"          text        NOT NULL,
    "name"          text        NOT NULL,
    "host"          text        NOT NULL,
    "port"          text        NOT NULL,
    "url_suffix"    text        NOT NULL,
    "username"      text        NOT NULL,
    "password"      text        NOT NULL,
    "order_key"     bigint      NOT NULL DEFAULT 0,
    "created_time"  timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "modified_time" timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "remark"        text        NOT NULL DEFAULT ''
);

COMMENT ON TABLE "gen_data_source" IS '生成数据源';
COMMENT ON COLUMN "gen_data_source"."id" IS 'ID';
COMMENT ON COLUMN "gen_data_source"."type" IS '数据库类型';
COMMENT ON COLUMN "gen_data_source"."name" IS '名称';
COMMENT ON COLUMN "gen_data_source"."host" IS '主机';
COMMENT ON COLUMN "gen_data_source"."port" IS '端口';
COMMENT ON COLUMN "gen_data_source"."url_suffix" IS '链接后缀';
COMMENT ON COLUMN "gen_data_source"."username" IS '用户名';
COMMENT ON COLUMN "gen_data_source"."password" IS '密码';
COMMENT ON COLUMN "gen_data_source"."order_key" IS '自定排序';
COMMENT ON COLUMN "gen_data_source"."created_time" IS '创建时间';
COMMENT ON COLUMN "gen_data_source"."modified_time" IS '修改时间';
COMMENT ON COLUMN "gen_data_source"."remark" IS '备注';

CREATE TRIGGER "trg_update_gen_data_source_modified_time"
    BEFORE UPDATE
    ON "gen_data_source"
    FOR EACH ROW
EXECUTE FUNCTION update_modified_time();

-- ----------------------------
-- Table structure for gen_schema
-- ----------------------------
CREATE TABLE "gen_schema"
(
    "id"             BIGSERIAL PRIMARY KEY,
    "data_source_id" bigint      NOT NULL REFERENCES "gen_data_source" ("id") ON DELETE CASCADE ON UPDATE RESTRICT,
    "name"           text        NOT NULL,
    "order_key"      bigint      NOT NULL DEFAULT 0,
    "created_time"   timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "modified_time"  timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "remark"         text        NOT NULL DEFAULT ''
);

CREATE INDEX "fk_schema_data_source" ON "gen_schema" ("data_source_id");

COMMENT ON TABLE "gen_schema" IS '生成数据架构';
COMMENT ON COLUMN "gen_schema"."id" IS 'ID';
COMMENT ON COLUMN "gen_schema"."data_source_id" IS '数据源 ID';
COMMENT ON COLUMN "gen_schema"."name" IS '名称';
COMMENT ON COLUMN "gen_schema"."order_key" IS '自定排序';
COMMENT ON COLUMN "gen_schema"."created_time" IS '创建时间';
COMMENT ON COLUMN "gen_schema"."modified_time" IS '修改时间';
COMMENT ON COLUMN "gen_schema"."remark" IS '备注';

CREATE TRIGGER "trg_update_gen_schema_modified_time"
    BEFORE UPDATE
    ON "gen_schema"
    FOR EACH ROW
EXECUTE FUNCTION update_modified_time();

-- ----------------------------
-- Table structure for gen_table
-- ----------------------------
CREATE TABLE "gen_table"
(
    "id"            BIGSERIAL PRIMARY KEY,
    "model_id"      bigint      NULL REFERENCES "gen_model" ("id") ON DELETE CASCADE ON UPDATE RESTRICT,
    "schema_id"     bigint      NULL REFERENCES "gen_schema" ("id") ON DELETE CASCADE ON UPDATE RESTRICT,
    "name"          text        NOT NULL,
    "comment"       text        NOT NULL,
    "type"          text        NOT NULL,
    "order_key"     bigint      NOT NULL DEFAULT 0,
    "created_time"  timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "modified_time" timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "remark"        text        NOT NULL DEFAULT ''
);

CREATE INDEX "fk_table_model" ON "gen_table" ("model_id");
CREATE INDEX "fk_table_schema" ON "gen_table" ("schema_id");

COMMENT ON TABLE "gen_table" IS '生成表';
COMMENT ON COLUMN "gen_table"."id" IS 'ID';
COMMENT ON COLUMN "gen_table"."model_id" IS '模型';
COMMENT ON COLUMN "gen_table"."schema_id" IS '数据架构';
COMMENT ON COLUMN "gen_table"."name" IS '表名称';
COMMENT ON COLUMN "gen_table"."comment" IS '表注释';
COMMENT ON COLUMN "gen_table"."type" IS '表种类';
COMMENT ON COLUMN "gen_table"."order_key" IS '自定排序';
COMMENT ON COLUMN "gen_table"."created_time" IS '创建时间';
COMMENT ON COLUMN "gen_table"."modified_time" IS '修改时间';
COMMENT ON COLUMN "gen_table"."remark" IS '备注';

CREATE TRIGGER "trg_update_gen_table_modified_time"
    BEFORE UPDATE
    ON "gen_table"
    FOR EACH ROW
EXECUTE FUNCTION update_modified_time();

-- ----------------------------
-- Table structure for gen_column
-- ----------------------------
CREATE TABLE "gen_column"
(
    "id"                 BIGSERIAL PRIMARY KEY,
    "table_id"           bigint      NOT NULL REFERENCES "gen_table" ("id") ON DELETE CASCADE ON UPDATE RESTRICT,
    "order_key"          bigint      NOT NULL,
    "name"               text        NOT NULL,
    "type_code"          int         NOT NULL,
    "type"               text        NOT NULL,
    "display_size"       bigint      NOT NULL DEFAULT 0,
    "numeric_precision"  bigint      NOT NULL DEFAULT 0,
    "default_value"      text        NULL     DEFAULT NULL,
    "comment"            text        NOT NULL,
    "part_of_pk"         boolean     NOT NULL DEFAULT FALSE,
    "auto_increment"     boolean     NOT NULL DEFAULT FALSE,
    "part_of_fk"         boolean     NOT NULL DEFAULT FALSE,
    "part_of_unique_idx" boolean     NOT NULL DEFAULT FALSE,
    "type_not_null"      boolean     NOT NULL DEFAULT FALSE,
    "business_key"       boolean     NOT NULL DEFAULT FALSE,
    "logical_delete"     boolean     NOT NULL DEFAULT FALSE,
    "enum_id"            bigint      NULL     DEFAULT NULL REFERENCES "gen_enum" ("id") ON DELETE SET NULL ON UPDATE RESTRICT,
    "created_time"       timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "modified_time"      timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "remark"             text        NOT NULL DEFAULT ''
);

CREATE INDEX "fk_column_table" ON "gen_column" ("table_id");
CREATE INDEX "fk_column_enum" ON "gen_column" ("enum_id");

COMMENT ON TABLE "gen_column" IS '生成列';
COMMENT ON COLUMN "gen_column"."id" IS 'ID';
COMMENT ON COLUMN "gen_column"."table_id" IS '归属表';
COMMENT ON COLUMN "gen_column"."order_key" IS '列在表中顺序';
COMMENT ON COLUMN "gen_column"."name" IS '列名称';
COMMENT ON COLUMN "gen_column"."type_code" IS '列对应 JDBCType 码值';
COMMENT ON COLUMN "gen_column"."type" IS '列类型';
COMMENT ON COLUMN "gen_column"."display_size" IS '列展示长度';
COMMENT ON COLUMN "gen_column"."numeric_precision" IS '列精度';
COMMENT ON COLUMN "gen_column"."default_value" IS '列默认值';
COMMENT ON COLUMN "gen_column"."comment" IS '列注释';
COMMENT ON COLUMN "gen_column"."part_of_pk" IS '是否主键';
COMMENT ON COLUMN "gen_column"."auto_increment" IS '是否自增';
COMMENT ON COLUMN "gen_column"."part_of_fk" IS '是否外键';
COMMENT ON COLUMN "gen_column"."part_of_unique_idx" IS '是否唯一索引';
COMMENT ON COLUMN "gen_column"."type_not_null" IS '是否非空';
COMMENT ON COLUMN "gen_column"."business_key" IS '是否为业务键';
COMMENT ON COLUMN "gen_column"."logical_delete" IS '是否为逻辑删除';
COMMENT ON COLUMN "gen_column"."enum_id" IS '枚举';
COMMENT ON COLUMN "gen_column"."created_time" IS '创建时间';
COMMENT ON COLUMN "gen_column"."modified_time" IS '修改时间';
COMMENT ON COLUMN "gen_column"."remark" IS '备注';

CREATE TRIGGER "trg_update_gen_column_modified_time"
    BEFORE UPDATE
    ON "gen_column"
    FOR EACH ROW
EXECUTE FUNCTION update_modified_time();

-- ----------------------------
-- Table structure for gen_association
-- ----------------------------
CREATE TABLE "gen_association"
(
    "id"                BIGSERIAL PRIMARY KEY,
    "model_id"          bigint      NULL REFERENCES "gen_model" ("id") ON DELETE CASCADE ON UPDATE RESTRICT,
    "comment"           text        NOT NULL DEFAULT '',
    "source_column_id"  bigint      NOT NULL REFERENCES "gen_column" ("id") ON DELETE CASCADE ON UPDATE RESTRICT,
    "target_column_id"  bigint      NOT NULL REFERENCES "gen_column" ("id") ON DELETE CASCADE ON UPDATE RESTRICT,
    "association_type"  text        NOT NULL,
    "dissociate_action" text        NULL     DEFAULT NULL,
    "fake"              boolean     NOT NULL DEFAULT TRUE,
    "order_key"         bigint      NOT NULL DEFAULT 0,
    "created_time"      timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "modified_time"     timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "remark"            text        NOT NULL DEFAULT ''
);

CREATE INDEX "fk_association_model" ON "gen_association" ("model_id");
CREATE INDEX "fk_association_source_column" ON "gen_association" ("source_column_id");
CREATE INDEX "fk_association_target_column" ON "gen_association" ("target_column_id");

COMMENT ON TABLE "gen_association" IS '生成关联';
COMMENT ON COLUMN "gen_association"."id" IS 'ID';
COMMENT ON COLUMN "gen_association"."model_id" IS '模型';
COMMENT ON COLUMN "gen_association"."comment" IS '关联注释';
COMMENT ON COLUMN "gen_association"."source_column_id" IS '主列';
COMMENT ON COLUMN "gen_association"."target_column_id" IS '从列';
COMMENT ON COLUMN "gen_association"."association_type" IS '关联类型';
COMMENT ON COLUMN "gen_association"."dissociate_action" IS '脱钩行为';
COMMENT ON COLUMN "gen_association"."fake" IS '是否伪外键';
COMMENT ON COLUMN "gen_association"."order_key" IS '自定排序';
COMMENT ON COLUMN "gen_association"."created_time" IS '创建时间';
COMMENT ON COLUMN "gen_association"."modified_time" IS '修改时间';
COMMENT ON COLUMN "gen_association"."remark" IS '备注';

CREATE TRIGGER "trg_update_gen_association_modified_time"
    BEFORE UPDATE
    ON "gen_association"
    FOR EACH ROW
EXECUTE FUNCTION update_modified_time();

-- ----------------------------
-- Table structure for gen_entity
-- ----------------------------
CREATE TABLE "gen_entity"
(
    "id"            BIGSERIAL PRIMARY KEY,
    "package_id"    bigint      NULL     DEFAULT NULL REFERENCES "gen_package" ("id") ON DELETE SET NULL ON UPDATE RESTRICT,
    "table_id"      bigint      NOT NULL REFERENCES "gen_table" ("id") ON DELETE CASCADE ON UPDATE RESTRICT,
    "name"          text        NOT NULL,
    "comment"       text        NOT NULL,
    "author"        text        NOT NULL DEFAULT '',
    "order_key"     bigint      NOT NULL DEFAULT 0,
    "created_time"  timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "modified_time" timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "remark"        text        NOT NULL DEFAULT ''
);

CREATE UNIQUE INDEX "u_entity_table" ON "gen_entity" ("table_id");
CREATE INDEX "fk_entity_package" ON "gen_entity" ("package_id");
CREATE INDEX "fk_entity_table" ON "gen_entity" ("table_id");

COMMENT ON TABLE "gen_entity" IS '生成实体';
COMMENT ON COLUMN "gen_entity"."id" IS 'ID';
COMMENT ON COLUMN "gen_entity"."package_id" IS '所属包';
COMMENT ON COLUMN "gen_entity"."table_id" IS '对应表';
COMMENT ON COLUMN "gen_entity"."name" IS '类名称';
COMMENT ON COLUMN "gen_entity"."comment" IS '类注释';
COMMENT ON COLUMN "gen_entity"."author" IS '作者';
COMMENT ON COLUMN "gen_entity"."order_key" IS '自定排序';
COMMENT ON COLUMN "gen_entity"."created_time" IS '创建时间';
COMMENT ON COLUMN "gen_entity"."modified_time" IS '修改时间';
COMMENT ON COLUMN "gen_entity"."remark" IS '备注';

CREATE TRIGGER "trg_update_gen_entity_modified_time"
    BEFORE UPDATE
    ON "gen_entity"
    FOR EACH ROW
EXECUTE FUNCTION update_modified_time();

-- ----------------------------
-- Table structure for gen_property
-- ----------------------------
CREATE TABLE "gen_property"
(
    "id"                     BIGSERIAL PRIMARY KEY,
    "entity_id"              bigint      NOT NULL REFERENCES "gen_entity" ("id") ON DELETE CASCADE ON UPDATE RESTRICT,
    "column_id"              bigint      NULL     DEFAULT NULL REFERENCES "gen_column" ("id") ON DELETE SET NULL ON UPDATE RESTRICT,
    "name"                   text        NOT NULL,
    "comment"                text        NOT NULL,
    "type"                   text        NOT NULL,
    "type_table_id"          bigint      NULL     DEFAULT NULL REFERENCES "gen_table" ("id") ON DELETE SET NULL ON UPDATE RESTRICT,
    "list_type"              boolean     NOT NULL DEFAULT FALSE,
    "type_not_null"          boolean     NOT NULL DEFAULT FALSE,
    "id_property"            boolean     NOT NULL DEFAULT FALSE,
    "id_generation_type"     text        NULL     DEFAULT NULL,
    "key_property"           boolean     NOT NULL DEFAULT FALSE,
    "logical_delete"         boolean     NOT NULL DEFAULT FALSE,
    "id_view"                boolean     NOT NULL DEFAULT FALSE,
    "id_view_annotation"     text        NULL     DEFAULT NULL,
    "association_type"       text        NULL     DEFAULT NULL,
    "association_annotation" text        NULL     DEFAULT NULL,
    "dissociate_annotation"  text        NULL     DEFAULT NULL,
    "other_annotation"       text        NULL     DEFAULT NULL,
    "enum_id"                bigint      NULL     DEFAULT NULL REFERENCES "gen_enum" ("id") ON DELETE SET NULL ON UPDATE RESTRICT,
    "order_key"              bigint      NOT NULL DEFAULT 0,
    "created_time"           timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "modified_time"          timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "remark"                 text        NOT NULL DEFAULT ''
);

CREATE INDEX "fk_property_column" ON "gen_property" ("column_id");
CREATE INDEX "fk_property_entity" ON "gen_property" ("entity_id");
CREATE INDEX "fk_property_enum" ON "gen_property" ("enum_id");
CREATE INDEX "fk_property_type_table" ON "gen_property" ("type_table_id");

COMMENT ON TABLE "gen_property" IS '生成属性';
COMMENT ON COLUMN "gen_property"."id" IS 'ID';
COMMENT ON COLUMN "gen_property"."entity_id" IS '归属实体';
COMMENT ON COLUMN "gen_property"."column_id" IS '对应列';
COMMENT ON COLUMN "gen_property"."name" IS '属性名';
COMMENT ON COLUMN "gen_property"."comment" IS '属性注释';
COMMENT ON COLUMN "gen_property"."type" IS '属性类型';
COMMENT ON COLUMN "gen_property"."type_table_id" IS '类型对应表';
COMMENT ON COLUMN "gen_property"."list_type" IS '是否列表';
COMMENT ON COLUMN "gen_property"."type_not_null" IS '是否非空';
COMMENT ON COLUMN "gen_property"."id_property" IS '是否Id';
COMMENT ON COLUMN "gen_property"."id_generation_type" IS 'Id 生成类型';
COMMENT ON COLUMN "gen_property"."key_property" IS '是否为业务键属性';
COMMENT ON COLUMN "gen_property"."logical_delete" IS '是否为逻辑删除属性';
COMMENT ON COLUMN "gen_property"."id_view" IS '是否为 ID 视图属性';
COMMENT ON COLUMN "gen_property"."id_view_annotation" IS 'ID 视图注释';
COMMENT ON COLUMN "gen_property"."association_type" IS '关联类型';
COMMENT ON COLUMN "gen_property"."association_annotation" IS '关联注释';
COMMENT ON COLUMN "gen_property"."dissociate_annotation" IS '脱钩注释';
COMMENT ON COLUMN "gen_property"."other_annotation" IS '其他注释';
COMMENT ON COLUMN "gen_property"."enum_id" IS '对应枚举 ID';
COMMENT ON COLUMN "gen_property"."order_key" IS '自定排序';
COMMENT ON COLUMN "gen_property"."created_time" IS '创建时间';
COMMENT ON COLUMN "gen_property"."modified_time" IS '修改时间';
COMMENT ON COLUMN "gen_property"."remark" IS '备注';

CREATE TRIGGER "trg_update_gen_property_modified_time"
    BEFORE UPDATE
    ON "gen_property"
    FOR EACH ROW
EXECUTE FUNCTION update_modified_time();

-- ----------------------------
-- Table structure for gen_type_mapping
-- ----------------------------
CREATE TABLE "gen_type_mapping"
(
    "id"              BIGSERIAL PRIMARY KEY,
    "data_source_type" text NOT NULL,
    "type_expression" text        NOT NULL,
    "language"        text        NOT NULL,
    "property_type"   text        NOT NULL,
    "order_key"       bigint      NOT NULL DEFAULT 0,
    "created_time"    timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "modified_time"   timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "remark"          text        NOT NULL DEFAULT ''
);

COMMENT ON TABLE "gen_type_mapping" IS '列到属性类型映射';
COMMENT ON COLUMN "gen_type_mapping"."id" IS 'ID';
COMMENT ON COLUMN "gen_type_mapping"."data_source_type" IS '数据源类型';
COMMENT ON COLUMN "gen_type_mapping"."type_expression" IS '数据库类型表达式';
COMMENT ON COLUMN "gen_type_mapping"."language" IS '语言';
COMMENT ON COLUMN "gen_type_mapping"."property_type" IS '属性类型';
COMMENT ON COLUMN "gen_type_mapping"."order_key" IS '自定排序';
COMMENT ON COLUMN "gen_type_mapping"."created_time" IS '创建时间';
COMMENT ON COLUMN "gen_type_mapping"."modified_time" IS '修改时间';
COMMENT ON COLUMN "gen_type_mapping"."remark" IS '备注';

CREATE TRIGGER "trg_update_gen_type_mapping_modified_time"
    BEFORE UPDATE
    ON "gen_type_mapping"
    FOR EACH ROW
EXECUTE FUNCTION update_modified_time();
