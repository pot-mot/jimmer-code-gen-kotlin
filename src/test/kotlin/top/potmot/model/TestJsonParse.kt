package top.potmot.model

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.babyfish.jimmer.jackson.ImmutableModule
import org.junit.jupiter.api.Test
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import top.potmot.model.dto.GenTableColumnsInput

class TestJsonParse {
    private val mapper = jacksonObjectMapper()
        .registerModule(ImmutableModule())
        .registerModule(JavaTimeModule())

    @Test
    fun jsonParse() {
        mapper.readValue<GenTableColumnsInput>(
            """
            {
                "name": "table",
                "comment": "",
                "remark": "",
                "orderKey": 0,
                "type": "TABLE",
                "columns": [
                    {
                        "orderKey": 0,
                        "name": "id",
                        "comment": "ID",
                        "type": "BIGINT",
                        "typeCode": -5,
                        "typeNotNull": true,
                        "displaySize": 0,
                        "numericPrecision": 0,
                        "partOfPk": true,
                        "autoIncrement": false,
                        "partOfFk": false,
                        "partOfUniqueIdx": true,
                        "remark": ""
                    },
                    {
                        "orderKey": 0,
                        "name": "test",
                        "comment": "",
                        "type": "VARCHAR",
                        "typeCode": 12,
                        "typeNotNull": true,
                        "displaySize": 0,
                        "numericPrecision": 0,
                        "partOfPk": false,
                        "autoIncrement": false,
                        "partOfFk": false,
                        "partOfUniqueIdx": false,
                        "remark": ""
                    }
                ]
            }
        """.trimIndent()
        ).toString().let {
            println(it)
        }
    }
}
