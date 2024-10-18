package top.potmot.entity.extension

import org.babyfish.jimmer.ImmutableObjects.isLoaded
import top.potmot.entity.GenEntity
import top.potmot.entity.GenEntityProps
import top.potmot.entity.GenProperty
import top.potmot.entity.dto.GenEntityBusinessView

fun GenEntity.getProperty(id: Long): GenProperty? {
    if (!isLoaded(this, GenEntityProps.PROPERTIES)) {
        return null
    }
    return this.properties.find { it.id == id }
}

fun GenEntity.getProperty(name: String): GenProperty? {
    if (!isLoaded(this, GenEntityProps.PROPERTIES)) {
        return null
    }
    return this.properties.find { it.name == name }
}

/**
 * 获取全部上级实体
 */
fun GenEntityBusinessView.allSuperEntities(): List<GenEntityBusinessView> {
    val result = superEntities ?: listOf()
    return result + result.flatMap { it.allSuperEntities() }
}