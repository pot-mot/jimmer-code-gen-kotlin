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
class TestLongName(
    @Autowired val modelService: ModelService,
    @Autowired val previewService: PreviewService
) {
    @Test
    @Order(1)
    fun testCreateMysqlLongName() {
        modelInput.dataSourceType = DataSourceType.MySQL

        val id = modelService.save(modelInput)

        val tableDefineCodes = previewService.previewModelSql(id)

        assertEquals(
            mysqlResult.trim(),
            tableDefineCodes.toString().trim()
        )
    }

    @Test
    @Order(2)
    fun testCreatePostgresLongName() {
        modelInput.dataSourceType = DataSourceType.PostgreSQL

        val id = modelService.save(modelInput)

        val tableDefineCodes = previewService.previewModelSql(id)

        assertEquals(
            postgresResult.trim(),
            tableDefineCodes.toString().trim()
        )
    }

    private val modelInput = getBaseModel()
        .let {
            it.graphData = "{\"json\":{\"cells\":[{\"position\":{\"x\":345,\"y\":196},\"size\":{\"width\":1085,\"height\":94},\"view\":\"vue-shape-view\",\"shape\":\"TABLE_NODE\",\"id\":\"8278cfde-b081-4685-9769-8bc6c3c47ac3\",\"data\":{\"table\":{\"name\":\"sdaadsdassddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd\",\"comment\":\"\",\"remark\":\"\",\"orderKey\":0,\"type\":\"TABLE\",\"columns\":[{\"orderKey\":1,\"name\":\"sdaadsdassddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd\",\"comment\":\"\",\"typeCode\":-5,\"overwriteByType\":false,\"type\":\"BIGINT\",\"typeNotNull\":true,\"displaySize\":0,\"numericPrecision\":0,\"partOfPk\":true,\"autoIncrement\":true,\"remark\":\"\",\"logicalDelete\":false,\"businessKey\":false},{\"orderKey\":2,\"name\":\"sdaadsdassddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd1\",\"comment\":\"\",\"typeCode\":12,\"overwriteByType\":false,\"type\":\"VARCHAR\",\"typeNotNull\":true,\"displaySize\":\"500\",\"numericPrecision\":0,\"partOfPk\":false,\"autoIncrement\":false,\"remark\":\"\",\"logicalDelete\":false,\"businessKey\":false}],\"indexes\":[{\"name\":\"uidx_first_column\",\"uniqueIndex\":true,\"columns\":[{\"name\":\"sdaadsdassddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd1\"}]},{\"name\":\"idx_multi_columns\",\"uniqueIndex\":false,\"columns\":[{\"name\":\"sdaadsdassddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd\"},{\"name\":\"sdaadsdassddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd1\"}]}]}},\"zIndex\":1,\"ports\":{\"groups\":{\"COLUMN_PORT_GROUP\":{\"position\":\"COLUMN_PORT\",\"markup\":[{\"tagName\":\"rect\",\"selector\":\"COLUMN_PORT_SELECTOR\"}],\"attrs\":{\"COLUMN_PORT_SELECTOR\":{\"magnet\":true,\"fill\":\"rgba(0,0,0,0)\",\"height\":30,\"width\":200}}}},\"items\":[{\"group\":\"COLUMN_PORT_GROUP\",\"id\":\"cc6d4116-3a3d-4e81-a084-d822db3fc61f\",\"attrs\":{\"COLUMN_PORT_SELECTOR\":{\"width\":1085}}},{\"group\":\"COLUMN_PORT_GROUP\",\"id\":\"bd1cb7b6-8f8e-4c1f-9dc2-5993896609dd\",\"attrs\":{\"COLUMN_PORT_SELECTOR\":{\"width\":1085}}}]}}]},\"zoom\":0.7000000000000001,\"transform\":\"matrix(0.7000000000000001,0,0,0.7000000000000001,51.84998917324185,202.49998082433396)\"}"
            it
        }

    private val mysqlResult = """
[(all-tables.sql, DROP TABLE IF EXISTS `SDAADSDASSDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD_BB0D4D2B`;

CREATE TABLE `SDAADSDASSDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD_BB0D4D2B` (
    `SDAADSDASSDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD_BB0D4D2B` BIGINT(0) NOT NULL AUTO_INCREMENT,
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
    `SDAADSDASSDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD_BB0D4D2B` BIGINT(0) NOT NULL AUTO_INCREMENT,
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

    private val postgresResult = """
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
}
