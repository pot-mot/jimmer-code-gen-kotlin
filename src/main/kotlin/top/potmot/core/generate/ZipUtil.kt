package top.potmot.core.generate

import java.io.ByteArrayOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

/**
 * 将Map<String, ByteArray>转换为Zip压缩字节数组。
 * @return Zip压缩后的字节数组
 */
fun mapToZipByteArray(
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

/**
 * 将Map<String, String>转换为Zip压缩字节数组。
 * @return Zip压缩后的字节数组
 */
fun Map<String, String>.toZipByteArray(): ByteArray =
    mapToZipByteArray(mapValues { it.value.toByteArray() })
