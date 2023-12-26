package top.potmot.model.extension

import org.babyfish.jimmer.ImmutableObjects.isLoaded
import top.potmot.model.GenEntity
import top.potmot.model.GenProperty
import top.potmot.model.dto.GenEntityPropertiesView

fun GenEntity.getProperty(id: Long): GenProperty? {
    if (!isLoaded(this, "properties")) {
        return null
    }
    return this.properties.find { it.id == id }
}

fun GenEntity.getProperty(name: String): GenProperty? {
    if (!isLoaded(this, "properties")) {
        return null
    }
    return this.properties.find { it.name == name }
}

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
        return if (it.packagePath.isNotBlank()) {
            it.packagePath + "." + it.name
        } else {
            it.name
        }
    }

    typeTable?.let {table ->
        table.entity?.let {
            return if (it.packagePath.isNotBlank()) {
                it.packagePath + "." + it.name
            } else {
                it.name
            }
        }
    }

    return type
}
