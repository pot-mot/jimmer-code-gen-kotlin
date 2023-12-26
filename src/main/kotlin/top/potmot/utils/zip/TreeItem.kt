package top.potmot.utils.zip

data class TreeItem<T> (
    var key: String,
    var value: List<Pair<String, T>> = emptyList(),
    var children: List<TreeItem<T>> = emptyList()
)
