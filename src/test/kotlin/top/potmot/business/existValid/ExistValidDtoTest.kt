package top.potmot.business.existValid

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import top.potmot.core.business.dto.generate.DtoGenerator
import top.potmot.error.ModelException

class ExistValidDtoTest {
    @Test
    fun `test toOneAndEnumEntity dto`() {
        listOf(
            toOneAndEnumEntity,
            toOneDuplicateAndEnumEntity,
        ).map { testEntity ->
            assertEquals(
                """
(Entity.dto, export EntityPackage.Entity

EntityListView {
    #allScalars
    id(toOneProperty)
    id(toOneNullableProperty)
}

EntityDetailView {
    #allScalars
    id(toOneProperty)
    id(toOneNullableProperty)
}

EntityOptionView {
    id
}

input EntityInsertInput {
    #allScalars(this)
    -id
    id(toOneProperty)
    id(toOneNullableProperty)
}

input EntityUpdateInput {
    #allScalars(this)
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
    associatedIdEq(toOneProperty)
    associatedIdEq(toOneNullableProperty)
}

specification EntityExistByEnumPropertyAndEnumNullablePropertySpec {
    enumProperty
    enumNullableProperty
})
            """.trimIndent(),
                DtoGenerator.generateDto(testEntity).toString().trim()
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
(Entity.dto, export EntityPackage.Entity

EntityListView {
    #allScalars
    toOnePropertyId
    toOneNullablePropertyId
}

EntityDetailView {
    #allScalars
    toOnePropertyId
    toOneNullablePropertyId
}

EntityOptionView {
    id
}

input EntityInsertInput {
    #allScalars(this)
    -id
    toOnePropertyId
    toOneNullablePropertyId
}

input EntityUpdateInput {
    #allScalars(this)
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

specification EntityExistByToOnePropertyIdAndToOneNullablePropertyIdSpec {
    toOnePropertyId
    toOneNullablePropertyId
}

specification EntityExistByEnumPropertyAndEnumNullablePropertySpec {
    enumProperty
    enumNullableProperty
})
            """.trimIndent(),
                DtoGenerator.generateDto(testEntity).toString().trim()
            )
        }
    }
}