package top.potmot.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import top.potmot.enumeration.DataSourceType
import top.potmot.service.ModelService
import top.potmot.service.PreviewService

@SpringBootTest
@ActiveProfiles("test-kotlin", "h2")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class TestModelToSql (
    @Autowired val modelService: ModelService,
    @Autowired val previewService: PreviewService
) {
    @Test
    @Order(1)
    fun testCreateMysqlSql() {
        val model = getBaseModel()

        model.dataSourceType = DataSourceType.MySQL

        val id = modelService.save(model)

        val tableDefineCodes = previewService.previewModelSql(id)

        assertEquals(
            mysqlResult.trim(),
            tableDefineCodes.toString().trim()
        )
    }

    @Test
    @Order(2)
    fun testCreatePostgresSql() {
        val model = getBaseModel()

        model.dataSourceType = DataSourceType.PostgreSQL

        val id = modelService.save(model)

        val tableDefineCodes = previewService.previewModelSql(id)

        assertEquals(
            postgresResult.trim(),
            tableDefineCodes.toString().trim()
        )
    }

    private val mysqlResult = """
[(all-tables.sql, DROP TABLE IF EXISTS `ENUM_TABLE`;
DROP TABLE IF EXISTS `TREE_NODE`;
DROP TABLE IF EXISTS `O_O_TARGET`;
DROP TABLE IF EXISTS `O_M_SOURCE`;
DROP TABLE IF EXISTS `O_M_TARGET`;
DROP TABLE IF EXISTS `M_N_TARGET`;
DROP TABLE IF EXISTS `M_O_SOURCE`;
DROP TABLE IF EXISTS `M_O_TARGET`;
DROP TABLE IF EXISTS `O_O_SOURCE`;
DROP TABLE IF EXISTS `M_N_SOURCE`;
DROP TABLE IF EXISTS `COMMENT_TABLE`;

CREATE TABLE `ENUM_TABLE` (
    `ID` BIGINT(0) NOT NULL AUTO_INCREMENT,
    `COMMON_ENUM` VARCHAR(0) NOT NULL,
    `ORDINAL_ENUM` VARCHAR(0) NOT NULL,
    `NAME_ENUM` VARCHAR(0) NOT NULL,
    PRIMARY KEY (`ID`)
)
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COMMENT = ''
  ROW_FORMAT = Dynamic;

CREATE TABLE `TREE_NODE` (
    `ID` BIGINT(0) NOT NULL AUTO_INCREMENT,
    `PARENT_ID` BIGINT(0) NOT NULL,
    PRIMARY KEY (`ID`)
)
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COMMENT = ''
  ROW_FORMAT = Dynamic;

CREATE TABLE `O_O_TARGET` (
    `ID` BIGINT(0) NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`ID`)
)
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COMMENT = ''
  ROW_FORMAT = Dynamic;

CREATE TABLE `O_M_SOURCE` (
    `ID` BIGINT(0) NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`ID`)
)
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COMMENT = ''
  ROW_FORMAT = Dynamic;

CREATE TABLE `O_M_TARGET` (
    `ID` BIGINT(0) NOT NULL AUTO_INCREMENT,
    `SOURCE_ID` BIGINT(0) NOT NULL,
    PRIMARY KEY (`ID`)
)
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COMMENT = ''
  ROW_FORMAT = Dynamic;

CREATE TABLE `M_N_TARGET` (
    `ID` BIGINT(0) NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`ID`)
)
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COMMENT = ''
  ROW_FORMAT = Dynamic;

CREATE TABLE `M_O_SOURCE` (
    `ID` BIGINT(0) NOT NULL AUTO_INCREMENT,
    `SOURCE_ID` BIGINT(0) NOT NULL,
    PRIMARY KEY (`ID`)
)
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COMMENT = ''
  ROW_FORMAT = Dynamic;

CREATE TABLE `M_O_TARGET` (
    `ID` BIGINT(0) NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`ID`)
)
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COMMENT = ''
  ROW_FORMAT = Dynamic;

CREATE TABLE `O_O_SOURCE` (
    `ID` BIGINT(0) NOT NULL AUTO_INCREMENT,
    `TARGET_ID` BIGINT(0) NOT NULL,
    PRIMARY KEY (`ID`)
)
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COMMENT = ''
  ROW_FORMAT = Dynamic;

CREATE TABLE `M_N_SOURCE` (
    `ID` BIGINT(0) NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`ID`)
)
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COMMENT = ''
  ROW_FORMAT = Dynamic;

CREATE TABLE `COMMENT_TABLE` (
    `ID` BIGINT(0) NOT NULL AUTO_INCREMENT,
    `NAME` VARCHAR(0) NOT NULL COMMENT '名称',
    PRIMARY KEY (`ID`)
)
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COMMENT = '注释测试'
  ROW_FORMAT = Dynamic;

ALTER TABLE `TREE_NODE` ADD CONSTRAINT `FK_TREE_NODE_TREE_NODE` 
    FOREIGN KEY (`PARENT_ID`)
  REFERENCES `TREE_NODE` (`ID`);

ALTER TABLE `O_M_TARGET` ADD CONSTRAINT `FK_O_M_TARGET_O_M_SOURCE` 
    FOREIGN KEY (`SOURCE_ID`)
  REFERENCES `O_M_SOURCE` (`ID`);

ALTER TABLE `M_O_SOURCE` ADD CONSTRAINT `FK_M_O_SOURCE_M_O_TARGET` 
    FOREIGN KEY (`SOURCE_ID`)
  REFERENCES `M_O_TARGET` (`ID`);

ALTER TABLE `O_O_SOURCE` ADD CONSTRAINT `FK_O_O_SOURCE_O_O_TARGET` 
    FOREIGN KEY (`TARGET_ID`)
  REFERENCES `O_O_TARGET` (`ID`);

DROP TABLE IF EXISTS `M_N_SOURCE_M_N_TARGET_MAPPING`;

CREATE TABLE `M_N_SOURCE_M_N_TARGET_MAPPING` (
    `M_N_SOURCE_ID` BIGINT(0) NOT NULL,
    `M_N_TARGET_ID` BIGINT(0) NOT NULL
)
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COMMENT = '与的映射关系表'
  ROW_FORMAT = Dynamic;

ALTER TABLE `M_N_SOURCE_M_N_TARGET_MAPPING` ADD CONSTRAINT `PK_M_N_SOURCE_M_N_TARGET_MAPPING` PRIMARY KEY (`M_N_SOURCE_ID`,`M_N_TARGET_ID`);

ALTER TABLE `M_N_SOURCE_M_N_TARGET_MAPPING` ADD CONSTRAINT `M_N_SOURCE_M_N_TARGET_MAPPING_S` 
    FOREIGN KEY (`M_N_SOURCE_ID`)
  REFERENCES `M_N_SOURCE` (`ID`);

ALTER TABLE `M_N_SOURCE_M_N_TARGET_MAPPING` ADD CONSTRAINT `M_N_SOURCE_M_N_TARGET_MAPPING_T` 
    FOREIGN KEY (`M_N_TARGET_ID`)
  REFERENCES `M_N_TARGET` (`ID`);

), (comment_table.sql, DROP TABLE IF EXISTS `COMMENT_TABLE`;

CREATE TABLE `COMMENT_TABLE` (
    `ID` BIGINT(0) NOT NULL AUTO_INCREMENT,
    `NAME` VARCHAR(0) NOT NULL COMMENT '名称',
    PRIMARY KEY (`ID`)
)
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COMMENT = '注释测试'
  ROW_FORMAT = Dynamic;

), (enum_table.sql, DROP TABLE IF EXISTS `ENUM_TABLE`;

CREATE TABLE `ENUM_TABLE` (
    `ID` BIGINT(0) NOT NULL AUTO_INCREMENT,
    `COMMON_ENUM` VARCHAR(0) NOT NULL,
    `ORDINAL_ENUM` VARCHAR(0) NOT NULL,
    `NAME_ENUM` VARCHAR(0) NOT NULL,
    PRIMARY KEY (`ID`)
)
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COMMENT = ''
  ROW_FORMAT = Dynamic;

), (m_n_source.sql, DROP TABLE IF EXISTS `M_N_SOURCE`;

CREATE TABLE `M_N_SOURCE` (
    `ID` BIGINT(0) NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`ID`)
)
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COMMENT = ''
  ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `M_N_SOURCE_M_N_TARGET_MAPPING`;

CREATE TABLE `M_N_SOURCE_M_N_TARGET_MAPPING` (
    `M_N_SOURCE_ID` BIGINT(0) NOT NULL,
    `M_N_TARGET_ID` BIGINT(0) NOT NULL
)
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COMMENT = '与的映射关系表'
  ROW_FORMAT = Dynamic;

ALTER TABLE `M_N_SOURCE_M_N_TARGET_MAPPING` ADD CONSTRAINT `PK_M_N_SOURCE_M_N_TARGET_MAPPING` PRIMARY KEY (`M_N_SOURCE_ID`,`M_N_TARGET_ID`);

ALTER TABLE `M_N_SOURCE_M_N_TARGET_MAPPING` ADD CONSTRAINT `M_N_SOURCE_M_N_TARGET_MAPPING_S` 
    FOREIGN KEY (`M_N_SOURCE_ID`)
  REFERENCES `M_N_SOURCE` (`ID`);

ALTER TABLE `M_N_SOURCE_M_N_TARGET_MAPPING` ADD CONSTRAINT `M_N_SOURCE_M_N_TARGET_MAPPING_T` 
    FOREIGN KEY (`M_N_TARGET_ID`)
  REFERENCES `M_N_TARGET` (`ID`);

), (m_n_target.sql, DROP TABLE IF EXISTS `M_N_TARGET`;

CREATE TABLE `M_N_TARGET` (
    `ID` BIGINT(0) NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`ID`)
)
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COMMENT = ''
  ROW_FORMAT = Dynamic;

), (m_o_source.sql, DROP TABLE IF EXISTS `M_O_SOURCE`;

CREATE TABLE `M_O_SOURCE` (
    `ID` BIGINT(0) NOT NULL AUTO_INCREMENT,
    `SOURCE_ID` BIGINT(0) NOT NULL,
    PRIMARY KEY (`ID`)
)
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COMMENT = ''
  ROW_FORMAT = Dynamic;

ALTER TABLE `M_O_SOURCE` ADD CONSTRAINT `FK_M_O_SOURCE_M_O_TARGET` 
    FOREIGN KEY (`SOURCE_ID`)
  REFERENCES `M_O_TARGET` (`ID`);

), (m_o_target.sql, DROP TABLE IF EXISTS `M_O_TARGET`;

CREATE TABLE `M_O_TARGET` (
    `ID` BIGINT(0) NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`ID`)
)
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COMMENT = ''
  ROW_FORMAT = Dynamic;

), (o_m_source.sql, DROP TABLE IF EXISTS `O_M_SOURCE`;

CREATE TABLE `O_M_SOURCE` (
    `ID` BIGINT(0) NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`ID`)
)
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COMMENT = ''
  ROW_FORMAT = Dynamic;

ALTER TABLE `O_M_TARGET` ADD CONSTRAINT `FK_O_M_TARGET_O_M_SOURCE` 
    FOREIGN KEY (`SOURCE_ID`)
  REFERENCES `O_M_SOURCE` (`ID`);

), (o_m_target.sql, DROP TABLE IF EXISTS `O_M_TARGET`;

CREATE TABLE `O_M_TARGET` (
    `ID` BIGINT(0) NOT NULL AUTO_INCREMENT,
    `SOURCE_ID` BIGINT(0) NOT NULL,
    PRIMARY KEY (`ID`)
)
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COMMENT = ''
  ROW_FORMAT = Dynamic;

), (o_o_source.sql, DROP TABLE IF EXISTS `O_O_SOURCE`;

CREATE TABLE `O_O_SOURCE` (
    `ID` BIGINT(0) NOT NULL AUTO_INCREMENT,
    `TARGET_ID` BIGINT(0) NOT NULL,
    PRIMARY KEY (`ID`)
)
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COMMENT = ''
  ROW_FORMAT = Dynamic;

ALTER TABLE `O_O_SOURCE` ADD CONSTRAINT `FK_O_O_SOURCE_O_O_TARGET` 
    FOREIGN KEY (`TARGET_ID`)
  REFERENCES `O_O_TARGET` (`ID`);

), (o_o_target.sql, DROP TABLE IF EXISTS `O_O_TARGET`;

CREATE TABLE `O_O_TARGET` (
    `ID` BIGINT(0) NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`ID`)
)
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COMMENT = ''
  ROW_FORMAT = Dynamic;

), (tree_node.sql, DROP TABLE IF EXISTS `TREE_NODE`;

CREATE TABLE `TREE_NODE` (
    `ID` BIGINT(0) NOT NULL AUTO_INCREMENT,
    `PARENT_ID` BIGINT(0) NOT NULL,
    PRIMARY KEY (`ID`)
)
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COMMENT = ''
  ROW_FORMAT = Dynamic;

ALTER TABLE `TREE_NODE` ADD CONSTRAINT `FK_TREE_NODE_TREE_NODE` 
    FOREIGN KEY (`PARENT_ID`)
  REFERENCES `TREE_NODE` (`ID`);

)]
    """

    private val postgresResult = """
[(all-tables.sql, DROP TABLE IF EXISTS "ENUM_TABLE" CASCADE;
DROP TABLE IF EXISTS "TREE_NODE" CASCADE;
DROP TABLE IF EXISTS "O_O_TARGET" CASCADE;
DROP TABLE IF EXISTS "O_M_SOURCE" CASCADE;
DROP TABLE IF EXISTS "O_M_TARGET" CASCADE;
DROP TABLE IF EXISTS "M_N_TARGET" CASCADE;
DROP TABLE IF EXISTS "M_O_SOURCE" CASCADE;
DROP TABLE IF EXISTS "M_O_TARGET" CASCADE;
DROP TABLE IF EXISTS "O_O_SOURCE" CASCADE;
DROP TABLE IF EXISTS "M_N_SOURCE" CASCADE;
DROP TABLE IF EXISTS "COMMENT_TABLE" CASCADE;

CREATE TABLE "ENUM_TABLE" (
    "ID" BIGSERIAL NOT NULL,
    "COMMON_ENUM" TEXT NOT NULL,
    "ORDINAL_ENUM" TEXT NOT NULL,
    "NAME_ENUM" TEXT NOT NULL,
    PRIMARY KEY ("ID")
);

COMMENT ON COLUMN "ENUM_TABLE"."ID" IS 'ID';

CREATE TABLE "TREE_NODE" (
    "ID" BIGSERIAL NOT NULL,
    "PARENT_ID" BIGINT NOT NULL,
    PRIMARY KEY ("ID")
);

CREATE TABLE "O_O_TARGET" (
    "ID" BIGSERIAL NOT NULL,
    PRIMARY KEY ("ID")
);

CREATE TABLE "O_M_SOURCE" (
    "ID" BIGSERIAL NOT NULL,
    PRIMARY KEY ("ID")
);

CREATE TABLE "O_M_TARGET" (
    "ID" BIGSERIAL NOT NULL,
    "SOURCE_ID" BIGINT NOT NULL,
    PRIMARY KEY ("ID")
);

CREATE TABLE "M_N_TARGET" (
    "ID" BIGSERIAL NOT NULL,
    PRIMARY KEY ("ID")
);

CREATE TABLE "M_O_SOURCE" (
    "ID" BIGSERIAL NOT NULL,
    "SOURCE_ID" BIGINT NOT NULL,
    PRIMARY KEY ("ID")
);

CREATE TABLE "M_O_TARGET" (
    "ID" BIGSERIAL NOT NULL,
    PRIMARY KEY ("ID")
);

CREATE TABLE "O_O_SOURCE" (
    "ID" BIGSERIAL NOT NULL,
    "TARGET_ID" BIGINT NOT NULL,
    PRIMARY KEY ("ID")
);

CREATE TABLE "M_N_SOURCE" (
    "ID" BIGSERIAL NOT NULL,
    PRIMARY KEY ("ID")
);

CREATE TABLE "COMMENT_TABLE" (
    "ID" BIGSERIAL NOT NULL,
    "NAME" TEXT NOT NULL,
    PRIMARY KEY ("ID")
);

COMMENT ON TABLE "COMMENT_TABLE" IS '注释测试';
COMMENT ON COLUMN "COMMENT_TABLE"."ID" IS 'ID';
COMMENT ON COLUMN "COMMENT_TABLE"."NAME" IS '名称';

ALTER TABLE "TREE_NODE" ADD CONSTRAINT "FK_TREE_NODE_TREE_NODE" 
    FOREIGN KEY ("PARENT_ID")
  REFERENCES "TREE_NODE" ("ID");

ALTER TABLE "O_M_TARGET" ADD CONSTRAINT "FK_O_M_TARGET_O_M_SOURCE" 
    FOREIGN KEY ("SOURCE_ID")
  REFERENCES "O_M_SOURCE" ("ID");

ALTER TABLE "M_O_SOURCE" ADD CONSTRAINT "FK_M_O_SOURCE_M_O_TARGET" 
    FOREIGN KEY ("SOURCE_ID")
  REFERENCES "M_O_TARGET" ("ID");

ALTER TABLE "O_O_SOURCE" ADD CONSTRAINT "FK_O_O_SOURCE_O_O_TARGET" 
    FOREIGN KEY ("TARGET_ID")
  REFERENCES "O_O_TARGET" ("ID");

DROP TABLE IF EXISTS "M_N_SOURCE_M_N_TARGET_MAPPING" CASCADE;

CREATE TABLE "M_N_SOURCE_M_N_TARGET_MAPPING" (
    "M_N_SOURCE_ID" BIGINT NOT NULL,
    "M_N_TARGET_ID" BIGINT NOT NULL
);

ALTER TABLE "M_N_SOURCE_M_N_TARGET_MAPPING" ADD CONSTRAINT "PK_M_N_SOURCE_M_N_TARGET_MAPPING" PRIMARY KEY ("M_N_SOURCE_ID","M_N_TARGET_ID");

ALTER TABLE "M_N_SOURCE_M_N_TARGET_MAPPING" ADD CONSTRAINT "M_N_SOURCE_M_N_TARGET_MAPPING_S" 
    FOREIGN KEY ("M_N_SOURCE_ID")
  REFERENCES "M_N_SOURCE" ("ID");

ALTER TABLE "M_N_SOURCE_M_N_TARGET_MAPPING" ADD CONSTRAINT "M_N_SOURCE_M_N_TARGET_MAPPING_T" 
    FOREIGN KEY ("M_N_TARGET_ID")
  REFERENCES "M_N_TARGET" ("ID");

COMMENT ON TABLE "M_N_SOURCE_M_N_TARGET_MAPPING" IS '与的映射关系表';

), (comment_table.sql, DROP TABLE IF EXISTS "COMMENT_TABLE" CASCADE;

CREATE TABLE "COMMENT_TABLE" (
    "ID" BIGSERIAL NOT NULL,
    "NAME" TEXT NOT NULL,
    PRIMARY KEY ("ID")
);

COMMENT ON TABLE "COMMENT_TABLE" IS '注释测试';
COMMENT ON COLUMN "COMMENT_TABLE"."ID" IS 'ID';
COMMENT ON COLUMN "COMMENT_TABLE"."NAME" IS '名称';

), (enum_table.sql, DROP TABLE IF EXISTS "ENUM_TABLE" CASCADE;

CREATE TABLE "ENUM_TABLE" (
    "ID" BIGSERIAL NOT NULL,
    "COMMON_ENUM" TEXT NOT NULL,
    "ORDINAL_ENUM" TEXT NOT NULL,
    "NAME_ENUM" TEXT NOT NULL,
    PRIMARY KEY ("ID")
);

COMMENT ON COLUMN "ENUM_TABLE"."ID" IS 'ID';

), (m_n_source.sql, DROP TABLE IF EXISTS "M_N_SOURCE" CASCADE;

CREATE TABLE "M_N_SOURCE" (
    "ID" BIGSERIAL NOT NULL,
    PRIMARY KEY ("ID")
);

DROP TABLE IF EXISTS "M_N_SOURCE_M_N_TARGET_MAPPING" CASCADE;

CREATE TABLE "M_N_SOURCE_M_N_TARGET_MAPPING" (
    "M_N_SOURCE_ID" BIGINT NOT NULL,
    "M_N_TARGET_ID" BIGINT NOT NULL
);

ALTER TABLE "M_N_SOURCE_M_N_TARGET_MAPPING" ADD CONSTRAINT "PK_M_N_SOURCE_M_N_TARGET_MAPPING" PRIMARY KEY ("M_N_SOURCE_ID","M_N_TARGET_ID");

ALTER TABLE "M_N_SOURCE_M_N_TARGET_MAPPING" ADD CONSTRAINT "M_N_SOURCE_M_N_TARGET_MAPPING_S" 
    FOREIGN KEY ("M_N_SOURCE_ID")
  REFERENCES "M_N_SOURCE" ("ID");

ALTER TABLE "M_N_SOURCE_M_N_TARGET_MAPPING" ADD CONSTRAINT "M_N_SOURCE_M_N_TARGET_MAPPING_T" 
    FOREIGN KEY ("M_N_TARGET_ID")
  REFERENCES "M_N_TARGET" ("ID");

COMMENT ON TABLE "M_N_SOURCE_M_N_TARGET_MAPPING" IS '与的映射关系表';

), (m_n_target.sql, DROP TABLE IF EXISTS "M_N_TARGET" CASCADE;

CREATE TABLE "M_N_TARGET" (
    "ID" BIGSERIAL NOT NULL,
    PRIMARY KEY ("ID")
);

), (m_o_source.sql, DROP TABLE IF EXISTS "M_O_SOURCE" CASCADE;

CREATE TABLE "M_O_SOURCE" (
    "ID" BIGSERIAL NOT NULL,
    "SOURCE_ID" BIGINT NOT NULL,
    PRIMARY KEY ("ID")
);

ALTER TABLE "M_O_SOURCE" ADD CONSTRAINT "FK_M_O_SOURCE_M_O_TARGET" 
    FOREIGN KEY ("SOURCE_ID")
  REFERENCES "M_O_TARGET" ("ID");

), (m_o_target.sql, DROP TABLE IF EXISTS "M_O_TARGET" CASCADE;

CREATE TABLE "M_O_TARGET" (
    "ID" BIGSERIAL NOT NULL,
    PRIMARY KEY ("ID")
);

), (o_m_source.sql, DROP TABLE IF EXISTS "O_M_SOURCE" CASCADE;

CREATE TABLE "O_M_SOURCE" (
    "ID" BIGSERIAL NOT NULL,
    PRIMARY KEY ("ID")
);

ALTER TABLE "O_M_TARGET" ADD CONSTRAINT "FK_O_M_TARGET_O_M_SOURCE" 
    FOREIGN KEY ("SOURCE_ID")
  REFERENCES "O_M_SOURCE" ("ID");

), (o_m_target.sql, DROP TABLE IF EXISTS "O_M_TARGET" CASCADE;

CREATE TABLE "O_M_TARGET" (
    "ID" BIGSERIAL NOT NULL,
    "SOURCE_ID" BIGINT NOT NULL,
    PRIMARY KEY ("ID")
);

), (o_o_source.sql, DROP TABLE IF EXISTS "O_O_SOURCE" CASCADE;

CREATE TABLE "O_O_SOURCE" (
    "ID" BIGSERIAL NOT NULL,
    "TARGET_ID" BIGINT NOT NULL,
    PRIMARY KEY ("ID")
);

ALTER TABLE "O_O_SOURCE" ADD CONSTRAINT "FK_O_O_SOURCE_O_O_TARGET" 
    FOREIGN KEY ("TARGET_ID")
  REFERENCES "O_O_TARGET" ("ID");

), (o_o_target.sql, DROP TABLE IF EXISTS "O_O_TARGET" CASCADE;

CREATE TABLE "O_O_TARGET" (
    "ID" BIGSERIAL NOT NULL,
    PRIMARY KEY ("ID")
);

), (tree_node.sql, DROP TABLE IF EXISTS "TREE_NODE" CASCADE;

CREATE TABLE "TREE_NODE" (
    "ID" BIGSERIAL NOT NULL,
    "PARENT_ID" BIGINT NOT NULL,
    PRIMARY KEY ("ID")
);

ALTER TABLE "TREE_NODE" ADD CONSTRAINT "FK_TREE_NODE_TREE_NODE" 
    FOREIGN KEY ("PARENT_ID")
  REFERENCES "TREE_NODE" ("ID");

)]
    """
}
