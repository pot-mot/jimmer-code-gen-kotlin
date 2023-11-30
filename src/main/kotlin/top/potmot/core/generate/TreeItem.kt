package top.potmot.core.generate

data class TreeItem<T> (
    var key: String,
    var value: Map<String, T> = emptyMap(),
    var children: List<TreeItem<T>> = emptyList()
)
