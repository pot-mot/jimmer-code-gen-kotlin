package top.potmot.core.generate

import java.io.ByteArrayOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

fun Map<String, ByteArray>.toZipByteArray(): ByteArray {
    val byteArrayStream = ByteArrayOutputStream()

    val zipStream = ZipOutputStream(byteArrayStream)

    entries.forEach {
        zipStream.putNextEntry(ZipEntry(it.key))
        zipStream.write(it.value)
        zipStream.closeEntry()
    }

    return byteArrayStream.toByteArray()
}
