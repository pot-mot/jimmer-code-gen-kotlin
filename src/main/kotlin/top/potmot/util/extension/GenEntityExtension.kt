package top.potmot.util.extension

import org.babyfish.jimmer.ImmutableObjects
import top.potmot.model.GenEntity
import top.potmot.model.GenProperty
import java.util.*

fun GenEntity.getProperty(id: Long): Optional<GenProperty> {
    if (!ImmutableObjects.isLoaded(this, "properties")) {
        return Optional.empty()
    }

    val result = this.properties.find { it.id == id }
    return Optional.ofNullable(result)
}

fun GenEntity.getProperty(name: String): Optional<GenProperty> {
    if (!ImmutableObjects.isLoaded(this, "properties")) {
        return Optional.empty()
    }

    val result = this.properties.find { it.propertyName == name }
    return Optional.ofNullable(result)
}
