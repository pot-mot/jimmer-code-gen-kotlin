package top.potmot.generate

import org.babyfish.jimmer.sql.GenerationType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import top.potmot.core.template.entity.javaClassStringify
import top.potmot.core.template.entity.kotlinClassStringify
import top.potmot.enumeration.AssociationType
import top.potmot.model.dto.GenEntityPropertiesView
import java.time.LocalDateTime

@SpringBootTest
@ActiveProfiles("test-kotlin", "mysql")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class EntityGenerateTest {
    @Test
    @Order(1)
    fun testKotlinEntityGenerate() {
        val basePackage = GenEntityPropertiesView.TargetOf_genPackage(
            id = 1,
            name = "test",
            parent = GenEntityPropertiesView.TargetOf_genPackage.TargetOf_parent(
                id = 1,
                name = "example",
                parent = GenEntityPropertiesView.TargetOf_genPackage.TargetOf_parent(
                    id = 1,
                    name = "com"
                )
            )
        )

        val pkProperty = GenEntityPropertiesView.TargetOf_properties(
            entityId = 1,
            id = 1,
            createdTime = LocalDateTime.now(),
            modifiedTime = LocalDateTime.now(),
            remark = "",
            name = "id",
            comment = "ID",
            type = "kotlin.Long",
            listType = false,
            typeNotNull = true,
            idProperty = true,
            idGenerationType = GenerationType.SEQUENCE,
            keyProperty = false,
            logicalDelete = false,
            idView = false,
        )

        val manyToOneProperty = GenEntityPropertiesView.TargetOf_properties(
            id = 2,
            createdTime = LocalDateTime.now(),
            modifiedTime = LocalDateTime.now(),
            remark = "remark",
            name = "manyToOneProperty",
            comment = "comment",
            type = "Entity",
            listType = false,
            typeNotNull = false,
            idProperty = false,
            keyProperty = true,
            logicalDelete = false,
            idView = false,
            associationType = AssociationType.MANY_TO_ONE,
            associationAnnotation = "@ManyToOne",
            entityId = 1,
        )


        val baseTable = GenEntityPropertiesView.TargetOf_table(
            id = 1,
            name = "table",
            comment = "comment",
            schema = GenEntityPropertiesView.TargetOf_table.TargetOf_schema(
                id = 1,
                name = "schema",
            )
        )

        val baseEntity = GenEntityPropertiesView(
            id = 1,
            createdTime = LocalDateTime.now(),
            modifiedTime = LocalDateTime.now(),
            remark = "remark",
            name = "Entity",
            comment = "comment",
            author = "Potmot",
            orderKey = 1,
            table = baseTable,
            genPackage = basePackage,
            properties = listOf(
                pkProperty,
                manyToOneProperty
            )
        )

        val kotlinExpected = """package com.example.test

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.Table
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Key
import org.babyfish.jimmer.sql.ManyToOne

/**
 * comment
 * remark
 * 
 * @author Potmot
 */
@Entity
@Table(name = "schema.table")
interface Entity {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Long

    /**
     * comment
     * remark
     */
    @Key
    @ManyToOne
    val manyToOneProperty: Entity?
}"""

        assertEquals(
            kotlinExpected,
            baseEntity.kotlinClassStringify()
                .replace(Regex("\n \\*\\s*@since\\s+\\d{4}-\\d{2}-\\d{2}\\s+\\d{2}:\\d{2}:\\d{2}"), "")
        )


        val javaExpected = """package com.example.test;

import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.Table;
import org.babyfish.jimmer.sql.Id;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.jetbrains.annotations.NotNull;
import org.babyfish.jimmer.sql.Key;
import org.babyfish.jimmer.sql.ManyToOne;

/**
 * comment
 * remark
 * 
 * @author Potmot
 */
@Entity
@Table(name = "schema.table")
interface Entity {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @NotNull
    Long id;

    /**
     * comment
     * remark
     */
    @Key
    @ManyToOne
    Entity manyToOneProperty;
}"""

        assertEquals(
            javaExpected,
            baseEntity.javaClassStringify()
                .replace(Regex("\n \\*\\s*@since\\s+\\d{4}-\\d{2}-\\d{2}\\s+\\d{2}:\\d{2}:\\d{2}"), "")
        )
    }
}
