package top.potmot.dataSource

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import top.potmot.enumeration.DataSourceType
import top.potmot.model.extension.execute
import top.potmot.model.dto.GenDataSourceInput


@SpringBootTest
@ActiveProfiles("test-kotlin", "mysql")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class TestDataSourceExecute {
    @Test
    @Order(1)
    fun testMysqlDataSource() {
        val dataSource = GenDataSourceInput(
            name = "test",
            host = "127.0.0.1",
            port = "3307",
            urlSuffix = "",
            type = DataSourceType.MySQL,
            username = "root",
            password = "root",
            orderKey = 0L,
            remark = "test"
        ).toEntity()

        dataSource.execute("jimmer_code_gen", """
            -- remove existed table
            DROP TABLE IF EXISTS `test`;
            
            -- create table
            CREATE TABLE `test` (
                `id`           BIGINT       NOT NULL AUTO_INCREMENT,
                `name`         VARCHAR(50)  NOT NULL,
                `key_property` VARCHAR(50)  NOT NULL,
                PRIMARY KEY (`id`)
            );
            
            -- insert data
            INSERT INTO test (`name`, `key_property`) VALUES ('name1', 'key1');
            INSERT INTO test (`name`, `key_property`) VALUES ('name2', 'key2');
            INSERT INTO test (`name`, `key_property`) VALUES ('name3', 'key3');
            
            -- remove existed table
            DROP TABLE `test`;
        """)
    }

    @Test
    @Order(2)
    fun testPostgreSqlDataSource() {
        val dataSource = GenDataSourceInput(
            name = "test",
            host = "127.0.0.1",
            port = "5432",
            urlSuffix = "/postgres",
            type = DataSourceType.PostgreSQL,
            username = "postgres",
            password = "root",
            orderKey = 0L,
            remark = "test"
        ).toEntity()

        dataSource.execute("jimmer_code_gen", """
            -- remove existed table
            DROP TABLE IF EXISTS "test";
            
            -- create table
            CREATE TABLE "test" (
                "id"           BIGSERIAL    PRIMARY KEY,
                "name"         TEXT         NOT NULL,
                "key_property" TEXT         NOT NULL
            );
            
            -- insert data
            INSERT INTO test ("name", "key_property") VALUES ('name1', 'key1');
            INSERT INTO test ("name", "key_property") VALUES ('name2', 'key2');
            INSERT INTO test ("name", "key_property") VALUES ('name3', 'key3');
            
            -- remove existed table
            DROP TABLE "test";
        """)
    }
}
