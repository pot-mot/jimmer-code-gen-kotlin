package top.potmot.model.extension

import org.babyfish.jimmer.ImmutableObjects
import top.potmot.config.GenConfig
import top.potmot.model.GenEntity
import top.potmot.model.GenProperty
import top.potmot.model.dto.GenEntityPropertiesView

fun GenEntity.getProperty(id: Long): GenProperty? {
    if (!ImmutableObjects.isLoaded(this, "properties")) {
        return null
    }
    return this.properties.find { it.id == id }
}

fun GenEntity.getProperty(name: String): GenProperty? {
    if (!ImmutableObjects.isLoaded(this, "properties")) {
        return null
    }
    return this.properties.find { it.name == name }
}

fun GenEntityPropertiesView.packagePath(): String =
    genPackage?.toEntity()?.toPackagePath() ?: GenConfig.defaultPackagePath

fun GenEntityPropertiesView.TargetOf_properties.TargetOf_typeTable_2.packagePath(): String =
    entity?.genPackage?.toEntity()?.toPackagePath() ?: GenConfig.defaultPackagePath

fun GenEntityPropertiesView.TargetOf_properties.TargetOf_enum_2.packagePath(): String =
    genPackage?.toEntity()?.toPackagePath() ?: GenConfig.defaultPackagePath

/**
 * 获取属性的简单类型名
 * 判断过程如下：
 *  1. 优先采用枚举类型
 *  2. 使用 typeTable 对应的 Entity
 *  3. 使用映射后的 type
 */
fun GenEntityPropertiesView.TargetOf_properties.shortType(): String {
    enum?.let { return it.name }

    val baseType =
        typeTable?.entity?.name ?: let {
            type.split(".").last()
        }

    return if (listType) {
        "List<$baseType>"
    } else {
        baseType
    }
}

fun GenEntityPropertiesView.TargetOf_properties.fullType(): String {
    enum?.let {
        val packagePath = it.packagePath()

        return if (packagePath.isNotBlank()) {
            packagePath + "." + it.name
        } else {
            it.name
        }
    }

    typeTable?.let {table ->
        table.entity?.let {entity ->
            val packagePath = table.packagePath()

            return if (packagePath.isNotBlank()) {
                packagePath + "." + entity.name
            } else {
                entity.name
            }
        }
    }

    return type
}
