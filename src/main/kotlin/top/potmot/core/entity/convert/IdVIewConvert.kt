package top.potmot.core.entity.convert

import top.potmot.context.getContextOrGlobal
import top.potmot.entity.copy
import top.potmot.entity.dto.GenPropertyInput
import top.potmot.entity.dto.GenTableConvertView
import top.potmot.entity.dto.share.ReferenceTable
import top.potmot.enumeration.GenLanguage
import top.potmot.error.ConvertEntityException
import kotlin.jvm.Throws

/**
 * 从基础属性和关联属性生成 IdView
 */
@Throws(ConvertEntityException.Property::class)
fun createIdViewProperty(
    singularName: String,
    baseProperty: GenPropertyInput,
    baseColumn: GenTableConvertView.TargetOf_columns,
    associationProperty: GenPropertyInput,
    typeTable: ReferenceTable,
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
                comment = "$it ID View"
            }

            if (associationProperty.listType) {
                toPlural()
            }

            val language = getContextOrGlobal().language

            if (typeTable.pkColumns.size != 1)
                throw ConvertEntityException.property("IdView Property [${name}] cannot parse from multi pkColumn Table [${typeTable.name}]")

            // IdView 的基础类型为外键列对应类型
            type = typeMapping(
                baseColumn.copy(
                    typeCode = typeTable.pkColumns[0].typeCode,
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