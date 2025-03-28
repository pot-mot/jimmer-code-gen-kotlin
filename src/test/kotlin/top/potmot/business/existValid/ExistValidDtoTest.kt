package top.potmot.business.existValid

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import top.potmot.core.business.dto.generate.DtoGenerator
import top.potmot.core.business.meta.RootEntityBusiness
import top.potmot.error.ModelException

class ExistValidDtoTest {
    private val RootEntityBusiness.result
        get() = DtoGenerator.generateDto(this).let { it.path to it.content }.toString()

    @Test
    fun `test toOneAndEnumEntity dto`() {
        listOf(
            toOneAndEnumEntity,
            toOneDuplicateAndEnumEntity,
        ).map { testEntity ->
            assertEquals(
                """
(dto/Entity.dto, export EntityPackage.Entity

EntityListView {
    #allScalars
    id(toOneProperty)
    id(toOneNullableProperty)
}

EntityDetailView {
    #allScalars
    id(toOneProperty)
    id(toOneNullableProperty)
    id(toManyProperties) as toManyPropertyIds
}

EntityOptionView {
    id
}

input EntityInsertInput {
    #allScalars
    -id
    id(toOneProperty)
    id(toOneNullableProperty)
}

EntityUpdateFillView {
    #allScalars
    id(toOneProperty)
    id(toOneNullableProperty)
}

input EntityUpdateInput {
    #allScalars
    id!
    id(toOneProperty)
    id(toOneNullableProperty)
}

specification EntitySpec {
    eq(id)
    eq(enumProperty)
    eq(enumNullableProperty)
    associatedIdEq(toOneProperty)
    associatedIdEq(toOneNullableProperty)
    associatedIdIn(toManyProperties) as toManyPropertyIds
}

specification EntityExistByToOnePropertyAndToOneNullablePropertySpec {
    ne(id) as id
    associatedIdEq(toOneProperty)
    associatedIdEq(toOneNullableProperty)
}

specification EntityExistByEnumPropertyAndEnumNullablePropertySpec {
    ne(id) as id
    eq(enumProperty)
    eq(enumNullableProperty)
})
            """.trimIndent(),
                testEntity.result
            )
        }
    }

    @Test
    fun `test toManyEntity dto`() {
        listOf(
            toManyEntity,
            toManyIdViewEntity,
        ).forEach { testEntity ->
            assertThrows<ModelException.IndexRefPropertyCannotBeList> {
                DtoGenerator.generateDto(testEntity)
            }
        }
    }

    @Test
    fun `test toOneIdViewAndEnumEntity dto`() {
        listOf(
            toOneIdViewAndEnumEntity,
            toOneIdViewDuplicateAndEntity,
        ).forEach { testEntity ->
            assertEquals(
                """
(dto/Entity.dto, export EntityPackage.Entity

EntityListView {
    #allScalars
    toOnePropertyId
    toOneNullablePropertyId
}

EntityDetailView {
    #allScalars
    toOnePropertyId
    toOneNullablePropertyId
    toManyPropertyIds
}

EntityOptionView {
    id
}

input EntityInsertInput {
    #allScalars
    -id
    toOnePropertyId
    toOneNullablePropertyId
}

EntityUpdateFillView {
    #allScalars
    toOnePropertyId
    toOneNullablePropertyId
}

input EntityUpdateInput {
    #allScalars
    id!
    toOnePropertyId
    toOneNullablePropertyId
}

specification EntitySpec {
    eq(id)
    eq(enumProperty)
    eq(enumNullableProperty)
    associatedIdEq(toOneProperty)
    associatedIdEq(toOneNullableProperty)
    associatedIdIn(toManyProperties) as toManyPropertyIds
}

specification EntityExistByToOnePropertyAndToOneNullablePropertySpec {
    ne(id) as id
    associatedIdEq(toOneProperty)
    associatedIdEq(toOneNullableProperty)
}

specification EntityExistByEnumPropertyAndEnumNullablePropertySpec {
    ne(id) as id
    eq(enumProperty)
    eq(enumNullableProperty)
})
            """.trimIndent(),
                testEntity.result
            )
        }
    }
}