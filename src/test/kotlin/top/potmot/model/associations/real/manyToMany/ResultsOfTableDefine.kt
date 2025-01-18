package top.potmot.model.associations.real.manyToMany

const val mysqlResult = """
[(ddl/all-tables.sql, DROP TABLE IF EXISTS `COURSE`;
DROP TABLE IF EXISTS `STUDENT`;

CREATE TABLE `COURSE` (
    `ID` BIGINT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`ID`)
)
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COMMENT = ''
  ROW_FORMAT = Dynamic;

CREATE TABLE `STUDENT` (
    `ID` BIGINT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`ID`)
)
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COMMENT = ''
  ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `COURSE_STUDENT_MAPPING`;

CREATE TABLE `COURSE_STUDENT_MAPPING` (
    `COURSE_ID` BIGINT NOT NULL,
    `STUDENT_ID` BIGINT NOT NULL
)
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COMMENT = '与的映射关系表'
  ROW_FORMAT = Dynamic;

ALTER TABLE `COURSE_STUDENT_MAPPING` ADD CONSTRAINT `PK_COURSE_STUDENT_MAPPING` PRIMARY KEY (`COURSE_ID`,`STUDENT_ID`);

ALTER TABLE `COURSE_STUDENT_MAPPING`
    ADD CONSTRAINT `COURSE_STUDENT_MAPPING_S`
        FOREIGN KEY (`COURSE_ID`)
            REFERENCES `COURSE` (`ID`);

ALTER TABLE `COURSE_STUDENT_MAPPING`
    ADD CONSTRAINT `COURSE_STUDENT_MAPPING_T`
        FOREIGN KEY (`STUDENT_ID`)
            REFERENCES `STUDENT` (`ID`);

), (ddl/course.sql, DROP TABLE IF EXISTS `COURSE`;

CREATE TABLE `COURSE` (
    `ID` BIGINT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`ID`)
)
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COMMENT = ''
  ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `COURSE_STUDENT_MAPPING`;

CREATE TABLE `COURSE_STUDENT_MAPPING` (
    `COURSE_ID` BIGINT NOT NULL,
    `STUDENT_ID` BIGINT NOT NULL
)
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COMMENT = '与的映射关系表'
  ROW_FORMAT = Dynamic;

ALTER TABLE `COURSE_STUDENT_MAPPING` ADD CONSTRAINT `PK_COURSE_STUDENT_MAPPING` PRIMARY KEY (`COURSE_ID`,`STUDENT_ID`);

ALTER TABLE `COURSE_STUDENT_MAPPING`
    ADD CONSTRAINT `COURSE_STUDENT_MAPPING_S`
        FOREIGN KEY (`COURSE_ID`)
            REFERENCES `COURSE` (`ID`);

ALTER TABLE `COURSE_STUDENT_MAPPING`
    ADD CONSTRAINT `COURSE_STUDENT_MAPPING_T`
        FOREIGN KEY (`STUDENT_ID`)
            REFERENCES `STUDENT` (`ID`);

), (ddl/student.sql, DROP TABLE IF EXISTS `STUDENT`;

CREATE TABLE `STUDENT` (
    `ID` BIGINT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`ID`)
)
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COMMENT = ''
  ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `COURSE_STUDENT_MAPPING`;

CREATE TABLE `COURSE_STUDENT_MAPPING` (
    `COURSE_ID` BIGINT NOT NULL,
    `STUDENT_ID` BIGINT NOT NULL
)
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COMMENT = '与的映射关系表'
  ROW_FORMAT = Dynamic;

ALTER TABLE `COURSE_STUDENT_MAPPING` ADD CONSTRAINT `PK_COURSE_STUDENT_MAPPING` PRIMARY KEY (`COURSE_ID`,`STUDENT_ID`);

ALTER TABLE `COURSE_STUDENT_MAPPING`
    ADD CONSTRAINT `COURSE_STUDENT_MAPPING_S`
        FOREIGN KEY (`COURSE_ID`)
            REFERENCES `COURSE` (`ID`);

ALTER TABLE `COURSE_STUDENT_MAPPING`
    ADD CONSTRAINT `COURSE_STUDENT_MAPPING_T`
        FOREIGN KEY (`STUDENT_ID`)
            REFERENCES `STUDENT` (`ID`);

)]
"""

const val postgresResult = """
[(ddl/all-tables.sql, DROP TABLE IF EXISTS "COURSE" CASCADE;
DROP TABLE IF EXISTS "STUDENT" CASCADE;

CREATE TABLE "COURSE" (
    "ID" BIGSERIAL NOT NULL,
    PRIMARY KEY ("ID")
);

CREATE TABLE "STUDENT" (
    "ID" BIGSERIAL NOT NULL,
    PRIMARY KEY ("ID")
);

DROP TABLE IF EXISTS "COURSE_STUDENT_MAPPING" CASCADE;

CREATE TABLE "COURSE_STUDENT_MAPPING" (
    "COURSE_ID" BIGINT NOT NULL,
    "STUDENT_ID" BIGINT NOT NULL
);

ALTER TABLE "COURSE_STUDENT_MAPPING" ADD CONSTRAINT "PK_COURSE_STUDENT_MAPPING" PRIMARY KEY ("COURSE_ID","STUDENT_ID");

ALTER TABLE "COURSE_STUDENT_MAPPING"
    ADD CONSTRAINT "COURSE_STUDENT_MAPPING_S"
        FOREIGN KEY ("COURSE_ID")
            REFERENCES "COURSE" ("ID");

ALTER TABLE "COURSE_STUDENT_MAPPING"
    ADD CONSTRAINT "COURSE_STUDENT_MAPPING_T"
        FOREIGN KEY ("STUDENT_ID")
            REFERENCES "STUDENT" ("ID");

COMMENT ON TABLE "COURSE_STUDENT_MAPPING" IS '与的映射关系表';

), (ddl/course.sql, DROP TABLE IF EXISTS "COURSE" CASCADE;

CREATE TABLE "COURSE" (
    "ID" BIGSERIAL NOT NULL,
    PRIMARY KEY ("ID")
);

DROP TABLE IF EXISTS "COURSE_STUDENT_MAPPING" CASCADE;

CREATE TABLE "COURSE_STUDENT_MAPPING" (
    "COURSE_ID" BIGINT NOT NULL,
    "STUDENT_ID" BIGINT NOT NULL
);

ALTER TABLE "COURSE_STUDENT_MAPPING" ADD CONSTRAINT "PK_COURSE_STUDENT_MAPPING" PRIMARY KEY ("COURSE_ID","STUDENT_ID");

ALTER TABLE "COURSE_STUDENT_MAPPING"
    ADD CONSTRAINT "COURSE_STUDENT_MAPPING_S"
        FOREIGN KEY ("COURSE_ID")
            REFERENCES "COURSE" ("ID");

ALTER TABLE "COURSE_STUDENT_MAPPING"
    ADD CONSTRAINT "COURSE_STUDENT_MAPPING_T"
        FOREIGN KEY ("STUDENT_ID")
            REFERENCES "STUDENT" ("ID");

COMMENT ON TABLE "COURSE_STUDENT_MAPPING" IS '与的映射关系表';

), (ddl/student.sql, DROP TABLE IF EXISTS "STUDENT" CASCADE;

CREATE TABLE "STUDENT" (
    "ID" BIGSERIAL NOT NULL,
    PRIMARY KEY ("ID")
);

DROP TABLE IF EXISTS "COURSE_STUDENT_MAPPING" CASCADE;

CREATE TABLE "COURSE_STUDENT_MAPPING" (
    "COURSE_ID" BIGINT NOT NULL,
    "STUDENT_ID" BIGINT NOT NULL
);

ALTER TABLE "COURSE_STUDENT_MAPPING" ADD CONSTRAINT "PK_COURSE_STUDENT_MAPPING" PRIMARY KEY ("COURSE_ID","STUDENT_ID");

ALTER TABLE "COURSE_STUDENT_MAPPING"
    ADD CONSTRAINT "COURSE_STUDENT_MAPPING_S"
        FOREIGN KEY ("COURSE_ID")
            REFERENCES "COURSE" ("ID");

ALTER TABLE "COURSE_STUDENT_MAPPING"
    ADD CONSTRAINT "COURSE_STUDENT_MAPPING_T"
        FOREIGN KEY ("STUDENT_ID")
            REFERENCES "STUDENT" ("ID");

COMMENT ON TABLE "COURSE_STUDENT_MAPPING" IS '与的映射关系表';

)]
"""

const val h2Result = """
[(ddl/all-tables.sql, DROP TABLE IF EXISTS `COURSE` CASCADE;
DROP TABLE IF EXISTS `STUDENT` CASCADE;

CREATE TABLE `COURSE` (
    `ID` BIGINT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`ID`)
);

CREATE TABLE `STUDENT` (
    `ID` BIGINT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`ID`)
);

DROP TABLE IF EXISTS `COURSE_STUDENT_MAPPING` CASCADE;

CREATE TABLE `COURSE_STUDENT_MAPPING` (
    `COURSE_ID` BIGINT NOT NULL,
    `STUDENT_ID` BIGINT NOT NULL
);

ALTER TABLE `COURSE_STUDENT_MAPPING` ADD CONSTRAINT `PK_COURSE_STUDENT_MAPPING` PRIMARY KEY (`COURSE_ID`,`STUDENT_ID`);

ALTER TABLE `COURSE_STUDENT_MAPPING`
    ADD CONSTRAINT `COURSE_STUDENT_MAPPING_S`
        FOREIGN KEY (`COURSE_ID`)
            REFERENCES `COURSE` (`ID`);

ALTER TABLE `COURSE_STUDENT_MAPPING`
    ADD CONSTRAINT `COURSE_STUDENT_MAPPING_T`
        FOREIGN KEY (`STUDENT_ID`)
            REFERENCES `STUDENT` (`ID`);

COMMENT ON TABLE `COURSE_STUDENT_MAPPING` IS '与的映射关系表';

), (ddl/course.sql, DROP TABLE IF EXISTS `COURSE` CASCADE;

CREATE TABLE `COURSE` (
    `ID` BIGINT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`ID`)
);

DROP TABLE IF EXISTS `COURSE_STUDENT_MAPPING` CASCADE;

CREATE TABLE `COURSE_STUDENT_MAPPING` (
    `COURSE_ID` BIGINT NOT NULL,
    `STUDENT_ID` BIGINT NOT NULL
);

ALTER TABLE `COURSE_STUDENT_MAPPING` ADD CONSTRAINT `PK_COURSE_STUDENT_MAPPING` PRIMARY KEY (`COURSE_ID`,`STUDENT_ID`);

ALTER TABLE `COURSE_STUDENT_MAPPING`
    ADD CONSTRAINT `COURSE_STUDENT_MAPPING_S`
        FOREIGN KEY (`COURSE_ID`)
            REFERENCES `COURSE` (`ID`);

ALTER TABLE `COURSE_STUDENT_MAPPING`
    ADD CONSTRAINT `COURSE_STUDENT_MAPPING_T`
        FOREIGN KEY (`STUDENT_ID`)
            REFERENCES `STUDENT` (`ID`);

COMMENT ON TABLE `COURSE_STUDENT_MAPPING` IS '与的映射关系表';

), (ddl/student.sql, DROP TABLE IF EXISTS `STUDENT` CASCADE;

CREATE TABLE `STUDENT` (
    `ID` BIGINT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`ID`)
);

DROP TABLE IF EXISTS `COURSE_STUDENT_MAPPING` CASCADE;

CREATE TABLE `COURSE_STUDENT_MAPPING` (
    `COURSE_ID` BIGINT NOT NULL,
    `STUDENT_ID` BIGINT NOT NULL
);

ALTER TABLE `COURSE_STUDENT_MAPPING` ADD CONSTRAINT `PK_COURSE_STUDENT_MAPPING` PRIMARY KEY (`COURSE_ID`,`STUDENT_ID`);

ALTER TABLE `COURSE_STUDENT_MAPPING`
    ADD CONSTRAINT `COURSE_STUDENT_MAPPING_S`
        FOREIGN KEY (`COURSE_ID`)
            REFERENCES `COURSE` (`ID`);

ALTER TABLE `COURSE_STUDENT_MAPPING`
    ADD CONSTRAINT `COURSE_STUDENT_MAPPING_T`
        FOREIGN KEY (`STUDENT_ID`)
            REFERENCES `STUDENT` (`ID`);

COMMENT ON TABLE `COURSE_STUDENT_MAPPING` IS '与的映射关系表';

)]
"""
