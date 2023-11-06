package top.potmot.core.convert

import org.babyfish.jimmer.kt.new
import org.babyfish.jimmer.sql.GenerationType
import top.potmot.config.GenConfig
import top.potmot.core.immutable.copyProperties
import top.potmot.enumeration.AssociationType.*
import top.potmot.model.GenEntity
import top.potmot.model.GenProperty
import top.potmot.model.GenTypeMapping
import top.potmot.model.by
import top.potmot.model.copy
import top.potmot.model.dto.GenTableAssociationsView

fun GenTableAssociationsView.toGenEntity(
    typeMappings: List<GenTypeMapping> = emptyList(),
): GenEntity =
    convertTableToEntity(this, typeMappings)

private fun convertTableToEntity(
    table: GenTableAssociationsView,
    typeMappings: List<GenTypeMapping> = emptyList(),
): GenEntity {
    val baseEntity = tableToEntity(table)

    val properties = table.columns.flatMap { column ->
        columnToProperties(
            column,
            typeMappings
        )
    }

    return baseEntity.copy {
        this.properties = properties
    }
}

/**
 * 表到实体转换
 */
private fun tableToEntity(
    genTable: GenTableAssociationsView,
): GenEntity {
    return new(GenEntity::class).by {
        tableId = genTable.toEntity().id
        author = GenConfig.author
        name = tableNameToClassName(genTable.name)
        comment = genTable.comment.clearTableComment()
    }
}

/**
 * 列到属性转换
 * 具备关联时将生成关联字段 和 IdView
 *
 * warning: 为保证保存时关联成立，请保证对应 column 具有 id
 */
fun columnToProperties(
    genColumn: GenTableAssociationsView.TargetOf_columns,
    typeMappings: List<GenTypeMapping> = emptyList(),
): List<GenProperty> {
    val properties = mutableListOf<GenProperty>()

    genColumn.outAssociations.forEach {
        properties += when(it.associationType) {
            ONE_TO_ONE -> genColumn.toOneToOneProperty(it, typeMappings)
            MANY_TO_ONE -> genColumn.toManyToOneProperty(it, typeMappings)
            ONE_TO_MANY -> genColumn.toOneToManyProperty(it, typeMappings)
            MANY_TO_MANY -> genColumn.toManyToManyProperty(it, typeMappings)
        }
    }

    if (properties.isEmpty()) {
        val baseProperty = genColumn.toBaseProperty(typeMappings)
        properties += if (genColumn.partOfPk) {
            new(GenProperty::class).by {
                copyProperties(baseProperty, this)

                idProperty = true
                typeNotNull = true
                keyProperty = false
                listType = false
                logicalDelete = false
                idView = false
                if (genColumn.autoIncrement) {
                    idGenerationType = GenerationType.IDENTITY
                }
            }
        } else {
            baseProperty
        }
    }

    genColumn.inAssociations.forEach {
        properties += when(it.associationType) {
            ONE_TO_ONE -> genColumn.getOneToOneProperty(it, typeMappings)
            MANY_TO_ONE -> genColumn.getOneToManyProperty(it, typeMappings)
            ONE_TO_MANY -> genColumn.getManyToOneProperty(it, typeMappings)
            MANY_TO_MANY -> genColumn.getManyToManyProperty(it, typeMappings)
        }
    }

    return properties
}
