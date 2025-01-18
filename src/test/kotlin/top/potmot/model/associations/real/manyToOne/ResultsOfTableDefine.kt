package top.potmot.model.associations.real.manyToOne

const val mysqlResult = """
[(ddl/all-tables.sql, DROP TABLE IF EXISTS `ORDER`;
DROP TABLE IF EXISTS `ORDER_DETAIL`;

CREATE TABLE `ORDER` (
    `ID` BIGINT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`ID`)
)
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COMMENT = ''
  ROW_FORMAT = Dynamic;

CREATE TABLE `ORDER_DETAIL` (
    `ID` BIGINT NOT NULL AUTO_INCREMENT,
    `ORDER_ID` BIGINT NOT NULL,
    PRIMARY KEY (`ID`)
)
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COMMENT = ''
  ROW_FORMAT = Dynamic;

ALTER TABLE `ORDER_DETAIL`
    ADD CONSTRAINT `FK_DETAIL_ORDER`
        FOREIGN KEY (`ORDER_ID`)
            REFERENCES `ORDER` (`ID`);

), (ddl/order.sql, DROP TABLE IF EXISTS `ORDER`;

CREATE TABLE `ORDER` (
    `ID` BIGINT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`ID`)
)
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COMMENT = ''
  ROW_FORMAT = Dynamic;

ALTER TABLE `ORDER_DETAIL`
    ADD CONSTRAINT `FK_DETAIL_ORDER`
        FOREIGN KEY (`ORDER_ID`)
            REFERENCES `ORDER` (`ID`);

), (ddl/order_detail.sql, DROP TABLE IF EXISTS `ORDER_DETAIL`;

CREATE TABLE `ORDER_DETAIL` (
    `ID` BIGINT NOT NULL AUTO_INCREMENT,
    `ORDER_ID` BIGINT NOT NULL,
    PRIMARY KEY (`ID`)
)
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COMMENT = ''
  ROW_FORMAT = Dynamic;

ALTER TABLE `ORDER_DETAIL`
    ADD CONSTRAINT `FK_DETAIL_ORDER`
        FOREIGN KEY (`ORDER_ID`)
            REFERENCES `ORDER` (`ID`);

)]
"""

const val postgresResult = """
[(ddl/all-tables.sql, DROP TABLE IF EXISTS "ORDER" CASCADE;
DROP TABLE IF EXISTS "ORDER_DETAIL" CASCADE;

CREATE TABLE "ORDER" (
    "ID" BIGSERIAL NOT NULL,
    PRIMARY KEY ("ID")
);

CREATE TABLE "ORDER_DETAIL" (
    "ID" BIGSERIAL NOT NULL,
    "ORDER_ID" BIGINT NOT NULL,
    PRIMARY KEY ("ID")
);

ALTER TABLE "ORDER_DETAIL"
    ADD CONSTRAINT "FK_DETAIL_ORDER"
        FOREIGN KEY ("ORDER_ID")
            REFERENCES "ORDER" ("ID");

), (ddl/order.sql, DROP TABLE IF EXISTS "ORDER" CASCADE;

CREATE TABLE "ORDER" (
    "ID" BIGSERIAL NOT NULL,
    PRIMARY KEY ("ID")
);

ALTER TABLE "ORDER_DETAIL"
    ADD CONSTRAINT "FK_DETAIL_ORDER"
        FOREIGN KEY ("ORDER_ID")
            REFERENCES "ORDER" ("ID");

), (ddl/order_detail.sql, DROP TABLE IF EXISTS "ORDER_DETAIL" CASCADE;

CREATE TABLE "ORDER_DETAIL" (
    "ID" BIGSERIAL NOT NULL,
    "ORDER_ID" BIGINT NOT NULL,
    PRIMARY KEY ("ID")
);

ALTER TABLE "ORDER_DETAIL"
    ADD CONSTRAINT "FK_DETAIL_ORDER"
        FOREIGN KEY ("ORDER_ID")
            REFERENCES "ORDER" ("ID");

)]
"""

const val h2Result = """
[(ddl/all-tables.sql, DROP TABLE IF EXISTS `ORDER` CASCADE;
DROP TABLE IF EXISTS `ORDER_DETAIL` CASCADE;

CREATE TABLE `ORDER` (
    `ID` BIGINT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`ID`)
);

CREATE TABLE `ORDER_DETAIL` (
    `ID` BIGINT NOT NULL AUTO_INCREMENT,
    `ORDER_ID` BIGINT NOT NULL,
    PRIMARY KEY (`ID`)
);

ALTER TABLE `ORDER_DETAIL`
    ADD CONSTRAINT `FK_DETAIL_ORDER`
        FOREIGN KEY (`ORDER_ID`)
            REFERENCES `ORDER` (`ID`);

), (ddl/order.sql, DROP TABLE IF EXISTS `ORDER` CASCADE;

CREATE TABLE `ORDER` (
    `ID` BIGINT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`ID`)
);

ALTER TABLE `ORDER_DETAIL`
    ADD CONSTRAINT `FK_DETAIL_ORDER`
        FOREIGN KEY (`ORDER_ID`)
            REFERENCES `ORDER` (`ID`);

), (ddl/order_detail.sql, DROP TABLE IF EXISTS `ORDER_DETAIL` CASCADE;

CREATE TABLE `ORDER_DETAIL` (
    `ID` BIGINT NOT NULL AUTO_INCREMENT,
    `ORDER_ID` BIGINT NOT NULL,
    PRIMARY KEY (`ID`)
);

ALTER TABLE `ORDER_DETAIL`
    ADD CONSTRAINT `FK_DETAIL_ORDER`
        FOREIGN KEY (`ORDER_ID`)
            REFERENCES `ORDER` (`ID`);

)]
"""
