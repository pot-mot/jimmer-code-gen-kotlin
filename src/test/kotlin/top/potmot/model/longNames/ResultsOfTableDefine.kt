package top.potmot.model.longNames

const val mysqlUpperResult = """
[(all-tables.sql, DROP TABLE IF EXISTS `SDAADSDASSDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD_BB0D4D2B`;

CREATE TABLE `SDAADSDASSDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD_BB0D4D2B` (
    `SDAADSDASSDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD_BB0D4D2B` BIGINT NOT NULL AUTO_INCREMENT,
    `SDAADSDASSDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD_D409B7E8` VARCHAR(500) NOT NULL,
    PRIMARY KEY (`SDAADSDASSDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD_BB0D4D2B`)
)
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COMMENT = ''
  ROW_FORMAT = Dynamic;

CREATE UNIQUE INDEX `UIDX_FIRST_COLUMN` ON `SDAADSDASSDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD_BB0D4D2B` (`SDAADSDASSDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD_D409B7E8`);
CREATE INDEX `IDX_MULTI_COLUMNS` ON `SDAADSDASSDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD_BB0D4D2B` (`SDAADSDASSDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD_BB0D4D2B`, `SDAADSDASSDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD_D409B7E8`);

), (sdaadsdassddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd.sql, DROP TABLE IF EXISTS `SDAADSDASSDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD_BB0D4D2B`;

CREATE TABLE `SDAADSDASSDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD_BB0D4D2B` (
    `SDAADSDASSDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD_BB0D4D2B` BIGINT NOT NULL AUTO_INCREMENT,
    `SDAADSDASSDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD_D409B7E8` VARCHAR(500) NOT NULL,
    PRIMARY KEY (`SDAADSDASSDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD_BB0D4D2B`)
)
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COMMENT = ''
  ROW_FORMAT = Dynamic;

CREATE UNIQUE INDEX `UIDX_FIRST_COLUMN` ON `SDAADSDASSDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD_BB0D4D2B` (`SDAADSDASSDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD_D409B7E8`);
CREATE INDEX `IDX_MULTI_COLUMNS` ON `SDAADSDASSDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD_BB0D4D2B` (`SDAADSDASSDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD_BB0D4D2B`, `SDAADSDASSDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD_D409B7E8`);

)]
"""

const val mysqlLowerResult = """
[(all-tables.sql, DROP TABLE IF EXISTS `sdaadsdassdddddddddddddddddddddddddddddddddddddddddddd_bb0d4d2b`;

CREATE TABLE `sdaadsdassdddddddddddddddddddddddddddddddddddddddddddd_bb0d4d2b` (
    `sdaadsdassdddddddddddddddddddddddddddddddddddddddddddd_bb0d4d2b` BIGINT NOT NULL AUTO_INCREMENT,
    `sdaadsdassdddddddddddddddddddddddddddddddddddddddddddd_d409b7e8` VARCHAR(500) NOT NULL,
    PRIMARY KEY (`sdaadsdassdddddddddddddddddddddddddddddddddddddddddddd_bb0d4d2b`)
)
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COMMENT = ''
  ROW_FORMAT = Dynamic;

CREATE UNIQUE INDEX `uidx_first_column` ON `sdaadsdassdddddddddddddddddddddddddddddddddddddddddddd_bb0d4d2b` (`sdaadsdassdddddddddddddddddddddddddddddddddddddddddddd_d409b7e8`);
CREATE INDEX `idx_multi_columns` ON `sdaadsdassdddddddddddddddddddddddddddddddddddddddddddd_bb0d4d2b` (`sdaadsdassdddddddddddddddddddddddddddddddddddddddddddd_bb0d4d2b`, `sdaadsdassdddddddddddddddddddddddddddddddddddddddddddd_d409b7e8`);

), (sdaadsdassddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd.sql, DROP TABLE IF EXISTS `sdaadsdassdddddddddddddddddddddddddddddddddddddddddddd_bb0d4d2b`;

CREATE TABLE `sdaadsdassdddddddddddddddddddddddddddddddddddddddddddd_bb0d4d2b` (
    `sdaadsdassdddddddddddddddddddddddddddddddddddddddddddd_bb0d4d2b` BIGINT NOT NULL AUTO_INCREMENT,
    `sdaadsdassdddddddddddddddddddddddddddddddddddddddddddd_d409b7e8` VARCHAR(500) NOT NULL,
    PRIMARY KEY (`sdaadsdassdddddddddddddddddddddddddddddddddddddddddddd_bb0d4d2b`)
)
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COMMENT = ''
  ROW_FORMAT = Dynamic;

CREATE UNIQUE INDEX `uidx_first_column` ON `sdaadsdassdddddddddddddddddddddddddddddddddddddddddddd_bb0d4d2b` (`sdaadsdassdddddddddddddddddddddddddddddddddddddddddddd_d409b7e8`);
CREATE INDEX `idx_multi_columns` ON `sdaadsdassdddddddddddddddddddddddddddddddddddddddddddd_bb0d4d2b` (`sdaadsdassdddddddddddddddddddddddddddddddddddddddddddd_bb0d4d2b`, `sdaadsdassdddddddddddddddddddddddddddddddddddddddddddd_d409b7e8`);

)]
"""

const val postgresUpperResult = """
[(all-tables.sql, DROP TABLE IF EXISTS "SDAADSDASSDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD_BB0D4D2B" CASCADE;

CREATE TABLE "SDAADSDASSDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD_BB0D4D2B" (
    "SDAADSDASSDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD_BB0D4D2B" BIGSERIAL NOT NULL,
    "SDAADSDASSDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD_D409B7E8" TEXT NOT NULL,
    PRIMARY KEY ("SDAADSDASSDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD_BB0D4D2B")
);

CREATE UNIQUE INDEX "UIDX_FIRST_COLUMN" ON "SDAADSDASSDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD_BB0D4D2B" ("SDAADSDASSDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD_D409B7E8");
CREATE INDEX "IDX_MULTI_COLUMNS" ON "SDAADSDASSDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD_BB0D4D2B" ("SDAADSDASSDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD_BB0D4D2B", "SDAADSDASSDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD_D409B7E8");

), (sdaadsdassddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd.sql, DROP TABLE IF EXISTS "SDAADSDASSDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD_BB0D4D2B" CASCADE;

CREATE TABLE "SDAADSDASSDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD_BB0D4D2B" (
    "SDAADSDASSDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD_BB0D4D2B" BIGSERIAL NOT NULL,
    "SDAADSDASSDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD_D409B7E8" TEXT NOT NULL,
    PRIMARY KEY ("SDAADSDASSDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD_BB0D4D2B")
);

CREATE UNIQUE INDEX "UIDX_FIRST_COLUMN" ON "SDAADSDASSDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD_BB0D4D2B" ("SDAADSDASSDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD_D409B7E8");
CREATE INDEX "IDX_MULTI_COLUMNS" ON "SDAADSDASSDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD_BB0D4D2B" ("SDAADSDASSDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD_BB0D4D2B", "SDAADSDASSDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD_D409B7E8");

)]
"""

const val postgresLowerResult = """
[(all-tables.sql, DROP TABLE IF EXISTS "sdaadsdassdddddddddddddddddddddddddddddddddddddddddddd_bb0d4d2b" CASCADE;

CREATE TABLE "sdaadsdassdddddddddddddddddddddddddddddddddddddddddddd_bb0d4d2b" (
    "sdaadsdassdddddddddddddddddddddddddddddddddddddddddddd_bb0d4d2b" BIGSERIAL NOT NULL,
    "sdaadsdassdddddddddddddddddddddddddddddddddddddddddddd_d409b7e8" TEXT NOT NULL,
    PRIMARY KEY ("sdaadsdassdddddddddddddddddddddddddddddddddddddddddddd_bb0d4d2b")
);

CREATE UNIQUE INDEX "uidx_first_column" ON "sdaadsdassdddddddddddddddddddddddddddddddddddddddddddd_bb0d4d2b" ("sdaadsdassdddddddddddddddddddddddddddddddddddddddddddd_d409b7e8");
CREATE INDEX "idx_multi_columns" ON "sdaadsdassdddddddddddddddddddddddddddddddddddddddddddd_bb0d4d2b" ("sdaadsdassdddddddddddddddddddddddddddddddddddddddddddd_bb0d4d2b", "sdaadsdassdddddddddddddddddddddddddddddddddddddddddddd_d409b7e8");

), (sdaadsdassddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd.sql, DROP TABLE IF EXISTS "sdaadsdassdddddddddddddddddddddddddddddddddddddddddddd_bb0d4d2b" CASCADE;

CREATE TABLE "sdaadsdassdddddddddddddddddddddddddddddddddddddddddddd_bb0d4d2b" (
    "sdaadsdassdddddddddddddddddddddddddddddddddddddddddddd_bb0d4d2b" BIGSERIAL NOT NULL,
    "sdaadsdassdddddddddddddddddddddddddddddddddddddddddddd_d409b7e8" TEXT NOT NULL,
    PRIMARY KEY ("sdaadsdassdddddddddddddddddddddddddddddddddddddddddddd_bb0d4d2b")
);

CREATE UNIQUE INDEX "uidx_first_column" ON "sdaadsdassdddddddddddddddddddddddddddddddddddddddddddd_bb0d4d2b" ("sdaadsdassdddddddddddddddddddddddddddddddddddddddddddd_d409b7e8");
CREATE INDEX "idx_multi_columns" ON "sdaadsdassdddddddddddddddddddddddddddddddddddddddddddd_bb0d4d2b" ("sdaadsdassdddddddddddddddddddddddddddddddddddddddddddd_bb0d4d2b", "sdaadsdassdddddddddddddddddddddddddddddddddddddddddddd_d409b7e8");

)]
"""
