package top.potmot.enumeration

enum class GenerateType {
    ALL,
    BackEnd,
    DDL,
    Enum,
    Entity,
    Service,
    Test,
    DTO,
    FrontEnd,
    View,
    Permission,
    Route,
}

fun Iterable<String>.has(type: GenerateType): Boolean {
    return contains(type.name) // 判断枚举的 name 是否在列表中
}