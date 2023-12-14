package top.potmot.generate

import org.babyfish.jimmer.sql.GenerationType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import top.potmot.core.entity.generate.JavaEntityCodeGenerator
import top.potmot.core.entity.generate.KotlinEntityCodeGenerator
import top.potmot.enumeration.AssociationType
import top.potmot.model.dto.GenEntityPropertiesView
import java.time.LocalDateTime

@SpringBootTest
@ActiveProfiles("test-kotlin", "mysql")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class EntityGenerateTest {
    @Test
    @Order(1)
    fun testEntityGenerate() {
        val basePackage = GenEntityPropertiesView.TargetOf_genPackage(
            id = 1,
            name = "test",
            parent = GenEntityPropertiesView.TargetOf_genPackage.TargetOf_parent_2(
                id = 1,
                name = "example",
                parent = GenEntityPropertiesView.TargetOf_genPackage.TargetOf_parent_2(
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
            orderKey = 1,
        )

        val manyToOneProperty = GenEntityPropertiesView.TargetOf_properties(
            id = 2,
            createdTime = LocalDateTime.now(),
            modifiedTime = LocalDateTime.now(),
            remark = "remark p1.\n${"remark p2 ".repeat(10)}\n",
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
            orderKey = 2,
        )


        val baseTable = GenEntityPropertiesView.TargetOf_table(
            id = 1,
            name = "table",
            comment = "comment",
            schema = GenEntityPropertiesView.TargetOf_table.TargetOf_schema_2(
                id = 1,
                name = "schema",
            )
        )

        val baseEntity = GenEntityPropertiesView(
            id = 1,
            createdTime = LocalDateTime.now(),
            modifiedTime = LocalDateTime.now(),
            remark = "remark p1.\n${"remark p2 ".repeat(10)}\n",
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
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.Key
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.Table

/**
 * comment
 * remark p1.
 * remark p2 remark p2 remark p2 remark p2
 * remark p2 remark p2 remark p2 remark p2
 * remark p2 remark p2
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
     * remark p1.
     * remark p2 remark p2 remark p2 remark p2
     * remark p2 remark p2 remark p2 remark p2
     * remark p2 remark p2
     */
    @Key
    @ManyToOne
    val manyToOneProperty: Entity?
}
"""

        assertEquals(
            kotlinExpected,
            KotlinEntityCodeGenerator().generate(baseEntity).second
                .replace(Regex("\n \\*\\s*@since\\s+\\d{4}-\\d{2}-\\d{2}\\s+\\d{2}:\\d{2}:\\d{2}"), "")
        )


        val javaExpected = """package com.example.test;

import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.babyfish.jimmer.sql.Id;
import org.babyfish.jimmer.sql.Key;
import org.babyfish.jimmer.sql.ManyToOne;
import org.babyfish.jimmer.sql.Table;
import org.jetbrains.annotations.Nullable;

/**
 * comment
 * remark p1.
 * remark p2 remark p2 remark p2 remark p2
 * remark p2 remark p2 remark p2 remark p2
 * remark p2 remark p2
 * 
 * @author Potmot
 */
@Entity
@Table(name = "schema.table")
public interface Entity {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;
    
    /**
     * comment
     * remark p1.
     * remark p2 remark p2 remark p2 remark p2
     * remark p2 remark p2 remark p2 remark p2
     * remark p2 remark p2
     */
    @Key
    @ManyToOne
    @Nullable
    Entity manyToOneProperty;
}
"""

        assertEquals(
            javaExpected,
            JavaEntityCodeGenerator().generate(baseEntity).second
                .replace(Regex("\n \\*\\s*@since\\s+\\d{4}-\\d{2}-\\d{2}\\s+\\d{2}:\\d{2}:\\d{2}"), "")
        )
    }
}
