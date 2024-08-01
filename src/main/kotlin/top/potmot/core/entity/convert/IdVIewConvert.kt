package top.potmot.core.entity.convert

import top.potmot.context.getContextOrGlobal
import top.potmot.core.database.meta.getTypeMeta
import top.potmot.entity.copy
import top.potmot.entity.dto.GenPropertyInput
import top.potmot.entity.dto.GenTableAssociationsView
import top.potmot.enumeration.GenLanguage

/**
 * 从基础属性和关联属性生成 IdView
 *
 * @param baseProperty 基础属性
 * @param associationProperty 关联属性
 */
fun createIdViewProperty(
    singularName: String,
    baseProperty: GenPropertyInput,
    baseColumn: GenTableAssociationsView.TargetOf_columns,
    associationProperty: GenPropertyInput,
    typeMapping: TypeMapping,
): GenPropertyInput =
    baseProperty.toEntity()
        .copy {
            name = singularName + "Id"
            idProperty = false
            idGenerationAnnotation = null
            typeNotNull = associationProperty.typeNotNull
            associationType = associationProperty.associationType
            idView = true
            idViewTarget = associationProperty.name
            keyProperty = false

            associationProperty.comment.takeIf { it.isNotBlank() }?.let {
                comment = "$it ID 视图"
            }

            if (associationProperty.listType) {
                toPlural()
            }

            val language = getContextOrGlobal().language

            // IdView 的基础类型为外键列对应类型
            type = typeMapping(
                baseColumn.getTypeMeta().copy(
                    typeNotNull =
                        if (listType && language == GenLanguage.JAVA)
                            // 当为列表属性时，java 为允许集合泛型使用，基本类型需要转化为包装类型，因此此时映射时必须调整为可空模式
                            false
                        else
                            associationProperty.typeNotNull
                )
            )
        }.let {
            GenPropertyInput(it)
        }