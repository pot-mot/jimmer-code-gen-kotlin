package top.potmot.model.associations.logicalDelete.manyToOne

const val mysqlResult = """
[(all-tables.sql, DROP TABLE IF EXISTS `ORDER_DETAIL`;
DROP TABLE IF EXISTS `ORDER`;

CREATE TABLE `ORDER_DETAIL` (
    `ID` BIGINT NOT NULL AUTO_INCREMENT,
    `ORDER_ID` BIGINT NOT NULL,
    `DELETE_FLAG` BOOLEAN NOT NULL,
    PRIMARY KEY (`ID`)
)
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COMMENT = ''
  ROW_FORMAT = Dynamic;

CREATE TABLE `ORDER` (
    `ID` BIGINT NOT NULL AUTO_INCREMENT,
    `DELETE_FLAG` BOOLEAN NOT NULL,
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

), (order.sql, DROP TABLE IF EXISTS `ORDER`;

CREATE TABLE `ORDER` (
    `ID` BIGINT NOT NULL AUTO_INCREMENT,
    `DELETE_FLAG` BOOLEAN NOT NULL,
    PRIMARY KEY (`ID`)
)
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COMMENT = ''
  ROW_FORMAT = Dynamic;

), (order_detail.sql, DROP TABLE IF EXISTS `ORDER_DETAIL`;

CREATE TABLE `ORDER_DETAIL` (
    `ID` BIGINT NOT NULL AUTO_INCREMENT,
    `ORDER_ID` BIGINT NOT NULL,
    `DELETE_FLAG` BOOLEAN NOT NULL,
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
[(all-tables.sql, DROP TABLE IF EXISTS "ORDER_DETAIL" CASCADE;
DROP TABLE IF EXISTS "ORDER" CASCADE;

CREATE TABLE "ORDER_DETAIL" (
    "ID" BIGSERIAL NOT NULL,
    "ORDER_ID" BIGINT NOT NULL,
    "DELETE_FLAG" BOOLEAN NOT NULL,
    PRIMARY KEY ("ID")
);

CREATE TABLE "ORDER" (
    "ID" BIGSERIAL NOT NULL,
    "DELETE_FLAG" BOOLEAN NOT NULL,
    PRIMARY KEY ("ID")
);

ALTER TABLE "ORDER_DETAIL"
    ADD CONSTRAINT "FK_DETAIL_ORDER"
        FOREIGN KEY ("ORDER_ID")
            REFERENCES "ORDER" ("ID");

), (order.sql, DROP TABLE IF EXISTS "ORDER" CASCADE;

CREATE TABLE "ORDER" (
    "ID" BIGSERIAL NOT NULL,
    "DELETE_FLAG" BOOLEAN NOT NULL,
    PRIMARY KEY ("ID")
);

), (order_detail.sql, DROP TABLE IF EXISTS "ORDER_DETAIL" CASCADE;

CREATE TABLE "ORDER_DETAIL" (
    "ID" BIGSERIAL NOT NULL,
    "ORDER_ID" BIGINT NOT NULL,
    "DELETE_FLAG" BOOLEAN NOT NULL,
    PRIMARY KEY ("ID")
);

ALTER TABLE "ORDER_DETAIL"
    ADD CONSTRAINT "FK_DETAIL_ORDER"
        FOREIGN KEY ("ORDER_ID")
            REFERENCES "ORDER" ("ID");

)]
"""

const val h2Result = """
[(all-tables.sql, DROP TABLE IF EXISTS `ORDER_DETAIL` CASCADE;
DROP TABLE IF EXISTS `ORDER` CASCADE;

CREATE TABLE `ORDER_DETAIL` (
    `ID` BIGINT NOT NULL AUTO_INCREMENT,
    `ORDER_ID` BIGINT NOT NULL,
    `DELETE_FLAG` BOOLEAN NOT NULL,
    PRIMARY KEY (`ID`)
);

CREATE TABLE `ORDER` (
    `ID` BIGINT NOT NULL AUTO_INCREMENT,
    `DELETE_FLAG` BOOLEAN NOT NULL,
    PRIMARY KEY (`ID`)
);

ALTER TABLE `ORDER_DETAIL`
    ADD CONSTRAINT `FK_DETAIL_ORDER`
        FOREIGN KEY (`ORDER_ID`)
            REFERENCES `ORDER` (`ID`);

), (order.sql, DROP TABLE IF EXISTS `ORDER` CASCADE;

CREATE TABLE `ORDER` (
    `ID` BIGINT NOT NULL AUTO_INCREMENT,
    `DELETE_FLAG` BOOLEAN NOT NULL,
    PRIMARY KEY (`ID`)
);

), (order_detail.sql, DROP TABLE IF EXISTS `ORDER_DETAIL` CASCADE;

CREATE TABLE `ORDER_DETAIL` (
    `ID` BIGINT NOT NULL AUTO_INCREMENT,
    `ORDER_ID` BIGINT NOT NULL,
    `DELETE_FLAG` BOOLEAN NOT NULL,
    PRIMARY KEY (`ID`)
);

ALTER TABLE `ORDER_DETAIL`
    ADD CONSTRAINT `FK_DETAIL_ORDER`
        FOREIGN KEY (`ORDER_ID`)
            REFERENCES `ORDER` (`ID`);

)]
"""
