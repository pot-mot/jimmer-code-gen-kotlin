package top.potmot.entity.generate

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import top.potmot.core.entity.generate.impl.java.JavaEntityCodeGenerator
import top.potmot.core.entity.generate.impl.kotlin.KotlinEntityCodeGenerator
import top.potmot.enumeration.AssociationType
import top.potmot.enumeration.TableType
import top.potmot.entity.dto.GenEntityPropertiesView
import top.potmot.entity.dto.GenPropertyView
import top.potmot.util.replaceSinceTimeComment
import java.time.LocalDateTime

/**
 * 验证 EntityGenerate 的基本功能
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class EntityGenerateTest {
    @Test
    @Order(1)
    fun testJavaEntityGenerate() {
        assertEquals(
            javaExpected,
            JavaEntityCodeGenerator.generateEntity(baseEntity).second
                .replaceSinceTimeComment()
        )
    }

    @Test
    @Order(2)
    fun testKotlinEntityGenerate() {
        assertEquals(
            kotlinExpected,
            KotlinEntityCodeGenerator.generateEntity(baseEntity).second
                .replaceSinceTimeComment()
        )
    }

    private val pkProperty = GenPropertyView(
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
        idGenerationAnnotation = "@GeneratedValue(strategy = GenerationType.SEQUENCE)",
        keyProperty = false,
        logicalDelete = false,
        idView = false,
        orderKey = 1,
    )

    private val manyToOneProperty = GenPropertyView(
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


    private val baseTable = GenEntityPropertiesView.TargetOf_table(
        id = 1,
        name = "table",
        type = TableType.TABLE
    )

    private val baseEntity = GenEntityPropertiesView(
        id = 1,
        createdTime = LocalDateTime.now(),
        modifiedTime = LocalDateTime.now(),
        remark = "remark p1.\n${"remark p2 ".repeat(10)}\n",
        name = "Entity",
        comment = "comment",
        author = "Potmot",
        table = baseTable,
        packagePath = "com.example.test",
        properties = listOf(
            pkProperty,
            manyToOneProperty
        )
    )

    private val kotlinExpected = """package com.example.test

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
@Table(name = "table")
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

    private val javaExpected = """package com.example.test;

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
@Table(name = "table")
public interface Entity {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id();

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
    Entity manyToOneProperty();
}
"""
}
