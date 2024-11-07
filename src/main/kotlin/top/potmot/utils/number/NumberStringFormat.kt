package top.potmot.utils.number

fun Double.format(precision: Int? = null): String =
        if (precision != null)
            "%.${precision}f".format(this)
        else
            toString()

fun formatIfDouble(string: String?, precision: Int? = null): String? {
    val double = string?.toDoubleOrNull()
    return double?.format(precision) ?: string
}