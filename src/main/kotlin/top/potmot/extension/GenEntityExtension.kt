package top.potmot.extension

import org.babyfish.jimmer.ImmutableObjects
import top.potmot.model.GenEntity
import top.potmot.model.GenProperty

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

fun GenEntity.stringify(): String {
    TODO()
    return this.toString()
}

fun GenProperty.stringify(): String {
    TODO()
    return this.toString()
}
