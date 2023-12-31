package top.potmot.utils.zip

import java.io.ByteArrayOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

private fun ZipOutputStream.putEntry(
    key: String,
    value: ByteArray
) {
    putNextEntry(ZipEntry(key))
    write(value)
    closeEntry()
}

private fun listToZipByteArray(
    list: List<Pair<String, ByteArray>>,
    formatCount: (key: String, count: Long) -> String = {key, count ->
        val lastIndex = key.lastIndexOf(".")
        "${key.substring(0, lastIndex)}($count)${key.substring(lastIndex)}"
    }
): ByteArray {
    val byteArrayStream = ByteArrayOutputStream()

    val zipStream = ZipOutputStream(byteArrayStream)

    val keyCountMap = mutableMapOf<String, Long>()

    list.forEach {(key, value) ->
        keyCountMap[key].let { count ->
            if (count != null) {
                zipStream.putEntry(formatCount(key, count), value)
                keyCountMap[key] = count + 1
            } else {
                zipStream.putEntry(key, value)
                keyCountMap[key] = 1
            }
        }
    }

    zipStream.close()
    byteArrayStream.close()

    return byteArrayStream.toByteArray()
}


fun List<Pair<String, String>>.toZipByteArray(distinct: Boolean = true): ByteArray =
    listToZipByteArray(this.map { Pair(it.first, it.second.toByteArray()) }.let { if (distinct) it.distinct() else it })

private fun TreeItem<String>.flatValue(parentPath: String? = null): List<Pair<String, String>> {
    val currentPath = "${parentPath ?: ""}$key/"
    return value.map { Pair(currentPath + it.first, it.second) } + children.flatMap { it.flatValue() }
}

private fun treeItemsToZipByteArray(
    items: List<TreeItem<String>>,
    parentPath: String? = null
): ByteArray =
    items.flatMap { it.flatValue(parentPath) }.toZipByteArray()

fun TreeItem<String>.toZipByteArray(
    parentPath: String? = null
): ByteArray =
    treeItemsToZipByteArray(listOf(this), parentPath)

fun List<TreeItem<String>>.toZipByteArray(
    parentPath: String? = null
): ByteArray =
    treeItemsToZipByteArray(this, parentPath)
