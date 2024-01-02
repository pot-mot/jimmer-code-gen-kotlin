package top.potmot.dataSource

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import top.potmot.enumeration.DataSourceType
import top.potmot.model.extension.execute
import top.potmot.model.dto.GenDataSourceInput

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
            6,
            sqlExecuteResults.size,
        )

        assertEquals(
            0,
            sqlExecuteResults.filter { !it.success }.size,
        )
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
            6,
            sqlExecuteResults.size,
        )

        assertEquals(
            0,
            sqlExecuteResults.filter { !it.success }.size,
        )
    }
}
