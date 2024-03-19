package top.potmot.core.database.generate.identifier

import java.math.BigInteger
import java.security.MessageDigest

/**
 * 处理定长标识符，如果标识符超过指定长度，则会进行截断并填补 MD5 值
 */
open class IdentifierProcessor(
    private val maxLength: Int,
    private val truncateLength: Int = maxLength / 8 + 1
) {
    private val cache: HashMap<String, String> = HashMap()

    fun process(identifier: String): String {
        val truncatedIdentifier = cache.getOrDefault(identifier, identifier)

        return if (truncatedIdentifier.length > maxLength) {
            val result = truncateIdentifier(truncatedIdentifier)
            cache[identifier] = result
            result
        } else {
            truncatedIdentifier
        }
    }

    private fun truncateIdentifier(identifier: String): String {
        val index = maxLength - truncateLength - 1

        val firstPart = identifier.substring(0, index)
        val secondPart = identifier.substring(index)

        val md5 = MessageDigest.getInstance("MD5")
        val hashBytes = md5.digest(secondPart.toByteArray())
        val hashValue = BigInteger(1, hashBytes).toString(16)

        val truncatedHash = hashValue.substring(0, truncateLength)

        return "${firstPart}_$truncatedHash"
    }
}
