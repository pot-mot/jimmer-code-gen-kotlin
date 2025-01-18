package top.potmot.model.superTable

const val mysqlResult = """
[(ddl/all-tables.sql, DROP TABLE IF EXISTS `BIZ_PROJECT`;
DROP TABLE IF EXISTS `SYS_USER`;

CREATE TABLE `BIZ_PROJECT` (
    `ID` INT NOT NULL AUTO_INCREMENT,
    `USER` INT NOT NULL COMMENT '用户',
    `NAME` VARCHAR(255) NOT NULL COMMENT '名称',
    `CREATED_BY` INT NOT NULL COMMENT '创建者',
    `MODIFIED_BY` INT NOT NULL COMMENT '修改者',
    PRIMARY KEY (`ID`)
)
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COMMENT = '项目'
  ROW_FORMAT = Dynamic;

CREATE TABLE `SYS_USER` (
    `ID` INT NOT NULL AUTO_INCREMENT,
    `NAME` VARCHAR(255) NOT NULL COMMENT '名称',
    `CREATED_BY` INT NOT NULL COMMENT '创建者',
    `MODIFIED_BY` INT NOT NULL COMMENT '修改者',
    PRIMARY KEY (`ID`)
)
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COMMENT = '用户'
  ROW_FORMAT = Dynamic;

ALTER TABLE `BIZ_PROJECT`
    ADD CONSTRAINT `FK_BIZ_PROJECT_SYS_USER`
        FOREIGN KEY (`USER`)
            REFERENCES `SYS_USER` (`ID`);

ALTER TABLE `BIZ_PROJECT`
    ADD CONSTRAINT `FK_BIZ_PROJECT_CREATED_BY`
        FOREIGN KEY (`CREATED_BY`)
            REFERENCES `SYS_USER` (`ID`);

ALTER TABLE `BIZ_PROJECT`
    ADD CONSTRAINT `FK_BIZ_PROJECT_MODIFIED_BY`
        FOREIGN KEY (`MODIFIED_BY`)
            REFERENCES `SYS_USER` (`ID`);

ALTER TABLE `SYS_USER`
    ADD CONSTRAINT `FK_SYS_USER_CREATED_BY`
        FOREIGN KEY (`CREATED_BY`)
            REFERENCES `SYS_USER` (`ID`);

ALTER TABLE `SYS_USER`
    ADD CONSTRAINT `FK_SYS_USER_MODIFIED_BY`
        FOREIGN KEY (`MODIFIED_BY`)
            REFERENCES `SYS_USER` (`ID`);

), (ddl/BIZ_PROJECT.sql, DROP TABLE IF EXISTS `BIZ_PROJECT`;

CREATE TABLE `BIZ_PROJECT` (
    `ID` INT NOT NULL AUTO_INCREMENT,
    `USER` INT NOT NULL COMMENT '用户',
    `NAME` VARCHAR(255) NOT NULL COMMENT '名称',
    `CREATED_BY` INT NOT NULL COMMENT '创建者',
    `MODIFIED_BY` INT NOT NULL COMMENT '修改者',
    PRIMARY KEY (`ID`)
)
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COMMENT = '项目'
  ROW_FORMAT = Dynamic;

ALTER TABLE `BIZ_PROJECT`
    ADD CONSTRAINT `FK_BIZ_PROJECT_SYS_USER`
        FOREIGN KEY (`USER`)
            REFERENCES `SYS_USER` (`ID`);

ALTER TABLE `BIZ_PROJECT`
    ADD CONSTRAINT `FK_BIZ_PROJECT_CREATED_BY`
        FOREIGN KEY (`CREATED_BY`)
            REFERENCES `SYS_USER` (`ID`);

ALTER TABLE `BIZ_PROJECT`
    ADD CONSTRAINT `FK_BIZ_PROJECT_MODIFIED_BY`
        FOREIGN KEY (`MODIFIED_BY`)
            REFERENCES `SYS_USER` (`ID`);

), (ddl/SYS_USER.sql, DROP TABLE IF EXISTS `SYS_USER`;

CREATE TABLE `SYS_USER` (
    `ID` INT NOT NULL AUTO_INCREMENT,
    `NAME` VARCHAR(255) NOT NULL COMMENT '名称',
    `CREATED_BY` INT NOT NULL COMMENT '创建者',
    `MODIFIED_BY` INT NOT NULL COMMENT '修改者',
    PRIMARY KEY (`ID`)
)
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COMMENT = '用户'
  ROW_FORMAT = Dynamic;

ALTER TABLE `SYS_USER`
    ADD CONSTRAINT `FK_SYS_USER_CREATED_BY`
        FOREIGN KEY (`CREATED_BY`)
            REFERENCES `SYS_USER` (`ID`);

ALTER TABLE `SYS_USER`
    ADD CONSTRAINT `FK_SYS_USER_MODIFIED_BY`
        FOREIGN KEY (`MODIFIED_BY`)
            REFERENCES `SYS_USER` (`ID`);

ALTER TABLE `BIZ_PROJECT`
    ADD CONSTRAINT `FK_BIZ_PROJECT_SYS_USER`
        FOREIGN KEY (`USER`)
            REFERENCES `SYS_USER` (`ID`);

ALTER TABLE `BIZ_PROJECT`
    ADD CONSTRAINT `FK_BIZ_PROJECT_CREATED_BY`
        FOREIGN KEY (`CREATED_BY`)
            REFERENCES `SYS_USER` (`ID`);

ALTER TABLE `BIZ_PROJECT`
    ADD CONSTRAINT `FK_BIZ_PROJECT_MODIFIED_BY`
        FOREIGN KEY (`MODIFIED_BY`)
            REFERENCES `SYS_USER` (`ID`);

)]
"""

const val postgresResult = """
[(ddl/all-tables.sql, DROP TABLE IF EXISTS "BIZ_PROJECT" CASCADE;
DROP TABLE IF EXISTS "SYS_USER" CASCADE;

CREATE TABLE "BIZ_PROJECT" (
    "ID" SERIAL NOT NULL,
    "USER" INT NOT NULL,
    "NAME" TEXT NOT NULL,
    "CREATED_BY" INT NOT NULL,
    "MODIFIED_BY" INT NOT NULL,
    PRIMARY KEY ("ID")
);

COMMENT ON TABLE "BIZ_PROJECT" IS '项目';
COMMENT ON COLUMN "BIZ_PROJECT"."USER" IS '用户';
COMMENT ON COLUMN "BIZ_PROJECT"."NAME" IS '名称';
COMMENT ON COLUMN "BIZ_PROJECT"."CREATED_BY" IS '创建者';
COMMENT ON COLUMN "BIZ_PROJECT"."MODIFIED_BY" IS '修改者';

CREATE TABLE "SYS_USER" (
    "ID" SERIAL NOT NULL,
    "NAME" TEXT NOT NULL,
    "CREATED_BY" INT NOT NULL,
    "MODIFIED_BY" INT NOT NULL,
    PRIMARY KEY ("ID")
);

COMMENT ON TABLE "SYS_USER" IS '用户';
COMMENT ON COLUMN "SYS_USER"."NAME" IS '名称';
COMMENT ON COLUMN "SYS_USER"."CREATED_BY" IS '创建者';
COMMENT ON COLUMN "SYS_USER"."MODIFIED_BY" IS '修改者';

ALTER TABLE "BIZ_PROJECT"
    ADD CONSTRAINT "FK_BIZ_PROJECT_SYS_USER"
        FOREIGN KEY ("USER")
            REFERENCES "SYS_USER" ("ID");

ALTER TABLE "BIZ_PROJECT"
    ADD CONSTRAINT "FK_BIZ_PROJECT_CREATED_BY"
        FOREIGN KEY ("CREATED_BY")
            REFERENCES "SYS_USER" ("ID");

ALTER TABLE "BIZ_PROJECT"
    ADD CONSTRAINT "FK_BIZ_PROJECT_MODIFIED_BY"
        FOREIGN KEY ("MODIFIED_BY")
            REFERENCES "SYS_USER" ("ID");

ALTER TABLE "SYS_USER"
    ADD CONSTRAINT "FK_SYS_USER_CREATED_BY"
        FOREIGN KEY ("CREATED_BY")
            REFERENCES "SYS_USER" ("ID");

ALTER TABLE "SYS_USER"
    ADD CONSTRAINT "FK_SYS_USER_MODIFIED_BY"
        FOREIGN KEY ("MODIFIED_BY")
            REFERENCES "SYS_USER" ("ID");

), (ddl/BIZ_PROJECT.sql, DROP TABLE IF EXISTS "BIZ_PROJECT" CASCADE;

CREATE TABLE "BIZ_PROJECT" (
    "ID" SERIAL NOT NULL,
    "USER" INT NOT NULL,
    "NAME" TEXT NOT NULL,
    "CREATED_BY" INT NOT NULL,
    "MODIFIED_BY" INT NOT NULL,
    PRIMARY KEY ("ID")
);

COMMENT ON TABLE "BIZ_PROJECT" IS '项目';
COMMENT ON COLUMN "BIZ_PROJECT"."USER" IS '用户';
COMMENT ON COLUMN "BIZ_PROJECT"."NAME" IS '名称';
COMMENT ON COLUMN "BIZ_PROJECT"."CREATED_BY" IS '创建者';
COMMENT ON COLUMN "BIZ_PROJECT"."MODIFIED_BY" IS '修改者';

ALTER TABLE "BIZ_PROJECT"
    ADD CONSTRAINT "FK_BIZ_PROJECT_SYS_USER"
        FOREIGN KEY ("USER")
            REFERENCES "SYS_USER" ("ID");

ALTER TABLE "BIZ_PROJECT"
    ADD CONSTRAINT "FK_BIZ_PROJECT_CREATED_BY"
        FOREIGN KEY ("CREATED_BY")
            REFERENCES "SYS_USER" ("ID");

ALTER TABLE "BIZ_PROJECT"
    ADD CONSTRAINT "FK_BIZ_PROJECT_MODIFIED_BY"
        FOREIGN KEY ("MODIFIED_BY")
            REFERENCES "SYS_USER" ("ID");

), (ddl/SYS_USER.sql, DROP TABLE IF EXISTS "SYS_USER" CASCADE;

CREATE TABLE "SYS_USER" (
    "ID" SERIAL NOT NULL,
    "NAME" TEXT NOT NULL,
    "CREATED_BY" INT NOT NULL,
    "MODIFIED_BY" INT NOT NULL,
    PRIMARY KEY ("ID")
);

COMMENT ON TABLE "SYS_USER" IS '用户';
COMMENT ON COLUMN "SYS_USER"."NAME" IS '名称';
COMMENT ON COLUMN "SYS_USER"."CREATED_BY" IS '创建者';
COMMENT ON COLUMN "SYS_USER"."MODIFIED_BY" IS '修改者';

ALTER TABLE "SYS_USER"
    ADD CONSTRAINT "FK_SYS_USER_CREATED_BY"
        FOREIGN KEY ("CREATED_BY")
            REFERENCES "SYS_USER" ("ID");

ALTER TABLE "SYS_USER"
    ADD CONSTRAINT "FK_SYS_USER_MODIFIED_BY"
        FOREIGN KEY ("MODIFIED_BY")
            REFERENCES "SYS_USER" ("ID");

ALTER TABLE "BIZ_PROJECT"
    ADD CONSTRAINT "FK_BIZ_PROJECT_SYS_USER"
        FOREIGN KEY ("USER")
            REFERENCES "SYS_USER" ("ID");

ALTER TABLE "BIZ_PROJECT"
    ADD CONSTRAINT "FK_BIZ_PROJECT_CREATED_BY"
        FOREIGN KEY ("CREATED_BY")
            REFERENCES "SYS_USER" ("ID");

ALTER TABLE "BIZ_PROJECT"
    ADD CONSTRAINT "FK_BIZ_PROJECT_MODIFIED_BY"
        FOREIGN KEY ("MODIFIED_BY")
            REFERENCES "SYS_USER" ("ID");

)]
"""

const val h2Result = """
[(ddl/all-tables.sql, DROP TABLE IF EXISTS `BIZ_PROJECT` CASCADE;
DROP TABLE IF EXISTS `SYS_USER` CASCADE;

CREATE TABLE `BIZ_PROJECT` (
    `ID` INTEGER NOT NULL AUTO_INCREMENT,
    `USER` INTEGER NOT NULL,
    `NAME` CHARACTER VARYING(255) NOT NULL,
    `CREATED_BY` INTEGER NOT NULL,
    `MODIFIED_BY` INTEGER NOT NULL,
    PRIMARY KEY (`ID`)
);

COMMENT ON TABLE `BIZ_PROJECT` IS '项目';
COMMENT ON COLUMN `BIZ_PROJECT`.`USER` IS '用户';
COMMENT ON COLUMN `BIZ_PROJECT`.`NAME` IS '名称';
COMMENT ON COLUMN `BIZ_PROJECT`.`CREATED_BY` IS '创建者';
COMMENT ON COLUMN `BIZ_PROJECT`.`MODIFIED_BY` IS '修改者';

CREATE TABLE `SYS_USER` (
    `ID` INTEGER NOT NULL AUTO_INCREMENT,
    `NAME` CHARACTER VARYING(255) NOT NULL,
    `CREATED_BY` INTEGER NOT NULL,
    `MODIFIED_BY` INTEGER NOT NULL,
    PRIMARY KEY (`ID`)
);

COMMENT ON TABLE `SYS_USER` IS '用户';
COMMENT ON COLUMN `SYS_USER`.`NAME` IS '名称';
COMMENT ON COLUMN `SYS_USER`.`CREATED_BY` IS '创建者';
COMMENT ON COLUMN `SYS_USER`.`MODIFIED_BY` IS '修改者';

ALTER TABLE `BIZ_PROJECT`
    ADD CONSTRAINT `FK_BIZ_PROJECT_SYS_USER`
        FOREIGN KEY (`USER`)
            REFERENCES `SYS_USER` (`ID`);

ALTER TABLE `BIZ_PROJECT`
    ADD CONSTRAINT `FK_BIZ_PROJECT_CREATED_BY`
        FOREIGN KEY (`CREATED_BY`)
            REFERENCES `SYS_USER` (`ID`);

ALTER TABLE `BIZ_PROJECT`
    ADD CONSTRAINT `FK_BIZ_PROJECT_MODIFIED_BY`
        FOREIGN KEY (`MODIFIED_BY`)
            REFERENCES `SYS_USER` (`ID`);

ALTER TABLE `SYS_USER`
    ADD CONSTRAINT `FK_SYS_USER_CREATED_BY`
        FOREIGN KEY (`CREATED_BY`)
            REFERENCES `SYS_USER` (`ID`);

ALTER TABLE `SYS_USER`
    ADD CONSTRAINT `FK_SYS_USER_MODIFIED_BY`
        FOREIGN KEY (`MODIFIED_BY`)
            REFERENCES `SYS_USER` (`ID`);

), (ddl/BIZ_PROJECT.sql, DROP TABLE IF EXISTS `BIZ_PROJECT` CASCADE;

CREATE TABLE `BIZ_PROJECT` (
    `ID` INTEGER NOT NULL AUTO_INCREMENT,
    `USER` INTEGER NOT NULL,
    `NAME` CHARACTER VARYING(255) NOT NULL,
    `CREATED_BY` INTEGER NOT NULL,
    `MODIFIED_BY` INTEGER NOT NULL,
    PRIMARY KEY (`ID`)
);

COMMENT ON TABLE `BIZ_PROJECT` IS '项目';
COMMENT ON COLUMN `BIZ_PROJECT`.`USER` IS '用户';
COMMENT ON COLUMN `BIZ_PROJECT`.`NAME` IS '名称';
COMMENT ON COLUMN `BIZ_PROJECT`.`CREATED_BY` IS '创建者';
COMMENT ON COLUMN `BIZ_PROJECT`.`MODIFIED_BY` IS '修改者';

ALTER TABLE `BIZ_PROJECT`
    ADD CONSTRAINT `FK_BIZ_PROJECT_SYS_USER`
        FOREIGN KEY (`USER`)
            REFERENCES `SYS_USER` (`ID`);

ALTER TABLE `BIZ_PROJECT`
    ADD CONSTRAINT `FK_BIZ_PROJECT_CREATED_BY`
        FOREIGN KEY (`CREATED_BY`)
            REFERENCES `SYS_USER` (`ID`);

ALTER TABLE `BIZ_PROJECT`
    ADD CONSTRAINT `FK_BIZ_PROJECT_MODIFIED_BY`
        FOREIGN KEY (`MODIFIED_BY`)
            REFERENCES `SYS_USER` (`ID`);

), (ddl/SYS_USER.sql, DROP TABLE IF EXISTS `SYS_USER` CASCADE;

CREATE TABLE `SYS_USER` (
    `ID` INTEGER NOT NULL AUTO_INCREMENT,
    `NAME` CHARACTER VARYING(255) NOT NULL,
    `CREATED_BY` INTEGER NOT NULL,
    `MODIFIED_BY` INTEGER NOT NULL,
    PRIMARY KEY (`ID`)
);

COMMENT ON TABLE `SYS_USER` IS '用户';
COMMENT ON COLUMN `SYS_USER`.`NAME` IS '名称';
COMMENT ON COLUMN `SYS_USER`.`CREATED_BY` IS '创建者';
COMMENT ON COLUMN `SYS_USER`.`MODIFIED_BY` IS '修改者';

ALTER TABLE `SYS_USER`
    ADD CONSTRAINT `FK_SYS_USER_CREATED_BY`
        FOREIGN KEY (`CREATED_BY`)
            REFERENCES `SYS_USER` (`ID`);

ALTER TABLE `SYS_USER`
    ADD CONSTRAINT `FK_SYS_USER_MODIFIED_BY`
        FOREIGN KEY (`MODIFIED_BY`)
            REFERENCES `SYS_USER` (`ID`);

ALTER TABLE `BIZ_PROJECT`
    ADD CONSTRAINT `FK_BIZ_PROJECT_SYS_USER`
        FOREIGN KEY (`USER`)
            REFERENCES `SYS_USER` (`ID`);

ALTER TABLE `BIZ_PROJECT`
    ADD CONSTRAINT `FK_BIZ_PROJECT_CREATED_BY`
        FOREIGN KEY (`CREATED_BY`)
            REFERENCES `SYS_USER` (`ID`);

ALTER TABLE `BIZ_PROJECT`
    ADD CONSTRAINT `FK_BIZ_PROJECT_MODIFIED_BY`
        FOREIGN KEY (`MODIFIED_BY`)
            REFERENCES `SYS_USER` (`ID`);

)]
"""
