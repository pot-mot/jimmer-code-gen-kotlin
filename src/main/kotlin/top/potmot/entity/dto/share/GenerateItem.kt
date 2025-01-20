package top.potmot.entity.dto.share

interface GenerateItem {
    val id: Long

    val packagePath: String

    val name: String

    val comment: String
}

interface GenerateEntity : GenerateItem

interface GenerateEnum : GenerateItem
