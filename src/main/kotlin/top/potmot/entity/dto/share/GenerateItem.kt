package top.potmot.entity.dto.share

interface GenerateItem {
    val id: Long

    val packagePath: String

    val name: String

    val comment: String
}

interface GenerateEntity : GenerateItem

interface GenerateEnum : GenerateItem

interface GenerateProperty {
    val id: Long

    val name: String

    val comment: String
}

data class GeneratePropertyData(
    override val id: Long,
    override val name: String,
    override val comment: String
): GenerateProperty