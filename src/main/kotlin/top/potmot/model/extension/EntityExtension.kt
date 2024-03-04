package top.potmot.model.extension

import org.babyfish.jimmer.ImmutableObjects.isLoaded
import top.potmot.model.GenEntity
import top.potmot.model.GenEntityProps
import top.potmot.model.GenProperty

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
