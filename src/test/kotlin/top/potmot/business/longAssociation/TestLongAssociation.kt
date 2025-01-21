package top.potmot.business.longAssociation

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import top.potmot.core.business.dto.generate.DtoGenerator
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator

class TestLongAssociation {
    private val index = "${'$'}index"
    @Test
    fun `test longAssociationTarget toOne dto`() {
        assertEquals(
            """
(dto/Entity.dto, export EntityPackage.Entity

EntityListView {
    #allScalars
    longAssociationToOneProperty {
        name
    }
}

EntityDetailView {
    #allScalars
    longAssociationToOneProperty {
        #allScalars
    }
}

EntityOptionView {
    id
}

input EntityInsertInput {
    #allScalars
    -id
    longAssociationToOneProperty {
        #allScalars
        -id
    }
}

EntityUpdateFillView {
    #allScalars
    longAssociationToOneProperty {
        #allScalars
    }
}

input EntityUpdateInput {
    #allScalars
    id!
    longAssociationToOneProperty {
        #allScalars
    }
}

specification EntitySpec {
    eq(id)
    associatedIdEq(longAssociationToOneProperty)
})
            """.trimIndent(),
            DtoGenerator.generateDto(longAssociationToOneTargetEntity).let { it.path to it.content }.toString()
        )
    }

    @Test
    fun `test longAssociationTarget toOne view`() {
        val viewItems = Vue3ElementPlusViewGenerator.generateView(listOf(longAssociationToOneTargetEntity))

        TODO()
    }

    @Test
    fun `test longAssociationTarget toMany dto`() {
        assertEquals(
            """
(dto/Entity.dto, export EntityPackage.Entity

EntityListView {
    #allScalars
    longAssociationToOneProperty {
        name
    }
}

EntityDetailView {
    #allScalars
    longAssociationToOneProperty {
        #allScalars
    }
}

EntityOptionView {
    id
}

input EntityInsertInput {
    #allScalars
    -id
    longAssociationToOneProperty {
        #allScalars
        -id
    }
}

EntityUpdateFillView {
    #allScalars
    longAssociationToOneProperty {
        #allScalars
    }
}

input EntityUpdateInput {
    #allScalars
    id!
    longAssociationToOneProperty {
        #allScalars
    }
}

specification EntitySpec {
    eq(id)
    associatedIdIn(longAssociationToOneProperty) as longAssociationToOnePropertyIds
})
            """.trimIndent(),
            DtoGenerator.generateDto(longAssociationToManyTargetEntity).let { it.path to it.content }.toString()
        )
    }

    @Test
    fun `test longAssociationTarget toMany view`() {
        val viewItems = Vue3ElementPlusViewGenerator.generateView(listOf(longAssociationToManyTargetEntity))

        TODO()
    }
}