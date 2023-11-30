package top.potmot.core.generate

import java.io.ByteArrayOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

private fun mapToZipByteArray(
    map: Map<String, ByteArray>
): ByteArray {
    val byteArrayStream = ByteArrayOutputStream()

    val zipStream = ZipOutputStream(byteArrayStream)

    map.entries.forEach {
        zipStream.putNextEntry(ZipEntry(it.key))
        zipStream.write(it.value)
        zipStream.closeEntry()
    }

    return byteArrayStream.toByteArray()
}

fun Map<String, String>.toZipByteArray(): ByteArray =
    mapToZipByteArray(mapValues { it.value.toByteArray() })

private fun TreeItem<String>.writeIntoZipStream(
    zipStream: ZipOutputStream,
    parentPath: String? = null
) {
    val currentPath = (parentPath?.takeIf { it.isNotBlank() } ?: "") + key + "/"

    zipStream.putNextEntry(ZipEntry(currentPath))
    zipStream.closeEntry()

    value.forEach {entry ->
        zipStream.putNextEntry(ZipEntry(currentPath + entry.key))
        zipStream.write(entry.value.toByteArray())
        zipStream.closeEntry()
    }

    children.forEach {
        it.writeIntoZipStream(zipStream, currentPath)
    }
}

private fun treeItemsToZipByteArray(
    items: List<TreeItem<String>>,
    parentPath: String? = null
): ByteArray {
    val byteArrayStream = ByteArrayOutputStream()

    val zipStream = ZipOutputStream(byteArrayStream)

    items.forEach {
        it.writeIntoZipStream(zipStream, parentPath)
    }

    return byteArrayStream.toByteArray()
}

fun TreeItem<String>.toZipByteArray(
    parentPath: String? = null
): ByteArray =
    treeItemsToZipByteArray(listOf(this), parentPath)

fun List<TreeItem<String>>.toZipByteArray(
    parentPath: String? = null
): ByteArray =
    treeItemsToZipByteArray(this, parentPath)
