package top.potmot.entity.extension

import org.babyfish.jimmer.ImmutableObjects.isLoaded
import top.potmot.entity.GenEntity
import top.potmot.entity.GenEntityProps
import top.potmot.entity.GenProperty

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
