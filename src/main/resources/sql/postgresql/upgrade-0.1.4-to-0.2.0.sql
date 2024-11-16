ALTER TABLE "jimmer_code_gen"."gen_property"
    ADD COLUMN "key_group" varchar (500) NULL DEFAULT NULL;

COMMENT ON COLUMN "gen_property"."key_group" IS '业务键组';


ALTER TABLE "jimmer_code_gen"."gen_property"
    ADD COLUMN "long_association" boolean NOT NULL DEFAULT FALSE;

ALTER TABLE "jimmer_code_gen"."gen_property"
    ALTER COLUMN "long_association" DROP DEFAULT;

COMMENT ON COLUMN "gen_property"."long_association" IS '是否为长关联';


