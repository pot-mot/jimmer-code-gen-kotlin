package top.potmot.core.generate

import org.babyfish.jimmer.ImmutableObjects
import org.babyfish.jimmer.kt.new
import org.babyfish.jimmer.sql.kt.fetcher.newFetcher
import top.potmot.core.template.entity.EntityBuilder
import top.potmot.enumeration.GenLanguage
import top.potmot.model.GenEntity
import top.potmot.model.GenEnum
import top.potmot.model.GenPackage
import top.potmot.model.GenPackageProps
import top.potmot.model.GenProperty
import top.potmot.model.by
import top.potmot.model.copy
import top.potmot.model.dto.GenEntityPropertiesView
import top.potmot.model.dto.GenEntityPropertiesView.TargetOf_properties.TargetOf_enum_2 as PropertyEnum

fun generatePackage(genPackage: GenPackage, language: GenLanguage?): TreeItem<String> =
    genPackage.toTreeItem(language.getEntityBuilder())

private fun GenPackage.toTreeItem(entityBuilder: EntityBuilder): TreeItem<String> =
    TreeItem(
        key = name,
        value = getContentStringMap(entityBuilder),
        children = children.map { it.toTreeItem(entityBuilder) }
    )

private fun GenPackage.getContentStringMap(
    entityBuilder: EntityBuilder
): Map<String, String> {
    val result = mutableMapOf<String, String>()

    val complexPackage = this

    val parentPathPackage = new(GenPackage::class).by {
        id = complexPackage.id
        name = complexPackage.name
        if (ImmutableObjects.isLoaded(complexPackage, GenPackageProps.PARENT)) {
            this.parent = complexPackage.parent
        }
    }

    if (ImmutableObjects.isLoaded(complexPackage, GenPackageProps.ENTITIES)) {
        entities.forEach {
            result += entityBuilder.generate(GenEntityPropertiesView(it.copy {
                genPackage = parentPathPackage
            }))
        }
    }

    if (ImmutableObjects.isLoaded(complexPackage, GenPackageProps.ENUMS)) {
        enums.forEach {
            result += entityBuilder.generate(PropertyEnum(it.copy {
                genPackage = parentPathPackage
            }))
        }
    }

    return result
}

val packageEntityPropertiesFetcher = newFetcher(GenProperty::class)
    .by(GenEntityPropertiesView.TargetOf_properties.METADATA.fetcher) {
        enum(false)
    }

val packageEntityFetcher = newFetcher(GenEntity::class)
    .by(GenEntityPropertiesView.METADATA.fetcher) {
        genPackage(false)
        properties(packageEntityPropertiesFetcher)
    }

val packageEnumFetcher = newFetcher(GenEnum::class)
    .by(PropertyEnum.METADATA.fetcher) {
        genPackage(false)
    }

val packageGenerateFetcher = newFetcher(GenPackage::class).by {
    parent({ recursive() }) {
        name()
    }
    createdTime()
    modifiedTime()
    remark()
    name()
    orderKey()
    entities(packageEntityFetcher)
    enums(packageEnumFetcher)
}

val packageParentFetcher = newFetcher(GenPackage::class).by {
    name()
}

val packageGenerateTreeFetcher = newFetcher(GenPackage::class)
    .by(packageGenerateFetcher) {
        parent(packageParentFetcher) { recursive() }
        children(packageGenerateFetcher) { recursive() }
    }
