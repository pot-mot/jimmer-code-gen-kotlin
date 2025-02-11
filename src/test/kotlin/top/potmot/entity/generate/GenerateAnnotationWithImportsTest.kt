package top.potmot.entity.generate

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import top.potmot.core.entity.generate.impl.java.JavaEntityCodeGenerator
import top.potmot.core.entity.generate.impl.kotlin.KotlinEntityCodeGenerator
import top.potmot.enumeration.TableType
import top.potmot.entity.dto.GenEntityGenerateView
import top.potmot.entity.sub.AnnotationWithImports
import top.potmot.utils.replaceSinceTimeComment
import java.time.LocalDateTime
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test", "h2", "hide-sql")
class GenerateAnnotationWithImportsTest {
    @Test
    fun testJavaEntityGenerate() {
        assertEquals(
            javaExpected,
            JavaEntityCodeGenerator.generateEntity(baseEntity).content
                .replaceSinceTimeComment()
        )
    }

    @Test
    fun testKotlinEntityGenerate() {
        assertEquals(
            kotlinExpected,
            KotlinEntityCodeGenerator.generateEntity(baseEntity).content
                .replaceSinceTimeComment()
        )
    }

    private val idProperty = GenEntityGenerateView.TargetOf_properties(
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
        generatedId = true,
        keyProperty = false,
        keyGroups = emptyList(),
        logicalDelete = false,
        idView = false,
        orderKey = 1,
    )

    private val otherAnnotationProperty = GenEntityGenerateView.TargetOf_properties(
        id = 2,
        createdTime = LocalDateTime.now(),
        modifiedTime = LocalDateTime.now(),
        remark = "remark p1.\n${"remark p2 ".repeat(10)}\n",
        name = "otherAnnotationProperty",
        comment = "comment",
        type = "Int",
        otherAnnotation = AnnotationWithImports(
            imports = listOf("package.Annotation"),
            annotations = listOf("@Annotation(\n    value = \"a\"\n)")
        ),
        listType = false,
        typeNotNull = false,
        idProperty = false,
        keyProperty = true,
        keyGroups = emptyList(),
        logicalDelete = false,
        idView = false,
        associationType = null,
        entityId = 1,
        orderKey = 2,
    )


    private val baseTable = GenEntityGenerateView.TargetOf_table(
        id = 1,
        name = "table",
        type = TableType.TABLE
    )

    private val baseEntity = GenEntityGenerateView(
        id = 1,
        createdTime = LocalDateTime.now(),
        modifiedTime = LocalDateTime.now(),
        remark = "remark p1.\n${"remark p2 ".repeat(10)}\n",
        name = "Entity",
        comment = "comment",
        author = "Potmot",
        table = baseTable,
        packagePath = "com.example.test",
        superEntities = emptyList(),
        otherAnnotation = AnnotationWithImports(
            imports = listOf("package.Annotation"),
            annotations = listOf("@Annotation(\n    value = \"a\"\n)")
        ),
        properties = listOf(
            idProperty,
            otherAnnotationProperty
        )
    )

    private val kotlinExpected = """package com.example.test

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.Key
import org.babyfish.jimmer.sql.Table
import package.Annotation

/**
 * comment
 * remark p1.
 * remark p2 remark p2 remark p2 remark p2 remark p2 remark p2 remark p2 remark p2
 * remark p2 remark p2
 * 
 * @author Potmot
 */
@Entity
@Table(name = "table")
@Annotation(
    value = "a"
)
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
     * remark p2 remark p2 remark p2 remark p2 remark p2 remark p2 remark p2 remark p2
     * remark p2 remark p2
     */
    @Key
    @Annotation(
        value = "a"
    )
    val otherAnnotationProperty: Int?
}
"""

    private val javaExpected = """package com.example.test;

import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.babyfish.jimmer.sql.Id;
import org.babyfish.jimmer.sql.Key;
import org.babyfish.jimmer.sql.Table;
import org.jetbrains.annotations.Nullable;
import package.Annotation;

/**
 * comment
 * remark p1.
 * remark p2 remark p2 remark p2 remark p2 remark p2 remark p2 remark p2 remark p2
 * remark p2 remark p2
 * 
 * @author Potmot
 */
@Entity
@Table(name = "table")
@Annotation(
    value = "a"
)
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
     * remark p2 remark p2 remark p2 remark p2 remark p2 remark p2 remark p2 remark p2
     * remark p2 remark p2
     */
    @Key
    @Annotation(
        value = "a"
    )
    @Nullable
    Int otherAnnotationProperty();
}
"""
}
