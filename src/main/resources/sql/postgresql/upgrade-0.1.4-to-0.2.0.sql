ALTER TABLE "jimmer_code_gen"."gen_column"
    ADD COLUMN "key_group" varchar(500) NULL DEFAULT NULL;

COMMENT ON COLUMN "gen_column"."key_group" IS '业务键组';


ALTER TABLE "jimmer_code_gen"."gen_property"
    ADD COLUMN "key_group" varchar(500) NULL DEFAULT NULL;

COMMENT ON COLUMN "gen_property"."key_group" IS '业务键组';


ALTER TABLE "jimmer_code_gen"."gen_property"
    ADD COLUMN "long_association" boolean NOT NULL DEFAULT FALSE;
ALTER TABLE "jimmer_code_gen"."gen_property"
    ALTER COLUMN "long_association" DROP DEFAULT;

COMMENT ON COLUMN "gen_property"."long_association" IS '是否为长关联';



ALTER TABLE "jimmer_code_gen"."gen_entity"
    ADD COLUMN "can_add" boolean NOT NULL DEFAULT TRUE;
ALTER TABLE "jimmer_code_gen"."gen_entity"
    ALTER COLUMN "can_add" DROP DEFAULT;

COMMENT ON COLUMN "gen_entity"."can_add" IS '是否可以创建';

ALTER TABLE "jimmer_code_gen"."gen_entity"
    ADD COLUMN "can_edit" boolean NOT NULL DEFAULT TRUE;
ALTER TABLE "jimmer_code_gen"."gen_entity"
    ALTER COLUMN "can_edit" DROP DEFAULT;

COMMENT ON COLUMN "gen_entity"."can_edit" IS '是否可以修改';

ALTER TABLE "jimmer_code_gen"."gen_entity"
    ADD COLUMN "can_delete" boolean NOT NULL DEFAULT TRUE;
ALTER TABLE "jimmer_code_gen"."gen_entity"
    ALTER COLUMN "can_delete" DROP DEFAULT;

COMMENT ON COLUMN "gen_entity"."can_delete" IS '是否可以删除';

ALTER TABLE "jimmer_code_gen"."gen_entity"
    ADD COLUMN "can_query" boolean NOT NULL DEFAULT TRUE;
ALTER TABLE "jimmer_code_gen"."gen_entity"
    ALTER COLUMN "can_query" DROP DEFAULT;

COMMENT ON COLUMN "gen_entity"."can_query" IS '是否可以查询';

ALTER TABLE "jimmer_code_gen"."gen_entity"
    ADD COLUMN "has_page" boolean NOT NULL DEFAULT TRUE;
ALTER TABLE "jimmer_code_gen"."gen_entity"
    ALTER COLUMN "has_page" DROP DEFAULT;

COMMENT ON COLUMN "gen_entity"."has_page" IS '是否具有页面';



ALTER TABLE "jimmer_code_gen"."gen_property"
    ADD COLUMN "in_list_view" boolean NOT NULL DEFAULT TRUE;
ALTER TABLE "jimmer_code_gen"."gen_property"
    ALTER COLUMN "in_list_view" DROP DEFAULT;

COMMENT ON COLUMN "gen_property"."in_list_view" IS '是否在列表视图DTO中';

ALTER TABLE "jimmer_code_gen"."gen_property"
    ADD COLUMN "in_detail_view" boolean NOT NULL DEFAULT TRUE;
ALTER TABLE "jimmer_code_gen"."gen_property"
    ALTER COLUMN "in_detail_view" DROP DEFAULT;

COMMENT ON COLUMN "gen_property"."in_detail_view" IS '是否在详情视图DTO中';

ALTER TABLE "jimmer_code_gen"."gen_property"
    ADD COLUMN "in_insert_input" boolean NOT NULL DEFAULT TRUE;
ALTER TABLE "jimmer_code_gen"."gen_property"
    ALTER COLUMN "in_insert_input" DROP DEFAULT;

COMMENT ON COLUMN "gen_property"."in_insert_input" IS '是否在新增入参DTO中';

ALTER TABLE "jimmer_code_gen"."gen_property"
    ADD COLUMN "in_update_input" boolean NOT NULL DEFAULT TRUE;
ALTER TABLE "jimmer_code_gen"."gen_property"
    ALTER COLUMN "in_update_input" DROP DEFAULT;

COMMENT ON COLUMN "gen_property"."in_update_input" IS '是否在修改入参DTO中';

ALTER TABLE "jimmer_code_gen"."gen_property"
    ADD COLUMN "in_specification" boolean NOT NULL DEFAULT TRUE;
ALTER TABLE "jimmer_code_gen"."gen_property"
    ALTER COLUMN "in_specification" DROP DEFAULT;

COMMENT ON COLUMN "gen_property"."in_specification" IS '是否在查询规格DTO中';

ALTER TABLE "jimmer_code_gen"."gen_property"
    ADD COLUMN "in_long_association_view" boolean NOT NULL DEFAULT TRUE;
ALTER TABLE "jimmer_code_gen"."gen_property"
    ALTER COLUMN "in_long_association_view" DROP DEFAULT;

COMMENT ON COLUMN "gen_property"."in_long_association_view" IS '是否在长关联视图DTO中';

ALTER TABLE "jimmer_code_gen"."gen_property"
    ADD COLUMN "in_long_association_input" boolean NOT NULL DEFAULT TRUE;
ALTER TABLE "jimmer_code_gen"."gen_property"
    ALTER COLUMN "in_long_association_input" DROP DEFAULT;

COMMENT ON COLUMN "gen_property"."in_long_association_input" IS '是否在长关联入参DTO中';



ALTER TABLE "jimmer_code_gen"."gen_property"
    ADD COLUMN "in_option_view" boolean NOT NULL DEFAULT FALSE;
ALTER TABLE "jimmer_code_gen"."gen_property"
    ALTER COLUMN "in_option_view" DROP DEFAULT;

COMMENT ON COLUMN "gen_property"."in_option_view" IS '是否在选项视图DTO中';

ALTER TABLE "jimmer_code_gen"."gen_property"
    ADD COLUMN "in_short_association_view" boolean NOT NULL DEFAULT FALSE;
ALTER TABLE "jimmer_code_gen"."gen_property"
    ALTER COLUMN "in_short_association_view" DROP DEFAULT;

COMMENT ON COLUMN "gen_property"."in_short_association_view" IS '是否在短关联视图DTO中';


ALTER TABLE "jimmer_code_gen"."gen_enum_item"
    ADD COLUMN "default_item" boolean NOT NULL DEFAULT FALSE;
ALTER TABLE "jimmer_code_gen"."gen_enum_item"
    ALTER COLUMN "default_item" DROP DEFAULT;

COMMENT ON COLUMN "gen_enum_item"."default_item" IS '是否是默认值';



ALTER TABLE "jimmer_code_gen"."gen_property"
    ADD COLUMN "overwrite_name" boolean NOT NULL DEFAULT FALSE;
ALTER TABLE "jimmer_code_gen"."gen_property"
    ALTER COLUMN "overwrite_name" DROP DEFAULT;

COMMENT ON COLUMN "gen_property"."overwrite_name" IS '覆盖自动生成属性名';

ALTER TABLE "jimmer_code_gen"."gen_property"
    ADD COLUMN "overwrite_comment" boolean NOT NULL DEFAULT FALSE;
ALTER TABLE "jimmer_code_gen"."gen_property"
    ALTER COLUMN "overwrite_comment" DROP DEFAULT;

COMMENT ON COLUMN "gen_property"."overwrite_comment" IS '覆盖自动生成注释';



ALTER TABLE "jimmer_code_gen"."gen_entity"
    ADD COLUMN "overwrite_name" boolean NOT NULL DEFAULT FALSE;
ALTER TABLE "jimmer_code_gen"."gen_entity"
    ALTER COLUMN "overwrite_name" DROP DEFAULT;

COMMENT ON COLUMN "gen_entity"."overwrite_name" IS '覆盖自动生成类名称';

ALTER TABLE "jimmer_code_gen"."gen_entity"
    ADD COLUMN "overwrite_comment" boolean NOT NULL DEFAULT FALSE;
ALTER TABLE "jimmer_code_gen"."gen_entity"
    ALTER COLUMN "overwrite_comment" DROP DEFAULT;

COMMENT ON COLUMN "gen_entity"."overwrite_comment" IS '覆盖自动生成注释';



ALTER TABLE "jimmer_code_gen"."gen_property"
    ADD COLUMN "body" text NULL DEFAULT NULL;

COMMENT ON COLUMN "gen_property"."body" IS '函数方法体';


ALTER TABLE "jimmer_code_gen"."gen_entity"
    ADD COLUMN "other_annotation" text NULL DEFAULT NULL;

COMMENT ON COLUMN "gen_entity"."other_annotation" IS '其他注解';