package top.potmot.core.database.name

interface DatabaseNameProvider {
    fun default(base: String): String = base
}
