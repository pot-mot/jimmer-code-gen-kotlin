-- FIX input_not_null type
ALTER TABLE "jimmer_code_gen"."gen_property"
    ADD COLUMN "temp_input_not_null" boolean NULL DEFAULT NULL;

UPDATE "jimmer_code_gen"."gen_property"
SET "temp_input_not_null" = CASE
                                WHEN "input_not_null" = 0 THEN FALSE
                                ELSE TRUE
    END;

ALTER TABLE "jimmer_code_gen"."gen_property"
    DROP COLUMN "input_not_null";

ALTER TABLE "jimmer_code_gen"."gen_property"
    RENAME COLUMN "temp_input_not_null" TO "input_not_null";