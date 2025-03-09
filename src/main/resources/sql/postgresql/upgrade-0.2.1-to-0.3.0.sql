ALTER TABLE gen_enum
    ADD COLUMN sub_group_id BIGINT DEFAULT NULL;

COMMENT ON COLUMN "gen_enum"."sub_group_id" IS '子组';


ALTER TABLE gen_table
    ADD COLUMN sub_group_id BIGINT DEFAULT NULL;

COMMENT ON COLUMN "gen_table"."sub_group_id" IS '子组';


CREATE TABLE "gen_model_sub_group"
(
    "id"               BIGSERIAL   NOT NULL,
    "model_id"         BIGINT      NOT NULL,
    "name"             TEXT        NOT NULL,
    "comment"          TEXT        NOT NULL,
    "sub_package_path" TEXT        NOT NULL,
    "style"            TEXT        NOT NULL,
    "created_time"     timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "modified_time"    timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY ("id")
);

COMMENT ON TABLE "gen_model_sub_group" IS '生成模型子组';
COMMENT ON COLUMN "gen_model_sub_group"."id" IS 'ID';
COMMENT ON COLUMN "gen_model_sub_group"."model_id" IS '模型';
COMMENT ON COLUMN "gen_model_sub_group"."name" IS '名称';
COMMENT ON COLUMN "gen_model_sub_group"."comment" IS '注释';
COMMENT ON COLUMN "gen_model_sub_group"."sub_package_path" IS '子包路径';
COMMENT ON COLUMN "gen_model_sub_group"."style" IS '样式';

ALTER TABLE "gen_model_sub_group"
    ADD CONSTRAINT "fk_gen_model_sub_group_model_id"
        FOREIGN KEY ("model_id")
            REFERENCES "gen_model" ("id");

ALTER TABLE "gen_enum"
    ADD CONSTRAINT "fk_gen_enum_sub_group_id"
        FOREIGN KEY ("sub_group_id")
            REFERENCES "gen_model_sub_group" ("id");

ALTER TABLE "gen_table"
    ADD CONSTRAINT "fk_gen_table_sub_group_id"
        FOREIGN KEY ("sub_group_id")
            REFERENCES "gen_model_sub_group" ("id");

CREATE TRIGGER "trg_update_gen_model_sub_group_default_modified_time"
    BEFORE UPDATE
    ON "gen_model_sub_group"
    FOR EACH ROW
EXECUTE FUNCTION update_modified_time();