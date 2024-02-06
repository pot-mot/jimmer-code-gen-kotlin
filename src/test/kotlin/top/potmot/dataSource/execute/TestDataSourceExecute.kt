package top.potmot.dataSource.execute

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import top.potmot.dataSource.h2DataSource
import top.potmot.dataSource.mysqlDataSource
import top.potmot.dataSource.postgresDataSource
import top.potmot.model.extension.execute

/**
 * 校验两种数据源下 sql execute 是否存在问题
 * 其中数据源的基本数据基于默认连接配置，所以如果需要在自己的环境中进行测试，请修改对于 dataSource
 */
@SpringBootTest
@ActiveProfiles("test-kotlin", "h2")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class TestDataSourceExecute {
    @Test
    @Order(1)
    fun testMysqlExecute() {
        val dataSource = mysqlDataSource.toEntity()

        val sqlExecuteResults = dataSource.execute(
            "jimmer_code_gen", """
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
        """
        )

        assertEquals(
            7,
            sqlExecuteResults.size,
        )

        assertEquals(
            0,
            sqlExecuteResults.filter { !it.success }.size,
        )
    }

    @Test
    @Order(2)
    fun testPostgresExecute() {
        val dataSource = postgresDataSource.toEntity()

        val sqlExecuteResults = dataSource.execute("jimmer_code_gen", """
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

        assertEquals(
            7,
            sqlExecuteResults.size,
        )

        assertEquals(
            0,
            sqlExecuteResults.filter { !it.success }.size,
        )
    }

    @Test
    @Order(1)
    fun testH2Execute() {
        val dataSource = h2DataSource.toEntity()

        val sqlExecuteResults = dataSource.execute(
            "jimmer_code_gen", """
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
        """
        )

        assertEquals(
            7,
            sqlExecuteResults.size,
        )

        assertEquals(
            0,
            sqlExecuteResults.filter { !it.success }.size,
        )
    }
}
