package top.potmot.utils.identifier

import java.math.BigInteger
import java.security.MessageDigest

/**
 * 映射定长标识符，如果标识符超过指定长度，则会进行截断并填补 MD5 值
 */
class IdentifierFilter(
    private val maxLength: Int = 63,
    private val truncateLength: Int = maxLength / 8 + 1
) {
    private val hashMap: HashMap<String, String> = HashMap()

    fun filterIdentifier(identifier: String): String =
        if (!hashMap.containsKey(identifier)) {
            if (identifier.length <= maxLength) {
                hashMap[identifier] = identifier
                identifier
            } else {
                val truncatedIdentifier = truncateIdentifier(identifier)
                hashMap[identifier] = truncatedIdentifier
                truncatedIdentifier
            }
        } else {
            hashMap[identifier]!!
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