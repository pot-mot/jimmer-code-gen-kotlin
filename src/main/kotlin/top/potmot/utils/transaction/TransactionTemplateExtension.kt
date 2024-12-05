package top.potmot.utils.transaction

import org.springframework.transaction.support.TransactionTemplate

fun <T> TransactionTemplate.executeNotNull(
    action: () -> T
): T {
    val result = execute {
        action()
    }
    if (result == null) {
        throw IllegalStateException("Transaction execution returned null")
    }
    return result
}