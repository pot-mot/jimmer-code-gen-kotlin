package top.potmot.model.idViewType

import org.springframework.boot.test.context.SpringBootTest
import top.potmot.enumeration.GenLanguage
import top.potmot.model.associations.AssociationsBaseTest
import top.potmot.model.createBaseModel
import top.potmot.entity.dto.GenConfig
import top.potmot.entity.dto.GenConfigProperties
import top.potmot.model.languageProperties

@SpringBootTest
class TestIdViewType : AssociationsBaseTest() {
    override fun getBaseModel() =
        createBaseModel(GRAPH_DATA)

    override fun getEntityResult(config: GenConfig) =
        when (config.language) {
            GenLanguage.KOTLIN -> kotlinResult
            GenLanguage.JAVA -> javaResult
        }

    override fun getTableDefineResult(config: GenConfig) =
"""
[(all-tables.sql, DROP TABLE IF EXISTS `SOURCE`;
DROP TABLE IF EXISTS `TARGET`;

CREATE TABLE `SOURCE` (
    `ID` BIGINT NOT NULL AUTO_INCREMENT,
    `TARGET_ID` INT NOT NULL,
    PRIMARY KEY (`ID`)
)
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COMMENT = ''
  ROW_FORMAT = Dynamic;

CREATE TABLE `TARGET` (
    `ID` INT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`ID`)
)
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COMMENT = ''
  ROW_FORMAT = Dynamic;

ALTER TABLE `SOURCE`
    ADD CONSTRAINT `FK_SOURCE_TARGET_ID`
        FOREIGN KEY (`TARGET_ID`)
            REFERENCES `TARGET` (`ID`);

), (source.sql, DROP TABLE IF EXISTS `SOURCE`;

CREATE TABLE `SOURCE` (
    `ID` BIGINT NOT NULL AUTO_INCREMENT,
    `TARGET_ID` INT NOT NULL,
    PRIMARY KEY (`ID`)
)
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COMMENT = ''
  ROW_FORMAT = Dynamic;

ALTER TABLE `SOURCE`
    ADD CONSTRAINT `FK_SOURCE_TARGET_ID`
        FOREIGN KEY (`TARGET_ID`)
            REFERENCES `TARGET` (`ID`);

), (target.sql, DROP TABLE IF EXISTS `TARGET`;

CREATE TABLE `TARGET` (
    `ID` INT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`ID`)
)
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COMMENT = ''
  ROW_FORMAT = Dynamic;

)]
"""

    companion object {
        @JvmStatic
        fun entityProperties(): List<GenConfigProperties> = languageProperties

        @JvmStatic
        fun tableDefineProperties() = listOf(GenConfigProperties())
    }
}
