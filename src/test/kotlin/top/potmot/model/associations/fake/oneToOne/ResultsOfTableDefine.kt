package top.potmot.model.associations.fake.oneToOne

const val mysqlResult = """
[(ddl/all-tables.sql, DROP TABLE IF EXISTS `USER`;
DROP TABLE IF EXISTS `USER_DETAIL`;

CREATE TABLE `USER` (
    `ID` BIGINT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`ID`)
)
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COMMENT = ''
  ROW_FORMAT = Dynamic;

CREATE TABLE `USER_DETAIL` (
    `ID` BIGINT NOT NULL AUTO_INCREMENT,
    `USER_ID` BIGINT NOT NULL,
    PRIMARY KEY (`ID`)
)
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COMMENT = ''
  ROW_FORMAT = Dynamic;

-- ALTER TABLE `USER_DETAIL`
--     ADD CONSTRAINT `FK_DETAIL_USER`
--         FOREIGN KEY (`USER_ID`)
--             REFERENCES `USER` (`ID`);

), (ddl/user.sql, DROP TABLE IF EXISTS `USER`;

CREATE TABLE `USER` (
    `ID` BIGINT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`ID`)
)
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COMMENT = ''
  ROW_FORMAT = Dynamic;

-- ALTER TABLE `USER_DETAIL`
--     ADD CONSTRAINT `FK_DETAIL_USER`
--         FOREIGN KEY (`USER_ID`)
--             REFERENCES `USER` (`ID`);

), (ddl/user_detail.sql, DROP TABLE IF EXISTS `USER_DETAIL`;

CREATE TABLE `USER_DETAIL` (
    `ID` BIGINT NOT NULL AUTO_INCREMENT,
    `USER_ID` BIGINT NOT NULL,
    PRIMARY KEY (`ID`)
)
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COMMENT = ''
  ROW_FORMAT = Dynamic;

-- ALTER TABLE `USER_DETAIL`
--     ADD CONSTRAINT `FK_DETAIL_USER`
--         FOREIGN KEY (`USER_ID`)
--             REFERENCES `USER` (`ID`);

)]
"""

const val postgresResult = """
[(ddl/all-tables.sql, DROP TABLE IF EXISTS "USER" CASCADE;
DROP TABLE IF EXISTS "USER_DETAIL" CASCADE;

CREATE TABLE "USER" (
    "ID" BIGSERIAL NOT NULL,
    PRIMARY KEY ("ID")
);

CREATE TABLE "USER_DETAIL" (
    "ID" BIGSERIAL NOT NULL,
    "USER_ID" BIGINT NOT NULL,
    PRIMARY KEY ("ID")
);

-- ALTER TABLE "USER_DETAIL"
--     ADD CONSTRAINT "FK_DETAIL_USER"
--         FOREIGN KEY ("USER_ID")
--             REFERENCES "USER" ("ID");

), (ddl/user.sql, DROP TABLE IF EXISTS "USER" CASCADE;

CREATE TABLE "USER" (
    "ID" BIGSERIAL NOT NULL,
    PRIMARY KEY ("ID")
);

-- ALTER TABLE "USER_DETAIL"
--     ADD CONSTRAINT "FK_DETAIL_USER"
--         FOREIGN KEY ("USER_ID")
--             REFERENCES "USER" ("ID");

), (ddl/user_detail.sql, DROP TABLE IF EXISTS "USER_DETAIL" CASCADE;

CREATE TABLE "USER_DETAIL" (
    "ID" BIGSERIAL NOT NULL,
    "USER_ID" BIGINT NOT NULL,
    PRIMARY KEY ("ID")
);

-- ALTER TABLE "USER_DETAIL"
--     ADD CONSTRAINT "FK_DETAIL_USER"
--         FOREIGN KEY ("USER_ID")
--             REFERENCES "USER" ("ID");

)]
"""

const val h2Result = """
[(ddl/all-tables.sql, DROP TABLE IF EXISTS `USER` CASCADE;
DROP TABLE IF EXISTS `USER_DETAIL` CASCADE;

CREATE TABLE `USER` (
    `ID` BIGINT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`ID`)
);

CREATE TABLE `USER_DETAIL` (
    `ID` BIGINT NOT NULL AUTO_INCREMENT,
    `USER_ID` BIGINT NOT NULL,
    PRIMARY KEY (`ID`)
);

-- ALTER TABLE `USER_DETAIL`
--     ADD CONSTRAINT `FK_DETAIL_USER`
--         FOREIGN KEY (`USER_ID`)
--             REFERENCES `USER` (`ID`);

), (ddl/user.sql, DROP TABLE IF EXISTS `USER` CASCADE;

CREATE TABLE `USER` (
    `ID` BIGINT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`ID`)
);

-- ALTER TABLE `USER_DETAIL`
--     ADD CONSTRAINT `FK_DETAIL_USER`
--         FOREIGN KEY (`USER_ID`)
--             REFERENCES `USER` (`ID`);

), (ddl/user_detail.sql, DROP TABLE IF EXISTS `USER_DETAIL` CASCADE;

CREATE TABLE `USER_DETAIL` (
    `ID` BIGINT NOT NULL AUTO_INCREMENT,
    `USER_ID` BIGINT NOT NULL,
    PRIMARY KEY (`ID`)
);

-- ALTER TABLE `USER_DETAIL`
--     ADD CONSTRAINT `FK_DETAIL_USER`
--         FOREIGN KEY (`USER_ID`)
--             REFERENCES `USER` (`ID`);

)]
"""
